package mx.edu.utez.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDate;

@Entity
@Table(name = "prestamo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lector_id", nullable = false)
    private Lector lector;
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucionMaxima;

    @Enumerated(EnumType.STRING)
    private Estado estado;

}
