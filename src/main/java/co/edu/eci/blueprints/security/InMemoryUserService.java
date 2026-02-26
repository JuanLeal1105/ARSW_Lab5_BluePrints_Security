package co.edu.eci.blueprints.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class InMemoryUserService {
    private final Map<String, String> users; // username -> hash
    private final PasswordEncoder encoder;

    public InMemoryUserService(PasswordEncoder encoder) {
        this.encoder = encoder;
        this.users = Map.of(
            "student", encoder.encode("student123"),
            "assistant", encoder.encode("assistant123")
                , "admin", encoder.encode("admin123")
        );
    }

    public boolean isValid(String username, String rawPassword) {
        String hash = users.get(username);
        return hash != null && encoder.matches(rawPassword, hash);
    }

    public String getScopeByUsername (String userCase){
        switch (userCase){
            case "admin":
                return "blueprints.read blueprints.write blueprints.create blueprints.update";
            case "student":
                return "blueprints.read";
            case "assistant":
                return "blueprints.read blueprints.write";
            default:
                return "";

        }
    }
}
