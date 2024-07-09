package andyquispesullon.com.example.demo.infra.security;

import andyquispesullon.com.example.demo.domain.user.User;
import andyquispesullon.com.example.demo.repository.userrepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

@Component // Marca la clase como un componente gestionado por Spring.
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired // Inyección de dependencias para el repositorio de usuarios.
    private userrepositorio repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found")); // Busca el usuario por email.
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>()); // Retorna UserDetails con email y contraseña.
    }
}

