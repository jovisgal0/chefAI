package br.com.chefai.servico;

import br.com.chefai.dto.*;
import br.com.chefai.modelo.Ingrediente;
import br.com.chefai.modelo.Receita;
import br.com.chefai.util.Configuracao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

// CLASSE ABSTRATA: Serve de base para os outros chefs
public abstract class SugestorBase {

    protected final HttpClient client;
    protected final ObjectMapper mapper;
    private final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    // Modelo Llama 3.3 (Rápido e Inteligente)
    private final String MODELO = "llama-3.3-70b-versatile";

    public SugestorBase() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    // Cada filho (Fit, Rápido, Gourmet) deve implementar sua personalidade aqui
    protected abstract String getPersonalidadeChef();

    // Método principal: Pede 3 receitas variadas
    public List<Receita> obterSugestoes(List<Ingrediente> ingredientes, String restricoes) {
        try {
            String promptUsuario = criarPromptComDespensa(ingredientes);
            
            String avisoRestricao = "";
            if (restricoes != null && !restricoes.trim().isEmpty()) {
                avisoRestricao = "\n IMPORTANTE: O usuário tem restrições: [" + restricoes + "].";
            }

            // PROMPT PARA GARANTIR 3 RECEITAS DIFERENTES
            String promptSistema = getPersonalidadeChef() + avisoRestricao + """
                
                SEU OBJETIVO:
                Criar um MENU COM EXATAMENTE 3 RECEITAS DISTINTAS usando os ingredientes.
                
                REGRAS DE VARIEDADE:
                - As 3 receitas devem ser COMPLETAMENTE DIFERENTES entre si (não repita o método de preparo).
                - Exemplo: Sugira uma salada, um prato quente e um assado/grelhado.
                
                REGRAS DE FORMATO:
                - Defina quantidades exatas (ex: "200g", "1 xícara"). Evite "a gosto".
                - Responda APENAS com este JSON (lista com 3 objetos):
                [
                  {
                    "nome": "Opção 1 (ex: Algo Grelhado)",
                    "tempoPreparoMinutos": 20,
                    "modoPreparo": "Passos...",
                    "ingredientes": [{"nome": "x", "quantidade": "y"}]
                  },
                  {
                    "nome": "Opção 2 (ex: Algo Cozido/Sopa)",
                    "tempoPreparoMinutos": 25,
                    "modoPreparo": "Passos...",
                    "ingredientes": [{"nome": "x", "quantidade": "y"}]
                  },
                  {
                    "nome": "Opção 3 (ex: Algo Criativo)",
                    "tempoPreparoMinutos": 15,
                    "modoPreparo": "Passos...",
                    "ingredientes": [{"nome": "x", "quantidade": "y"}]
                  }
                ]
                """;

            List<Message> mensagens = List.of(
                    new Message("system", promptSistema),
                    new Message("user", promptUsuario)
            );

            GroqRequest payload = new GroqRequest(MODELO, mensagens, 0.7); 
            String jsonBody = mapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + Configuracao.getApiKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            System.out.println("O Chef está criando 3 opções variadas...");

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                GroqResponse groqResponse = mapper.readValue(response.body(), GroqResponse.class);
                String conteudo = groqResponse.choices().get(0).message().content();
                List<ReceitaDTO> dtos = mapper.readValue(conteudo, new TypeReference<List<ReceitaDTO>>(){});
                return converterParaDominio(dtos);
            } else {
                System.err.println("Erro na API: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private String criarPromptComDespensa(List<Ingrediente> ingredientes) {
        StringBuilder sb = new StringBuilder("Tenho estes ingredientes: ");
        for (Ingrediente i : ingredientes) {
            sb.append(i.getNome()).append(" (").append(i.getQuantidade()).append("), ");
        }
        sb.append(". Considere que tenho itens básicos (sal, óleo, alho, cebola).");
        return sb.toString();
    }

    private List<Receita> converterParaDominio(List<ReceitaDTO> dtos) {
        List<Receita> receitas = new ArrayList<>();
        for (ReceitaDTO dto : dtos) {
            Receita r = new Receita(dto.nome(), dto.tempoPreparoMinutos(), dto.modoPreparo());
            if (dto.ingredientes() != null) {
                for (ReceitaDTO.Item item : dto.ingredientes()) {
                    r.adicionarIngrediente(new Ingrediente(item.nome(), item.quantidade()));
                }
            }
            receitas.add(r);
        }
        return receitas;
    }
}