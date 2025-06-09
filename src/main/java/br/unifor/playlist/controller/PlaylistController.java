package br.unifor.playlist.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.unifor.playlist.model.Playlist;
import br.unifor.playlist.repository.PlaylistRepository;
import br.unifor.playlist.service.MusicaService;
import br.unifor.playlist.model.Musica;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository repository;

    @Autowired
    private MusicaService musicaService;

    @GetMapping("/musicas")
    public Musica[] getMusicas() {
        return musicaService.listarMusicas();
    }

    @PostMapping
    public ResponseEntity<Playlist> criarPlaylist(@RequestBody Playlist playlist) {
        if (playlist.getNome() == null || playlist.getNome().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Playlist salva = repository.save(playlist);
        return ResponseEntity.status(201).body(salva);
    }
    @GetMapping
    public List<Playlist> listarPlaylists() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> buscarPlaylist(@PathVariable String id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPlaylist(@PathVariable String id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{playlistId}/musicas")
    public ResponseEntity<?> adicionarMusica(@PathVariable String playlistId, @RequestBody Map<String, String> body) {
        String musicaId = body.get("musicaId");

        if (musicaId == null || musicaId.isBlank()) {
            return ResponseEntity.badRequest().body("O campo 'musicaId' é obrigatório.");
        }

        try {
            Musica musica = musicaService.buscarMusicaPorId(musicaId);
            if (musica == null) {
                return ResponseEntity.status(404).body("Música com o ID " + musicaId + " não encontrada.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(503).body("Serviço de músicas indisponível.");
        }

        return repository.findById(playlistId).map(playlist -> {
            if (!playlist.getMusicas().contains(musicaId)) {
                playlist.getMusicas().add(musicaId);
                repository.save(playlist);
            }
            return ResponseEntity.ok(playlist);
        }).orElse(ResponseEntity.status(404).body("Playlist com o ID " + playlistId + " não encontrada."));
    }
}