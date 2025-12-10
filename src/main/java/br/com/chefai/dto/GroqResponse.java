package br.com.chefai.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonIgnoreProperties(ignoreUnknown = true) -> Ignora campos extras que a Groq manda e a gente não usa
@JsonIgnoreProperties(ignoreUnknown = true)
public record GroqResponse(List<Choice> choices) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Choice(Message message) {
    }
}