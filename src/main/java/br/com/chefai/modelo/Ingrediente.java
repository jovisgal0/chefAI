package br.com.chefai.modelo;

public class Ingrediente {
    private String nome;
    private String quantidade;

    // Construtor
    public Ingrediente(String nome, String quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    // Getters (para ler os valores)
    public String getNome() {
        return nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return nome + " (" + quantidade + ")";
    }
}