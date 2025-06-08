package br.unifor.playlist.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "playlists")
public class Playlist {

    @Id
    private String id;
    private String nome;
    private String descricao;
    private String cover; // URL da imagem de capa
    private List<String> musicas; // Lista de IDs de músicas

    public Playlist() {}

    public Playlist(String nome, String descricao, String cover, List<String> musicas) {
        this.nome = nome;
        this.descricao = descricao;
        this.cover = cover;
        this.musicas = musicas;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }

    public List<String> getMusicas() { return musicas; }
    public void setMusicas(List<String> musicas) { this.musicas = musicas; }
}
