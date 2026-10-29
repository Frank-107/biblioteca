package mx.edu.utez.repository;

import mx.edu.utez.model.Estado;
import mx.edu.utez.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    Long countByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);

    List<Prestamo> findByLectorIdAndEstadoIn(Long lectorId, List<Estado> estados);


}
