package andyquispesullon.com.example.demo.infra.security;
import andyquispesullon.com.example.demo.domain.user.User;
import andyquispesullon.com.example.demo.repository.userrepositorio;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;



@Component // Marca la clase como un componente gestionado por Spring.
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired // Inyección de dependencias para el TokenService.
    TokenService TokenService;
    @Autowired // Inyección de dependencias para el repositorio de usuarios.
    userrepositorio userrepositorio;

    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request); // Recupera el token del encabezado de la solicitud.
        var login = TokenService.validateToken(token); // Valida el token.

        if(login != null){
            User user = userrepositorio.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")); // Asigna el rol de usuario.
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities); // Autentica al usuario.
            SecurityContextHolder.getContext().setAuthentication(authentication); // Establece la autenticación en el contexto de seguridad.
        }
        filterChain.doFilter(request, response); // Continúa con el siguiente filtro.
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization"); // Obtiene el encabezado de autorización.
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", ""); // Elimina el prefijo "Bearer " del token.
    }
}
