package andyquispesullon.com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface userrepositorio extends JpaRepository<andyquispesullon.com.example.demo.domain.user.User, String> {
    Optional<andyquispesullon.com.example.demo.domain.user.User> findByEmail(String email);
}
