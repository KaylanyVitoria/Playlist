package br.unifor.playlist.service;

import br.unifor.playlist.model.Musica;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MusicaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String musicaApiUrl = "https://unimusica-production.up.railway.app/musicas";

    public Musica[] listarMusicas() {
        return restTemplate.getForObject(musicaApiUrl, Musica[].class);
    }
}
