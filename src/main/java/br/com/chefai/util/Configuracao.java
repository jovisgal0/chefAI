package br.com.chefai.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracao {
    private static Properties properties = new Properties();

    // Bloco estático: roda assim que a classe é carregada na memória
    static {
        try (InputStream input = Configuracao.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("AVISO: Arquivo application.properties não encontrado!");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getApiKey() {
        return properties.getProperty("groq.api.key");
    }
}