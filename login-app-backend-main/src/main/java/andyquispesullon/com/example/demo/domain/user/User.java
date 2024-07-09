package andyquispesullon.com.example.demo.domain.user;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // Define la clase como entidad JPA.
@Table(name = "users") // Nombre de la tabla en la base de datos.
@Getter // Genera getters.
@Setter // Genera setters.
@AllArgsConstructor // Constructor con todos los argumentos.
@NoArgsConstructor // Constructor sin argumentos.
public class User {
    @Id // Clave primaria.
    @GeneratedValue(strategy = GenerationType.UUID) // Genera UUID.
    private String id;
    private String name;
    private String email;
    private String password;
}
