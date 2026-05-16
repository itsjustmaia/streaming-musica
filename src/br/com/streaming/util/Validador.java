package br.com.streaming.util;

public class Validador {

    private Validador() {}

    public static String stringNaoVazia(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty())
            throw new IllegalArgumentException(campo + " nao pode ser nulo ou vazio.");
        return valor.trim();
    }

    public static String email(String email) {
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email nao pode ser nulo ou vazio.");
        if (!email.contains("@"))
            throw new IllegalArgumentException("Email invalido: deve conter '@'.");
        return email.trim();
    }

    public static boolean emailDuplicado(String email,
            java.util.ArrayList<br.com.streaming.modelo.Usuario> usuarios) {
        for (br.com.streaming.modelo.Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }
}
