package net.inpercima.cryptocheck.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Value("${app.bitpanda.apikey}")
    private String bitpandaApiKey;

    private final RestTemplate restTemplate;

    private static final String BITPANDA_BASE_URL = "https://api.bitpanda.com/v1";

    public RestService() {
        restTemplate = new RestTemplate();
    }

    public <T> ResponseEntity<T> getData(final String url, final Class<T> clazz) {
        return restTemplate.exchange(BITPANDA_BASE_URL + url, HttpMethod.GET, createHttpEntity(bitpandaApiKey), clazz);
    }

    private static <T> HttpEntity<T> createHttpEntity(final String apiKey) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-API-KEY", apiKey);
        final HttpEntity<T> entity = new HttpEntity<>(headers);
        return entity;
    }
}
