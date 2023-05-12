package at.kaindorf.progressplanerspring.web;

import at.kaindorf.progressplanerspring.pojos.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class MyRestController {


    @GetMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody Customer customer){
        return ResponseEntity.ok().build();
    }

}
