package br.com.streaming.modelo;

public class UsuarioFree extends Usuario {

    private static final int MAX_PLAYLISTS = 3;

    private int contadorReproducoes;
    private int limiteReproducoes;
    private int anunciosExibidos;

    public UsuarioFree(String nome, String email) {
        super(nome, email);
        this.contadorReproducoes = 0;
        this.limiteReproducoes   = 30;
        this.anunciosExibidos    = 0;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public int getContadorReproducoes() { return contadorReproducoes; }
    public int getLimiteReproducoes()   { return limiteReproducoes; }
    public int getAnunciosExibidos()    { return anunciosExibidos; }

    // ── Polimorfismo: reprodução com limite e anúncios ────────────────────────

    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        if (contadorReproducoes >= limiteReproducoes) {
            System.out.println("Limite diario de reproducoes atingido!");
            System.out.println("Assine Premium para reproducoes ilimitadas!");
            return;
        }

        contadorReproducoes++;

        if (contadorReproducoes % 3 == 0) {
            exibirAnuncio();
        }

        super.reproduzirMusica(musica);
        System.out.println("[Qualidade: Padrao]");
    }

    // Sobrecarga: reproduzir com exibição de detalhes
    public void reproduzirMusica(Musica musica, boolean exibirDetalhes) {
        reproduzirMusica(musica);
        if (exibirDetalhes && musica != null) {
            System.out.println("   Duracao: " + musica.getDuracaoFormatada()
                    + " | Genero: " + musica.getGenero());
        }
    }

    // ── Polimorfismo: limite de playlists ─────────────────────────────────────

    @Override
    public void criarPlaylist(String nome) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println("Limite de " + MAX_PLAYLISTS + " playlists atingido!");
            System.out.println("Assine Premium para playlists ilimitadas!");
            return;
        }
        super.criarPlaylist(nome);
    }

    // ── Anúncio ───────────────────────────────────────────────────────────────

    public void exibirAnuncio() {
        anunciosExibidos++;
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANUNCIO #" + anunciosExibidos
                + ": Assine Premium e ouca sem interrupcoes!");
        System.out.println("=".repeat(50) + "\n");
    }
}
