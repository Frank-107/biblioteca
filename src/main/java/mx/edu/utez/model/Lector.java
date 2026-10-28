package mx.edu.utez.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.cache.spi.entry.StructuredCacheEntry;

@Table(name = "lector")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private boolean activo = true;

    private boolean membresiaPremium = true;
}
