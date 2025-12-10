package br.com.chefai.servico;

public class ChefRapido extends SugestorBase {
    @Override
    protected String getPersonalidadeChef() {
        return "Você é um Chef Express. Gere 3 receitas rápidas possíveis de fazer em menos de 20 minutos mas VARIADAS. Exemplo: um sanduíche, um omelete e uma massa rápida.";
    }
}
