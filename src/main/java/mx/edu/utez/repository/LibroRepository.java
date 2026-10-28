package mx.edu.utez.repository;

import mx.edu.utez.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {



}
