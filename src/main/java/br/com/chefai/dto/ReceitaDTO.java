package br.com.chefai.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReceitaDTO(
    String nome, 
    int tempoPreparoMinutos, 
    String modoPreparo, 
    List<Item> ingredientes // <--- O erro acontece porque essa lista não existia no seu arquivo
) {
    // Essa classe interna 'Item' é o que estava faltando
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(String nome, String quantidade) {}
}