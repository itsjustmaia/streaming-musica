package br.com.streaming.principal;

import br.com.streaming.modelo.*;
import br.com.streaming.servico.GeradorRecomendacoes;
import br.com.streaming.util.FormatadorTempo;
import br.com.streaming.util.Validador;

import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusica {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Musica>  bancoMusicas  = new ArrayList<>();
    static ArrayList<Usuario> usuarios      = new ArrayList<>();
    static Usuario usuarioLogado            = null;

    public static void main(String[] args) {
        popularBancoDeMusicas();

        int opcao;
        do {
            if (usuarioLogado == null) {
                exibirMenuInicial();
                try { opcao = Integer.parseInt(scanner.nextLine()); }
                catch (Exception e) { opcao = -1; }
                processarMenuInicial(opcao);
            } else {
                if (usuarioLogado instanceof UsuarioPremium) {
                    exibirMenuPremium((UsuarioPremium) usuarioLogado);
                } else {
                    exibirMenuFree((UsuarioFree) usuarioLogado);
                }

                try { opcao = Integer.parseInt(scanner.nextLine()); }
                catch (Exception e) { opcao = -1; }

                if (usuarioLogado instanceof UsuarioPremium) {
                    processarOpcaoPremium(opcao, (UsuarioPremium) usuarioLogado);
                } else {
                    processarOpcaoFree(opcao, (UsuarioFree) usuarioLogado);
                }
            }
        } while (true);
    }

    // ─────────────────────────────────────────────
    // MENU INICIAL
    // ─────────────────────────────────────────────

    static void exibirMenuInicial() {
        System.out.println("\n=== SISTEMA DE STREAMING DE MUSICA ===");
        System.out.println("1. Criar novo usuario");
        System.out.println("2. Login");
        System.out.println("3. Listar usuarios cadastrados");
        System.out.println("4. Estatisticas do sistema");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    static void processarMenuInicial(int opcao) {
        switch (opcao) {
            case 1: criarUsuario();       break;
            case 2: fazerLogin();         break;
            case 3: listarUsuarios();     break;
            case 4: exibirEstatisticas(); break;
            case 0: System.out.println("Ate logo!"); System.exit(0);
            default: System.out.println("Opcao invalida.");
        }
    }

    // ─────────────────────────────────────────────
    // MENUS DE USUÁRIO LOGADO
    // ─────────────────────────────────────────────

    static void exibirMenuFree(UsuarioFree u) {
        System.out.println("\n=== MENU FREE - " + u.getNome() + " ===");
        System.out.println("Reproducoes: " + u.getContadorReproducoes()
                + "/" + u.getLimiteReproducoes()
                + " | Playlists: " + u.getQuantidadePlaylists() + "/3");
        System.out.println("---");
        System.out.println("1.  Cadastrar musica");
        System.out.println("2.  Listar todas as musicas");
        System.out.println("3.  Reproduzir musica");
        System.out.println("4.  Ver historico");
        System.out.println("5.  Criar playlist (max. 3)");
        System.out.println("6.  Gerenciar playlists");
        System.out.println("7.  Buscar musica");
        System.out.println("8.  Playlists automaticas");
        System.out.println("9.  Fazer upgrade para Premium");
        System.out.println("10. Logout");
        System.out.println("0.  Sair");
        System.out.print("Escolha: ");
    }

    static void exibirMenuPremium(UsuarioPremium u) {
        System.out.println("\n=== MENU PREMIUM - " + u.getNome()
                + " [" + u.getTipoPlano() + "] ===");
        System.out.println("Playlists: " + u.getQuantidadePlaylists()
                + " | Downloads: " + u.getQuantidadeMusicasBaixadas());
        System.out.println("---");
        System.out.println("1.  Cadastrar musica");
        System.out.println("2.  Listar todas as musicas");
        System.out.println("3.  Reproduzir musica (Alta Qualidade)");
        System.out.println("4.  Ver historico");
        System.out.println("5.  Criar playlist (ilimitado)");
        System.out.println("6.  Gerenciar playlists");
        System.out.println("7.  Buscar musica");
        System.out.println("8.  Playlists automaticas");
        System.out.println("9.  Baixar musica");
        System.out.println("10. Ver musicas baixadas");
        System.out.println("11. Logout");
        System.out.println("0.  Sair");
        System.out.print("Escolha: ");
    }

    // ─────────────────────────────────────────────
    // PROCESSAMENTO DE OPÇÕES
    // ─────────────────────────────────────────────

    static void processarOpcaoFree(int op, UsuarioFree u) {
        switch (op) {
            case 1:  cadastrarMusica();           break;
            case 2:  listarMusicas();             break;
            case 3:  reproduzirMusica(u);         break;
            case 4:  u.exibirHistorico();         break;
            case 5:  criarPlaylist(u);            break;
            case 6:  gerenciarPlaylists(u);       break;
            case 7:  buscarMusica();              break;
            case 8:  menuPlaylistsAutomaticas(u); break;
            case 9:  fazerUpgradePremium(u);      break;
            case 10: logout();                    break;
            case 0:  System.out.println("Ate logo, " + u.getNome() + "!"); System.exit(0);
            default: System.out.println("Opcao invalida!");
        }
    }

    static void processarOpcaoPremium(int op, UsuarioPremium u) {
        switch (op) {
            case 1:  cadastrarMusica();           break;
            case 2:  listarMusicas();             break;
            case 3:  reproduzirMusica(u);         break;
            case 4:  u.exibirHistorico();         break;
            case 5:  criarPlaylist(u);            break;
            case 6:  gerenciarPlaylists(u);       break;
            case 7:  buscarMusica();              break;
            case 8:  menuPlaylistsAutomaticas(u); break;
            case 9:  baixarMusicaPremium(u);      break;
            case 10: u.listarMusicasBaixadas();   break;
            case 11: logout();                    break;
            case 0:  System.out.println("Ate logo, " + u.getNome() + "!"); System.exit(0);
            default: System.out.println("Opcao invalida!");
        }
    }

    // ─────────────────────────────────────────────
    // SISTEMA MULTI-USUÁRIO
    // ─────────────────────────────────────────────

    static void criarUsuario() {
        System.out.println("\n=== CRIAR NOVO USUARIO ===");

        String nome  = lerStringNaoVazia("Nome: ");
        String email = lerEmailUnico();

        System.out.println("\nTipo de conta:");
        System.out.println("1. Free");
        System.out.println("2. Premium");
        System.out.print("Escolha: ");

        int tipo = -1;
        try { tipo = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

        try {
            if (tipo == 2) {
                String plano = escolherPlano();
                usuarios.add(new UsuarioPremium(nome, email, plano));
                System.out.println("Usuario Premium (" + plano + ") criado: " + nome);
            } else {
                usuarios.add(new UsuarioFree(nome, email));
                System.out.println("Usuario Free criado: " + nome);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar usuario: " + e.getMessage());
        }
    }

    static String lerEmailUnico() {
        while (true) {
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty())          { System.out.println("Email nao pode ser vazio."); continue; }
            if (!email.contains("@"))     { System.out.println("Email invalido.");           continue; }
            if (Validador.emailDuplicado(email, usuarios)) {
                System.out.println("E-mail ja cadastrado."); continue;
            }
            return email;
        }
    }

    static void fazerLogin() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado. Crie um primeiro.");
            return;
        }
        System.out.println("\n=== LOGIN ===");
        listarUsuarios();
        System.out.print("Escolha o numero do usuario: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= usuarios.size()) {
                System.out.println("Opcao invalida.");
                return;
            }
            usuarioLogado = usuarios.get(idx);
            if (usuarioLogado instanceof UsuarioPremium) {
                UsuarioPremium p = (UsuarioPremium) usuarioLogado;
                System.out.println("Login realizado: " + p.getNome()
                        + " (Premium - " + p.getTipoPlano() + ")");
            } else {
                System.out.println("Login realizado: " + usuarioLogado.getNome() + " (Free)");
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
        }
    }

    static void logout() {
        System.out.println("Logout realizado. Ate logo, " + usuarioLogado.getNome() + "!");
        usuarioLogado = null;
    }

    static void listarUsuarios() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }
        System.out.println("\n--- USUARIOS CADASTRADOS ---");
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            if (u instanceof UsuarioPremium) {
                UsuarioPremium p = (UsuarioPremium) u;
                System.out.println((i + 1) + ". " + p.getNome()
                        + " [Premium - " + p.getTipoPlano() + "] | " + p.getEmail());
            } else if (u instanceof UsuarioFree) {
                UsuarioFree f = (UsuarioFree) u;
                System.out.println((i + 1) + ". " + f.getNome()
                        + " [Free | " + f.getContadorReproducoes()
                        + "/" + f.getLimiteReproducoes() + " reprod.] | " + f.getEmail());
            }
        }
    }

    // ─────────────────────────────────────────────
    // ESTATÍSTICAS
    // ─────────────────────────────────────────────

    static void exibirEstatisticas() {
        System.out.println("\n=== ESTATISTICAS DO SISTEMA ===");

        int totalFree = 0, totalPremium = 0;
        int reproducoesFree = 0, reproducoesPremium = 0;
        int anunciosTotais = 0, totalDownloads = 0;

        for (Usuario u : usuarios) {
            if (u instanceof UsuarioPremium) {
                totalPremium++;
                UsuarioPremium p = (UsuarioPremium) u;
                reproducoesPremium += p.getTotalReproducoes();
                totalDownloads     += p.getQuantidadeMusicasBaixadas();
            } else if (u instanceof UsuarioFree) {
                totalFree++;
                UsuarioFree f = (UsuarioFree) u;
                reproducoesFree += f.getContadorReproducoes();
                anunciosTotais  += f.getAnunciosExibidos();
            }
        }

        int totalReproducoes = reproducoesFree + reproducoesPremium;

        System.out.println("Total de usuarios: " + (totalFree + totalPremium));
        System.out.println("  - Free:    " + totalFree    + " usuario(s)");
        System.out.println("  - Premium: " + totalPremium + " usuario(s)");
        System.out.println();
        System.out.println("Reproducoes totais: " + totalReproducoes);
        if (totalReproducoes > 0) {
            System.out.printf("  - Free:    %d (%.0f%%)%n", reproducoesFree,
                    (reproducoesFree    * 100.0 / totalReproducoes));
            System.out.printf("  - Premium: %d (%.0f%%)%n", reproducoesPremium,
                    (reproducoesPremium * 100.0 / totalReproducoes));
        }
        System.out.println();
        System.out.println("Anuncios exibidos (Free):   " + anunciosTotais);
        System.out.println("Downloads realizados (Prem): " + totalDownloads);
        System.out.println("Musicas no banco:            " + bancoMusicas.size());

        GeradorRecomendacoes.exibirEstatisticasGenero(bancoMusicas);
    }

    // ─────────────────────────────────────────────
    // PLAYLISTS AUTOMÁTICAS
    // ─────────────────────────────────────────────

    static void menuPlaylistsAutomaticas(Usuario u) {
        System.out.println("\n=== PLAYLISTS AUTOMATICAS ===");
        System.out.println("1. Top 5 Musicas");
        System.out.println("2. Recomendadas para Voce");
        System.out.println("3. Adicionadas Recentemente");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        int op = -1;
        try { op = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}

        String criterio, nomePlaylist;
        switch (op) {
            case 1: criterio = "top";          nomePlaylist = "Top 5 Musicas";           break;
            case 2: criterio = "recomendadas"; nomePlaylist = "Recomendadas para Voce";  break;
            case 3: criterio = "recentes";     nomePlaylist = "Adicionadas Recentemente"; break;
            case 0: return;
            default: System.out.println("Opcao invalida."); return;
        }

        if (bancoMusicas.isEmpty()) {
            System.out.println("Nenhuma musica no banco para gerar playlist.");
            return;
        }

        PlaylistAutomatica pa = GeradorRecomendacoes.gerar(
                criterio, nomePlaylist, bancoMusicas, u);

        System.out.print("Reproduzir agora? (1=Sim / outro=Nao): ");
        try {
            if (Integer.parseInt(scanner.nextLine()) == 1) {
                pa.reproduzir();
            }
        } catch (Exception e) {}
    }

    // ─────────────────────────────────────────────
    // AÇÕES COMPARTILHADAS
    // ─────────────────────────────────────────────

    static void reproduzirMusica(Usuario u) {
        if (bancoMusicas.isEmpty()) { System.out.println("Nenhuma musica cadastrada."); return; }
        listarMusicas();
        System.out.print("Escolha o numero da musica: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < bancoMusicas.size()) {
                u.reproduzirMusica(bancoMusicas.get(idx));
            } else {
                System.out.println("Musica nao encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
        }
    }

    static void baixarMusicaPremium(UsuarioPremium u) {
        if (bancoMusicas.isEmpty()) { System.out.println("Nenhuma musica cadastrada."); return; }
        listarMusicas();
        System.out.print("Escolha o numero da musica para baixar: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < bancoMusicas.size()) {
                u.baixar(bancoMusicas.get(idx));
            } else {
                System.out.println("Musica nao encontrada.");
            }
        } catch (Exception e) {
            System.out.println("Entrada invalida.");
        }
    }

    static void criarPlaylist(Usuario u) {
        System.out.print("Nome da playlist: ");
        String nome = scanner.nextLine();
        try {
            u.criarPlaylist(nome);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void gerenciarPlaylists(Usuario u) {
        System.out.println("\n=== GERENCIAR PLAYLISTS ===");
        System.out.println("1. Listar minhas playlists");
        System.out.println("2. Adicionar musica a uma playlist");
        System.out.println("3. Remover musica de uma playlist");
        System.out.println("4. Exibir detalhes de uma playlist");
        System.out.println("5. Reproduzir playlist");
        System.out.println("0. Voltar");
        System.out.print("Opcao: ");

        int op = -1;
        try { op = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}
        if (op == 0) return;
        if (op == 1) { u.listarPlaylists(); return; }

        u.listarPlaylists();
        if (u.getQuantidadePlaylists() == 0) return;

        System.out.print("Escolha o numero da playlist: ");
        int idxP = -1;
        try { idxP = Integer.parseInt(scanner.nextLine()) - 1; } catch (Exception e) {}
        Playlist p = u.getPlaylist(idxP);

        if (p == null) { System.out.println("Playlist nao encontrada."); return; }

        if (op == 2) {
            if (p instanceof PlaylistAutomatica) {
                System.out.println("Playlists automaticas nao aceitam adicao manual de musicas.");
                return;
            }
            listarMusicas();
            System.out.print("Escolha o numero da musica para adicionar: ");
            try {
                int idxM = Integer.parseInt(scanner.nextLine()) - 1;
                if (idxM >= 0 && idxM < bancoMusicas.size()) {
                    p.adicionarMusica(bancoMusicas.get(idxM));
                } else {
                    System.out.println("Musica nao encontrada.");
                }
            } catch (Exception e) { System.out.println("Entrada invalida."); }

        } else if (op == 3) {
            p.listarMusicas();
            System.out.print("Escolha o numero da musica para remover: ");
            try {
                int idxR = Integer.parseInt(scanner.nextLine()) - 1;
                p.removerMusica(idxR);
            } catch (Exception e) { System.out.println("Entrada invalida."); }

        } else if (op == 4) {
            p.listarMusicas();
            System.out.println("Total de musicas: " + p.getQuantidadeMusicas());
            System.out.println("Duracao total: "
                    + FormatadorTempo.formatarSegundosLongo(p.getDuracaoTotal()));
            if (p instanceof PlaylistAutomatica) {
                System.out.println("Criterio: " + ((PlaylistAutomatica) p).getCriterio());
            } else if (p instanceof PlaylistPersonalizada) {
                System.out.println("Criada por: " + ((PlaylistPersonalizada) p).getCriadorNome());
            }

        } else if (op == 5) {
            p.reproduzir();
        }
    }

    static void cadastrarMusica() {
        try {
            System.out.print("Titulo: ");
            String titulo  = scanner.nextLine();
            System.out.print("Artista: ");
            String artista = scanner.nextLine();
            System.out.print("Duracao (segundos): ");
            int duracao    = Integer.parseInt(scanner.nextLine());
            System.out.print("Genero (Pop, Rock, Jazz, Eletronica, Hip-Hop, Classica): ");
            String genero  = scanner.nextLine();

            bancoMusicas.add(new Musica(titulo, artista, duracao, genero));
            System.out.println("Musica cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Duracao invalida. Informe um numero inteiro.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    static void listarMusicas() {
        if (bancoMusicas.isEmpty()) {
            System.out.println("Nenhuma musica cadastrada.");
            return;
        }
        System.out.println("\n--- BANCO DE MUSICAS ---");
        for (int i = 0; i < bancoMusicas.size(); i++) {
            System.out.print((i + 1) + ". ");
            bancoMusicas.get(i).exibir();
        }
    }

    static void buscarMusica() {
        System.out.println("Buscar por: 1. Titulo | 2. Artista");
        System.out.print("Escolha: ");
        int tipo = -1;
        try { tipo = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}
        System.out.print("Busca: ");
        String busca = scanner.nextLine();

        boolean achou = false;
        for (Musica m : bancoMusicas) {
            if ((tipo == 1 && m.contemTitulo(busca))
                    || (tipo == 2 && m.contemArtista(busca))) {
                m.exibir();
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma musica encontrada para '" + busca + "'.");
    }

    static void fazerUpgradePremium(UsuarioFree free) {
        System.out.println("\n=== UPGRADE PARA PREMIUM ===");
        String plano     = escolherPlano();
        UsuarioPremium premium = new UsuarioPremium(free.getNome(), free.getEmail(), plano);
        int idx = usuarios.indexOf(free);
        if (idx >= 0) usuarios.set(idx, premium);
        usuarioLogado = premium;
        System.out.println("Upgrade realizado! Bem-vindo ao Premium (" + plano + ")!");
    }

    // ─────────────────────────────────────────────
    // UTILITÁRIOS
    // ─────────────────────────────────────────────

    static String escolherPlano() {
        System.out.println("Plano Premium:");
        System.out.println("1. Mensal   (R$ 19,90)");
        System.out.println("2. Anual    (R$ 199,00)");
        System.out.println("3. Familiar (R$ 29,90)");
        System.out.print("Escolha: ");
        int op = -1;
        try { op = Integer.parseInt(scanner.nextLine()); } catch (Exception e) {}
        switch (op) {
            case 1: return "Mensal";
            case 2: return "Anual";
            case 3: return "Familiar";
            default:
                System.out.println("Opcao invalida. Plano Mensal selecionado.");
                return "Mensal";
        }
    }

    static String lerStringNaoVazia(String prompt) {
        String valor = "";
        while (valor.isEmpty()) {
            System.out.print(prompt);
            valor = scanner.nextLine().trim();
            if (valor.isEmpty()) System.out.println("Campo obrigatorio.");
        }
        return valor;
    }

    // ─────────────────────────────────────────────
    // BANCO DE MÚSICAS INICIAL
    // ─────────────────────────────────────────────

    static void popularBancoDeMusicas() {
        try {
            bancoMusicas.add(new Musica("Bohemian Rhapsody", "Queen",       354, "Rock"));
            bancoMusicas.add(new Musica("Shape of You",      "Ed Sheeran",  234, "Pop"));
            bancoMusicas.add(new Musica("Blinding Lights",   "The Weeknd",  200, "Pop"));
            bancoMusicas.add(new Musica("God's Plan",        "Drake",       198, "Hip-Hop"));
            bancoMusicas.add(new Musica("So What",           "Miles Davis", 561, "Jazz"));
            bancoMusicas.add(new Musica("Levels",            "Avicii",      195, "Eletrônica"));
            bancoMusicas.add(new Musica("Fur Elise",         "Beethoven",   210, "Clássica"));
        } catch (Exception e) {
            System.out.println("Erro ao popular banco: " + e.getMessage());
        }
    }
}
