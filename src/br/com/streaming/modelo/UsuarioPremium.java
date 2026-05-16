package br.com.streaming.modelo;

import br.com.streaming.servico.Baixavel;
import br.com.streaming.util.Validador;

import java.util.ArrayList;

public class UsuarioPremium extends Usuario implements Baixavel {

    private String tipoPlano; // Mensal, Anual, Familiar
    private ArrayList<Musica> musicasBaixadas;

    public UsuarioPremium(String nome, String email, String tipoPlano) {
        super(nome, email);
        setTipoPlano(tipoPlano);
        this.musicasBaixadas = new ArrayList<>();
    }

    // ── Getters / Setters ─────────────────────────────────────────────────────

    public String getTipoPlano() { return tipoPlano; }

    public void setTipoPlano(String tipoPlano) {
        this.tipoPlano = Validador.stringNaoVazia(tipoPlano, "Tipo de plano");
    }

    // ── Polimorfismo: reprodução em alta qualidade ────────────────────────────

    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        System.out.println("Reproduzindo em ALTA QUALIDADE: "
                + musica.getTitulo() + " - " + musica.getArtista());
        System.out.println("[Qualidade: Alta | Plano: " + tipoPlano + "]");
        historicoReproducao.add(musica);
    }

    // Sobrecarga: reprodução offline
    public void reproduzirMusica(Musica musica, boolean modoOffline) {
        if (modoOffline) {
            if (!estaBaixada(musica)) {
                System.out.println("Musica nao esta baixada para reproducao offline.");
                return;
            }
            System.out.println("[OFFLINE] Reproduzindo: " + musica.getTitulo());
            historicoReproducao.add(musica);
        } else {
            reproduzirMusica(musica);
        }
    }

    // ── Baixavel (interface) ──────────────────────────────────────────────────

    @Override
    public void baixar(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida.");
            return;
        }
        if (estaBaixada(musica)) {
            System.out.println("Musica '" + musica.getTitulo() + "' ja esta baixada!");
            return;
        }
        musicasBaixadas.add(musica);
        System.out.println("Musica baixada: " + musica.getTitulo()
                + " - " + musica.getArtista());
    }

    @Override
    public void removerDownload(Musica musica) {
        if (musica == null) return;
        boolean removida = musicasBaixadas.removeIf(m ->
                m.getTitulo().equalsIgnoreCase(musica.getTitulo())
                && m.getArtista().equalsIgnoreCase(musica.getArtista()));
        if (removida) {
            System.out.println("Download removido: " + musica.getTitulo());
        } else {
            System.out.println("Musica nao encontrada nos downloads.");
        }
    }

    @Override
    public boolean estaBaixada(Musica musica) {
        if (musica == null) return false;
        for (Musica m : musicasBaixadas) {
            if (m.getTitulo().equalsIgnoreCase(musica.getTitulo())
                    && m.getArtista().equalsIgnoreCase(musica.getArtista())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getTamanhoBaixados() {
        return musicasBaixadas.size();
    }

    // ── Compatibilidade com chamadas legadas ──────────────────────────────────

    public void baixarMusica(Musica musica)  { baixar(musica); }
    public int getQuantidadeMusicasBaixadas() { return getTamanhoBaixados(); }

    public void listarMusicasBaixadas() {
        System.out.println("\n--- MUSICAS BAIXADAS ---");
        if (musicasBaixadas.isEmpty()) {
            System.out.println("Nenhuma musica baixada ainda.");
            return;
        }
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            System.out.print((i + 1) + ". ");
            musicasBaixadas.get(i).exibir();
        }
        System.out.println("Total: " + musicasBaixadas.size() + " musica(s) baixada(s).");
    }
}
