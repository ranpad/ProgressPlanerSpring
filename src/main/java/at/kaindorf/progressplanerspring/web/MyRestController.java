package at.kaindorf.progressplanerspring.web;

import at.kaindorf.progressplanerspring.pojos.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api")
public class MyRestController {

    @PostMapping("/login")
    public ResponseEntity<Customer> login(/*@RequestBody Customer customer*/){
        return ResponseEntity.ok().build();
    }
}
