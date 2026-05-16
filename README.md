# 🎵 Sistema de Streaming de Música

## 📋 Funcionalidades

- **Cadastro de usuários** Free e Premium com validação de e-mail único
- **Login / Logout** com suporte a múltiplos usuários simultâneos
- **Cadastro e busca de músicas** por título ou artista
- **Reprodução de músicas** com comportamento diferente por tipo de usuário
- **Histórico de reprodução** individual por usuário
- **Playlists personalizadas** (criadas manualmente pelo usuário)
- **Playlists automáticas** geradas por critério: Top 5, Recomendadas e Recentes
- **Sistema de downloads** exclusivo para usuários Premium (offline)
- **Limite de reproduções e anúncios** para usuários Free
- **Upgrade Free → Premium** sem perda de dados
- **Estatísticas do sistema** com distribuição por gênero musical

---

## 🏗️ Arquitetura

### Estrutura de Pacotes

```
src/
└── br/com/streaming/
    ├── modelo/          # Entidades do domínio
    │   ├── ItemReproducao.java      (classe abstrata)
    │   ├── Musica.java
    │   ├── Playlist.java
    │   ├── PlaylistAutomatica.java
    │   ├── PlaylistPersonalizada.java
    │   ├── Usuario.java
    │   ├── UsuarioFree.java
    │   └── UsuarioPremium.java
    ├── servico/         # Interfaces e serviços
    │   ├── Reproduzivel.java        (interface)
    │   ├── Baixavel.java            (interface)
    │   └── GeradorRecomendacoes.java
    ├── util/            # Utilitários reutilizáveis
    │   ├── Validador.java
    │   └── FormatadorTempo.java
    └── principal/       # Ponto de entrada
        └── StreamingMusica.java
```

### Conceitos de POO Aplicados

| Conceito | Onde foi aplicado |
|---|---|
| **Encapsulamento** | Atributos `private`/`protected` com getters/setters validados em todas as classes |
| **Herança** | `Musica` e `Playlist` estendem `ItemReproducao`; `UsuarioFree` e `UsuarioPremium` estendem `Usuario`; `PlaylistAutomatica` e `PlaylistPersonalizada` estendem `Playlist` |
| **Polimorfismo** | `reproduzirMusica()` e `reproduzir()` com comportamento distinto por subtipo; lista polimórfica `ArrayList<Usuario>` |
| **Abstração** | Classe abstrata `ItemReproducao` define contrato para Musica e Playlist |
| **Interface `Reproduzivel`** | Implementada por `ItemReproducao` (e portanto por `Musica` e `Playlist`) — contrato de `reproduzir()`, `pausar()`, `parar()`, `getDuracaoTotal()` |
| **Interface `Baixavel`** | Implementada por `UsuarioPremium` — contrato de `baixar()`, `removerDownload()`, `estaBaixada()`, `getTamanhoBaixados()` |
| **Sobrecarga (Overloading)** | `reproduzirMusica(Musica)` e `reproduzirMusica(Musica, boolean)` em `UsuarioFree` e `UsuarioPremium` |
| **`instanceof` + casting** | Menus e estatísticas diferenciam Free/Premium e PlaylistAutomatica/Personalizada |
| **`final`** | `setEmail()` em `Usuario` não pode ser sobrescrito por subclasses |

---

## 🚀 Como Executar

### Pré-requisitos

- Java 11 ou superior instalado
- Terminal / prompt de comando

### Compilar

```bash
# Na raiz do projeto (onde está a pasta src/)
mkdir -p out
javac -d out -sourcepath src $(find src -name "*.java")
```

### Executar

```bash
java -cp out br.com.streaming.principal.StreamingMusica
```

---

## 👤 Autor

- **Nome:** Pedro Henrique Maia Jordão
- **RA:** 45265844

---

## 📅 Histórico de Checkpoints

| CP | Tema | Conteúdo Principal |
|---|---|---|
| CP1 | Classes e Objetos | `Musica`, construtores, encapsulamento básico |
| CP2 | Herança | `Playlist`, `PlaylistAutomatica`, `PlaylistPersonalizada` |
| CP3 | Polimorfismo | `Usuario`, `UsuarioFree`, `UsuarioPremium`, `reproduzirMusica()` |
| CP4 | Coleções e ArrayList | Banco de músicas, histórico, listas polimórficas |
| CP5 | Sistema completo | Menu interativo, multi-usuário, estatísticas |
| CP6 | Interfaces e Pacotes | `Reproduzivel`, `Baixavel`, `ItemReproducao`, pacotes `br.com.streaming.*` |
