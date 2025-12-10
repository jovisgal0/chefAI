ChefAI - Sugestor de Receitas Inteligente

O ChefAI é uma aplicação Java de console que utiliza Modelos de Linguagem (LLMs) para sugerir receitas rápidas, fitness e gourmet, baseadas estritamente nos ingredientes que o usuário possui em casa e
levando em consideração alguma restrição alimentar.

Este projeto foi desenvolvido como um estudo prático de Programação Orientada a Objetos (POO) e integração de APIs.

Funcionalidades

Cadastro de Ingredientes: Input flexível de itens disponíveis na geladeira/despensa.
Sugestão Inteligente: Consulta uma IA (OpenAI GPT ou Google Gemini) para gerar receitas criativas.
Filtro de Tempo: Foco exclusivo em receitas rápidas (menos de 30 minutos).
Parsing Automático: Converte a resposta da IA em objetos Java estruturados.
Segurança: Gerenciamento de chaves de API via arquivos de configuração (fora do código-fonte).

Tecnologias Utilizadas


-Linguagem: Java 21 (ou superior)

-Build Tool: Maven

-Processamento JSON

-API: Integração com LLM (Sugestão: Groq)

Aplicação da POO


Este projeto demonstra a aplicação prática da Orientação a Objetos:
Encapsulamento: As classes Ingrediente e Receita protegem seus estados internos, permitindo acesso apenas via métodos controlados.
Herança: A classe SugestorRapido herda comportamentos base da classe abstrata SugestorReceita.
Polimorfismo: O sistema trata diferentes estratégias de sugestão de forma uniforme através da abstração, permitindo trocar o tipo de sugestor sem quebrar o código cliente.
Composição: Uma Receita é composta por uma lista de objetos Ingrediente.


![Model!Main_0](https://github.com/user-attachments/assets/eb12be47-55f2-43b1-9fd0-2a4d157a20b7)


Instalação e Configuração


Pré-requisitos


-JDK 21 ou superior instalado.

-Maven instalado (opcional, caso prefira compilar manualmente).

-Uma chave de API válida (Groq, OpenAI ou Google Gemini).
  
Passo 1: Clonar o Repositório


Passo 2: Configurar a API Key
    Por segurança, a chave da API disponibilizada. Você deve criar o arquivo de configuração manualmente.

    
    Vá até a pasta de resources: src/main/resources/

    
    Crie um arquivo chamado application.properties.

    
    Adicione sua chave no formato abaixo:

    
    Properties

    
      # src/main/resources/application.properties

      
      # Escolha o provider (openai ou gemini)

      
      llm.provider=openai

      
      llm.api.key=sk-sua-chave-secreta-aqui-123456

      
    Nota: O arquivo application.properties já está listado no .gitignore para evitar vazamento da chave.

    
Passo 3: Compilar e rodar o Projeto.

Exemplo de Uso 

=== Bem-vindo ao ChefAI ===
Digite seus ingredientes (separados por vírgula):
> ovos, queijo parmesão, macarrão, bacon

Consultando o Chef Inteligente...

--- SUGESTÃO 1: Carbonara Rápida ---

Tempo: 20 min

Ingredientes:

 - Ovos (2 unid)
   
 - Queijo Parmesão (50g)
   
 - Macarrão (200g)

 - Bacon (100g)
   
Modo de Preparo:

Cozinhe o macarrão. Frite o bacon. Misture ovos e queijo e incorpore ao macarrão quente.




Desenvolvido por João Vitor Caldeira da Silva - Engenharia da Computação UTFPR
