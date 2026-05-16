package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.PlaylistAutomatica;
import br.com.streaming.modelo.Usuario;

import java.util.ArrayList;

public class GeradorRecomendacoes {

    private GeradorRecomendacoes() {}

    /**
     * Gera uma PlaylistAutomatica com base no critério e adiciona ao perfil do usuário.
     */
    public static PlaylistAutomatica gerar(String criterio,
                                           String nomePlaylist,
                                           ArrayList<Musica> bancoMusicas,
                                           Usuario usuario) {
        PlaylistAutomatica pa = new PlaylistAutomatica(nomePlaylist, criterio);
        pa.atualizar(bancoMusicas);
        usuario.adicionarPlaylist(pa);
        System.out.println("Playlist automatica '" + nomePlaylist
                + "' adicionada ao perfil de " + usuario.getNome() + "!");
        return pa;
    }

    /**
     * Exibe estatísticas simples do banco de músicas por gênero.
     */
    public static void exibirEstatisticasGenero(ArrayList<Musica> bancoMusicas) {
        System.out.println("\n--- DISTRIBUICAO POR GENERO ---");
        String[] generos = {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};
        for (String g : generos) {
            long count = bancoMusicas.stream()
                    .filter(m -> m.getGenero().equalsIgnoreCase(g))
                    .count();
            if (count > 0)
                System.out.println("  " + g + ": " + count + " musica(s)");
        }
    }
}
