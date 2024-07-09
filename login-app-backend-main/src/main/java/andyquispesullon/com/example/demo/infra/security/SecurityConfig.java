package andyquispesullon.com.example.demo.infra.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Indica que esta clase contiene configuraciones de Spring.
@EnableWebSecurity // Habilita la configuración de seguridad web.
public class SecurityConfig {
    @Autowired // Inyección de dependencias para el filtro de seguridad.
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactiva CSRF.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura la política de sesión como stateless.
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Permite el acceso sin autenticación a la ruta de login.
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() // Permite el acceso sin autenticación a la ruta de registro.
                        .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud.
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class); // Añade el filtro de seguridad personalizado antes del filtro de autenticación por nombre de usuario y contraseña.
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define el codificador de contraseñas como BCrypt.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager(); // Provee el gestor de autenticación.
    }
}
