package br.com.chefai.main;

import br.com.chefai.modelo.Ingrediente;
import br.com.chefai.modelo.Receita;
import br.com.chefai.servico.ChefFit;
import br.com.chefai.servico.ChefGourmet;
import br.com.chefai.servico.ChefRapido;
import br.com.chefai.servico.SugestorBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Ingrediente> minhaDespensa = new ArrayList<>();
        SugestorBase chef;

        // 1. SELEÇÃO DO TIPO DE CHEF (Polimorfismo)
        System.out.println("=========================================");
        System.out.println("        BEM-VINDO AO CHEF AI       ");
        System.out.println("=========================================");
        System.out.println("Quem você quer que cozinhe hoje?");
        System.out.println("1. Chef Rápido (Receitas express em < 20min)");
        System.out.println("2. Chef Fit (Saudável e Low Carb)");
        System.out.println("3. Chef Gourmet (Sabor e apresentação refinada)");
        System.out.print("Escolha uma opção (1-3): ");
        
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "2" -> {
                System.out.println(">> Chef Fit selecionado! Focando na saúde.");
                chef = new ChefFit();
            }
            case "3" -> {
                System.out.println(">> Chef Gourmet selecionado! Caprichando no sabor.");
                chef = new ChefGourmet();
            }
            default -> {
                System.out.println(">> Chef Rápido selecionado! Vamos agilizar.");
                chef = new ChefRapido();
            }
        }

        // 2. CADASTRO DE INGREDIENTES
        System.out.println("\n-----------------------------------------");
        System.out.println("O que tem na sua geladeira/despensa?");
        System.out.println("(Digite 'fim' para encerrar a lista)");
        
        while (true) {
            System.out.print("\n Nome do ingrediente:(Ou digite ''fim'' para finalizar) ");
            String nome = scanner.nextLine();

            if (nome.equalsIgnoreCase("fim")) {
                break;
            }

            if (nome.trim().isEmpty()) continue;

            System.out.print("Quantidade (ex: 2 un, 500g, 100ml): ");
            String qtd = scanner.nextLine();

            minhaDespensa.add(new Ingrediente(nome, qtd));
            System.out.println( nome + " adicionado.");
        }

        if (minhaDespensa.isEmpty()) {
            System.out.println("Você não informou ingredientes. Até a próxima!");
            scanner.close();
            return;
        }

        // 3. PERGUNTA DE RESTRIÇÃO ALIMENTAR
        System.out.println("\n-----------------------------------------");
        System.out.println("Alguma restrição alimentar ou alergia?");
        System.out.println("(Ex: Vegano, Sem Glúten, Alergia a Camarão)");
        System.out.print("Digite aqui (ou Enter para pular): ");
        String restricoes = scanner.nextLine();

        // 4. CHAMADA DO SERVIÇO
        System.out.println("\n=========================================");
        System.out.println(" Consultando o Chef... isso leva uns segundos...");
        System.out.println("=========================================");

        // Passamos a lista E as restrições
        List<Receita> sugestoes = chef.obterSugestoes(minhaDespensa, restricoes);

        // 5. IMPRESSÃO DO RESULTADO (Menu Elegante)
        if (sugestoes.isEmpty()) {
            System.out.println("O Chef não conseguiu gerar receitas. Tente verificar sua internet ou mudar os ingredientes.");
        } else {
            System.out.println("\n SUGESTÕES DO CHEF PARA VOCÊ ");
            
            for (int i = 0; i < sugestoes.size(); i++) {
                Receita r = sugestoes.get(i);
                
                System.out.println("\n OPÇÃO " + (i + 1) + ": " + r.getNome().toUpperCase());
                System.out.println(" *Tempo estimado: " + r.getTempoPreparoMinutos() + " min");
                
                System.out.println("\n INGREDIENTES NECESSÁRIOS:");
                for (Ingrediente ing : r.getIngredientes()) {
                    // Formata bonitinho: - 200g de Arroz
                    System.out.println("   ▪ " + ing.getQuantidade() + " de " + ing.getNome());
                }
                
                System.out.println("\n MODO DE PREPARO:");
                // Garante que as quebras de linha vindas do JSON apareçam corretamente
                String preparoFormatado = r.getModoPreparo().replace("\\n", "\n");
                System.out.println(preparoFormatado);
                
                System.out.println("_________________________________________");
            }
        }
        
        scanner.close();
    }
}