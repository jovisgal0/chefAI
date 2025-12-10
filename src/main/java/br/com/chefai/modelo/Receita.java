package br.com.chefai.modelo;

import java.util.ArrayList;
import java.util.List;

public class Receita {
    private String nome;
    private int tempoPreparoMinutos;
    private String modoPreparo;
    private List<Ingrediente> ingredientes;

    public Receita(String nome, int tempoPreparoMinutos, String modoPreparo) {
        this.nome = nome;
        this.tempoPreparoMinutos = tempoPreparoMinutos;
        this.modoPreparo = modoPreparo;
        this.ingredientes = new ArrayList<>();
    }
    // Adiciona um ingrediente na lista da receita
    public void adicionarIngrediente(Ingrediente ingrediente) {
        this.ingredientes.add(ingrediente);
    }

    public String getNome() {
        return nome;
    }

    public int getTempoPreparoMinutos() {
        return tempoPreparoMinutos;
    }

    public String getModoPreparo() {
        return modoPreparo;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

@Override
    public String toString() {
        return "=== " + nome + " (" + tempoPreparoMinutos + " min) ===\n" +
               "Ingredientes: " + ingredientes + "\n" +  // <--- CORRIGIDO AQUI (adicionei o 'e')
               "Preparo: " + modoPreparo + "\n";
    }
}