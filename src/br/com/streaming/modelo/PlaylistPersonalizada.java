package br.com.streaming.modelo;

import br.com.streaming.util.FormatadorTempo;

public class PlaylistPersonalizada extends Playlist {

    private String criadorNome;

    public PlaylistPersonalizada(String nome, String criadorNome) {
        super(nome);
        this.criadorNome = criadorNome;
    }

    public String getCriadorNome() { return criadorNome; }

    @Override
    public void reproduzir() {
        emReproducao = true;
        System.out.println("Playlist Personalizada: " + nome
                + " (criada por " + criadorNome + ")");
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica nesta playlist.");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("  > " + m.getTitulo() + " - " + m.getArtista()
                    + " [" + m.getDuracaoFormatada() + "]");
        }
        System.out.println("  Duracao total: "
                + FormatadorTempo.formatarSegundos(getDuracaoTotal()));
    }
}
