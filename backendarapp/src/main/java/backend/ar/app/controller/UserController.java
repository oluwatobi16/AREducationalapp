package backend.ar.app.controller;

import backend.ar.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import backend.ar.app.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam Integer id){
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestParam String name,
                                          @RequestParam String academicSubject,
                                          @RequestParam String email,
                                          @RequestParam String password){
        User registeredUser = userService.registerUser(name, academicSubject, email, password);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/applogin")
    public ResponseEntity<?> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = userService.login(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            // Return a ResponseEntity with a String body and the UNAUTHORIZED status
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }




}
