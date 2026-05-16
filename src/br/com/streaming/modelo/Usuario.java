package br.com.streaming.modelo;

import br.com.streaming.util.Validador;

import java.util.ArrayList;

public class Usuario {

    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica> historicoReproducao;

    public Usuario() {
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    public Usuario(String nome, String email) {
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
        setNome(nome);
        setEmail(email);
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public String getNome()  { return nome; }
    public String getEmail() { return email; }

    public void setNome(String nome) {
        this.nome = Validador.stringNaoVazia(nome, "Nome do usuario");
    }

    // final: validação de e-mail não pode ser sobrescrita por subclasses
    public final void setEmail(String email) {
        this.email = Validador.email(email);
    }

    // ── Reprodução ────────────────────────────────────────────────────────────

    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        System.out.println("Reproduzindo: " + musica.getTitulo()
                + " - " + musica.getArtista());
        historicoReproducao.add(musica);
    }

    public void exibirHistorico() {
        System.out.println("\n--- HISTORICO DE REPRODUCAO de " + nome + " ---");
        if (historicoReproducao.isEmpty()) {
            System.out.println("Nenhuma musica reproduzida ainda.");
            return;
        }
        for (int i = 0; i < historicoReproducao.size(); i++) {
            System.out.print((i + 1) + ". ");
            historicoReproducao.get(i).exibir();
        }
    }

    // ── Playlists ─────────────────────────────────────────────────────────────

    public void criarPlaylist(String nomePlaylist) {
        PlaylistPersonalizada nova = new PlaylistPersonalizada(nomePlaylist, this.nome);
        playlists.add(nova);
        System.out.println("Playlist '" + nomePlaylist + "' criada com sucesso!");
    }

    public Playlist getPlaylist(int indice) {
        if (indice >= 0 && indice < playlists.size())
            return playlists.get(indice);
        return null;
    }

    public void listarPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("Nenhuma playlist encontrada.");
        } else {
            for (int i = 0; i < playlists.size(); i++) {
                Playlist p = playlists.get(i);
                String tipo = (p instanceof PlaylistAutomatica)
                        ? " [Automatica]" : " [Personalizada]";
                System.out.println((i + 1) + ". " + p.getNome() + tipo);
            }
        }
    }

    public int getQuantidadePlaylists() { return playlists.size(); }
    public int getTotalReproducoes()    { return historicoReproducao.size(); }

    public void adicionarPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }
}
