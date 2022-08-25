package ru.kadimov.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import ru.kadimov.rest.entity.User;

import java.util.List;

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;

    private HttpHeaders requestHeaders;

    private final String URL = "http://94.198.50.185:7081/api/users";

    public List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                });
        HttpHeaders headers = responseEntity.getHeaders();
        String set_cookie = headers.getFirst("Set-Cookie");
        requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", set_cookie);
        return  responseEntity.getBody();
    }

    public void saveUser(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user, requestHeaders);

        restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);

        HttpEntity<User> entity2 = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL,
                HttpMethod.GET, entity2, new ParameterizedTypeReference<List<User>>() {
                });
        System.out.println("User " + user + " was added");
    }

    public void updateUser(@RequestBody User user) {
        HttpEntity<User> entity = new HttpEntity<User>(user, requestHeaders);
        restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println("User " + user + " was updated");
    }

    public void deleteUser(Long id) {
        HttpEntity<User> entity = new HttpEntity<User>(requestHeaders);
        restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        System.out.println("User with ID " + id + " was deleted from DB");
    }
}




