package br.com.chefai.dto;

import java.util.List;

// Record cria automaticamente construtor, getters, etc.
public record GroqRequest(String model, List<Message> messages, double temperature) {
}