package br.unifor.playlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.unifor.playlist.model.Playlist;
import br.unifor.playlist.repository.PlaylistRepository;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository repository;

    // Criar nova playlist
    @PostMapping
    public ResponseEntity<Playlist> criarPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getNome() == null || playlist.getNome().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Playlist salva = repository.save(playlist);
        return ResponseEntity.status(201).body(salva);
    }

    // Listar todas as playlists
    @GetMapping
    public List<Playlist> listarPlaylists() {
        return repository.findAll();
    }

    // Buscar uma playlist pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> buscarPlaylist(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar uma playlist
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> atualizarPlaylist(@PathVariable String id, @RequestBody Playlist nova) {
        return repository.findById(id).map(p -> {
            p.setNome(nova.getNome());
            p.setDescricao(nova.getDescricao());
            p.setMusicas(nova.getMusicas());
            p.setCover(nova.getCover());
            return ResponseEntity.ok(repository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Deletar uma playlist
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPlaylist(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
