package br.com.streaming.modelo;

import br.com.streaming.util.FormatadorTempo;
import br.com.streaming.util.Validador;

public class Musica extends ItemReproducao {

    private String artista;
    private int duracaoSegundos;
    private String genero;

    private static final String[] GENEROS_VALIDOS =
            {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};

    public Musica() {
        super("Sem titulo");
    }

    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        super(titulo);
        setTitulo(titulo);
        setArtista(artista);
        setDuracaoSegundos(duracaoSegundos);
        setGenero(genero);
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getTitulo()          { return nome; }
    public String getArtista()         { return artista; }
    public int    getDuracaoSegundos() { return duracaoSegundos; }
    public String getGenero()          { return genero; }

    // ── Setters com validação ─────────────────────────────────────────────────

    public void setTitulo(String titulo) {
        this.nome = Validador.stringNaoVazia(titulo, "Titulo");
    }

    public void setArtista(String artista) {
        this.artista = Validador.stringNaoVazia(artista, "Artista");
    }

    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0 || duracaoSegundos >= 3600)
            throw new IllegalArgumentException("Duracao deve ser entre 1 e 3599 segundos.");
        this.duracaoSegundos = duracaoSegundos;
    }

    public void setGenero(String genero) {
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero)) {
                this.genero = g;
                return;
            }
        }
        throw new IllegalArgumentException(
                "Genero invalido. Use: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Classica.");
    }

    // ── Reproduzivel (interface via ItemReproducao) ───────────────────────────

    @Override
    public void reproduzir() {
        emReproducao = true;
        System.out.println("  >> " + nome + " - " + artista
                + " [" + getDuracaoFormatada() + "]");
    }

    @Override
    public int getDuracaoTotal() {
        return duracaoSegundos;
    }

    // ── Utilitários ───────────────────────────────────────────────────────────

    public void exibir() {
        System.out.println("Titulo: " + nome
                + " | Artista: " + artista
                + " | Duracao: " + getDuracaoFormatada()
                + " | Genero: " + genero);
    }

    public String getDuracaoFormatada() {
        return FormatadorTempo.formatarSegundos(duracaoSegundos);
    }

    public boolean contemTitulo(String busca) {
        return nome.toLowerCase().contains(busca.toLowerCase());
    }

    public boolean contemArtista(String busca) {
        return artista.toLowerCase().contains(busca.toLowerCase());
    }
}
