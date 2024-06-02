package backend.ar.app.service;

import backend.ar.app.model.User;
import backend.ar.app.model.Authentication;
import backend.ar.app.Repository.UserRepository;
import backend.ar.app.Repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationRepository authenticationRepository) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
    }

    public User registerUser(String name, String academicSubject, String email, String rawPassword) {
        // Create a new User object
        User newUser = new User();

        newUser.setName(name);
        newUser.setAcademicSubject(academicSubject);
        newUser.setEmail(email);

        // Save the User object to the database
        newUser = userRepository.save(newUser);

        // Create a new Authentication object
        Authentication auth = new Authentication();
        auth.setUser(newUser);
        // Create a new instance of BCryptPasswordEncoder to encode the raw password
        auth.setPassword(new BCryptPasswordEncoder().encode(rawPassword));

        // Save the Authentication object to the database
        authenticationRepository.save(auth);

        return newUser;
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            Authentication auth = authenticationRepository.findById(user.getId()).orElse(null);
            if (auth != null && new BCryptPasswordEncoder().matches(password, auth.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


}
