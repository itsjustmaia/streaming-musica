package br.com.streaming.modelo;

import br.com.streaming.servico.Reproduzivel;

/**
 * Classe abstrata que representa qualquer item reproduzível no sistema.
 * Musica e Playlist herdam desta classe e implementam Reproduzivel.
 */
public abstract class ItemReproducao implements Reproduzivel {

    protected String nome;
    protected boolean emReproducao;
    protected boolean pausado;

    public ItemReproducao(String nome) {
        this.nome = nome;
        this.emReproducao = false;
        this.pausado = false;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void pausar() {
        if (emReproducao) {
            pausado = true;
            emReproducao = false;
            System.out.println("Pausado: " + nome);
        } else {
            System.out.println("Nada em reproducao para pausar.");
        }
    }

    @Override
    public void parar() {
        emReproducao = false;
        pausado = false;
        System.out.println("Parado: " + nome);
    }

    // Subclasses devem implementar reproduzir() e getDuracaoTotal()
}
