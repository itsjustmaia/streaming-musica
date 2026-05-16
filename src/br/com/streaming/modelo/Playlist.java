package br.com.streaming.modelo;

import br.com.streaming.util.Validador;

import java.util.ArrayList;

public class Playlist extends ItemReproducao {

    protected ArrayList<Musica> musicas;
    protected String descricao;

    public Playlist() {
        super("Sem nome");
        this.musicas = new ArrayList<>();
    }

    public Playlist(String nome) {
        super(nome);
        this.musicas = new ArrayList<>();
        setNome(nome);
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public String getDescricao() { return descricao; }

    public void setNome(String nome) {
        this.nome = Validador.stringNaoVazia(nome, "Nome da playlist");
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // ── Gerenciamento de músicas ──────────────────────────────────────────────

    public void adicionarMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida (null). Nao foi adicionada.");
            return;
        }
        musicas.add(musica);
        System.out.println("Musica '" + musica.getTitulo() + "' adicionada a playlist " + nome);
    }

    public void removerMusica(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Indice invalido.");
            return;
        }
        musicas.remove(indice);
        System.out.println("Musica removida com sucesso.");
    }

    public void listarMusicas() {
        System.out.println("--- Playlist: " + nome + " ---");
        if (descricao != null && !descricao.isEmpty())
            System.out.println("Descricao: " + descricao);
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma musica nesta playlist.");
        } else {
            for (int i = 0; i < musicas.size(); i++) {
                System.out.print((i + 1) + ". ");
                musicas.get(i).exibir();
            }
        }
    }

    // ── Reproduzivel ──────────────────────────────────────────────────────────

    @Override
    public void reproduzir() {
        emReproducao = true;
        System.out.println("Reproduzindo playlist: " + nome);
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica nesta playlist.");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("  > " + m.getTitulo() + " - " + m.getArtista());
        }
    }

    @Override
    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) total += m.getDuracaoSegundos();
        return total;
    }

    // ── Utilitários ───────────────────────────────────────────────────────────

    public int getQuantidadeMusicas()    { return musicas.size(); }
    public ArrayList<Musica> getMusicas() { return musicas; }
}
