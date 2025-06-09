package br.unifor.playlist.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.unifor.playlist.model.Musica;

@Service
public class MusicaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String musicaApiUrl = "https://unimusica-production.up.railway.app";


    public Musica[] listarMusicas() {
        String url = musicaApiUrl + "/musicas";
        return restTemplate.getForObject(url, Musica[].class);
    }
    public Musica buscarMusicaPorId(String id) {
        String url = musicaApiUrl + "/musicas/" + id;
        try {

            return restTemplate.getForObject(url, Musica.class);
        } catch (HttpClientErrorException.NotFound e) {

            return null;
        }
    }
}