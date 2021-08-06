package net.inpercima.cryptocheck.service;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService() {
        restTemplate = new RestTemplate();
    }

    public <T> HttpEntity<T> getEntity(final String url, final String apiKey, final Class<T> clazz) {
        return restTemplate.exchange(url, HttpMethod.GET, createHttpEntity(apiKey), clazz);
    }

    private static <T> HttpEntity<T> createHttpEntity(final String apiKey) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-API-KEY", apiKey);
        final HttpEntity<T> entity = new HttpEntity<>(headers);
        return entity;
    }
}
