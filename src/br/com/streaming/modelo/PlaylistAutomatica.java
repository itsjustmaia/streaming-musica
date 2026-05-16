package br.com.streaming.modelo;

import java.util.ArrayList;

public class PlaylistAutomatica extends Playlist {

    private String criterio; // "top", "recomendadas", "recentes"

    public PlaylistAutomatica(String nome, String criterio) {
        super(nome);
        this.criterio = criterio;
        this.descricao = "Gerada automaticamente pelo sistema";
    }

    public String getCriterio() { return criterio; }

    @Override
    public void reproduzir() {
        emReproducao = true;
        System.out.println("Playlist Automatica: " + nome);
        System.out.println("  Criterio: " + criterio);
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica disponivel.");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("  > " + m.getTitulo() + " - " + m.getArtista()
                    + " [" + m.getGenero() + "]");
        }
    }

    public void atualizar(ArrayList<Musica> todasMusicas) {
        musicas.clear();

        if (criterio.equalsIgnoreCase("top")) {
            int limite = Math.min(5, todasMusicas.size());
            for (int i = 0; i < limite; i++) {
                musicas.add(todasMusicas.get(i));
            }
        } else if (criterio.equalsIgnoreCase("recomendadas")) {
            ArrayList<String> generosAdicionados = new ArrayList<>();
            for (Musica m : todasMusicas) {
                if (!generosAdicionados.contains(m.getGenero())) {
                    musicas.add(m);
                    generosAdicionados.add(m.getGenero());
                }
            }
        } else if (criterio.equalsIgnoreCase("recentes")) {
            int inicio = Math.max(0, todasMusicas.size() - 5);
            for (int i = inicio; i < todasMusicas.size(); i++) {
                musicas.add(todasMusicas.get(i));
            }
        }

        System.out.println("Playlist '" + nome + "' atualizada com "
                + musicas.size() + " musica(s).");
    }
}
