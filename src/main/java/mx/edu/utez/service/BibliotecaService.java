package mx.edu.utez.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.edu.utez.dto.DevolverLibroDTO;
import mx.edu.utez.dto.LectorDto;
import mx.edu.utez.dto.PrestamoLibroDTO;
import mx.edu.utez.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.model.Estado;
import mx.edu.utez.model.Lector;
import mx.edu.utez.model.Libro;
import mx.edu.utez.model.Prestamo;
import mx.edu.utez.repository.LectorRepository;
import mx.edu.utez.repository.LibroRepository;
import mx.edu.utez.repository.PrestamoRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BibliotecaService {
    private final LibroRepository libroRepository;
    private final PrestamoRepository prestamoRepository;
    private final LectorRepository lectorRepository;


    @Transactional
    public Prestamo hacerPrestamo(PrestamoLibroDTO payload) {

        Optional<Lector> lectorOp = lectorRepository.findById(payload.getIdLector());
        Optional<Libro> libroOp = libroRepository.findById(payload.getIdLibro());

        if(!lectorOp.isPresent()){
            throw new CustomBadRequestException("Lector no encontrado");
        }
        if(!libroOp.isPresent()){
            throw new CustomBadRequestException("Libro no encontrado");
        }
        Libro libro = libroOp.get();
        Lector lector = lectorOp.get();
        if(libro.getEjemplaresDisponibles() <= 0){
            throw new CustomBadRequestException("No hay ejemplares disponibles");
        }

        Long prestamosActivos = prestamoRepository.countByLectorIdAndEstadoIn(lector.getId(), List.of(Estado.PRESTADO, Estado.VENCIDO));
        int limiteMaximoPrestamos = 3;
        if(lector.isMembresiaPremium()){
            limiteMaximoPrestamos = 5;
        }

        if(prestamosActivos >= limiteMaximoPrestamos){
            throw new CustomBadRequestException("El lector ha alcanzado el límite máximo de préstamos activos");
        }

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles()-1);
        libroRepository.save(libro);

        Prestamo prestamo = new Prestamo();
        prestamo.setLector(lector);
        prestamo.setLibro(libro);
        prestamo.setEstado(Estado.PRESTADO);
        LocalDate hoy = LocalDate.now();
        prestamo.setFechaPrestamo(hoy);
        prestamo.setFechaDevolucionMaxima(hoy.plusDays(14));

        prestamoRepository.save(prestamo);
        return prestamo;

    }

    @Transactional
    public Prestamo devolverPrestamo(DevolverLibroDTO payload){
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(payload.getIdPrestamo());
        if(prestamoOptional.isEmpty()){
            throw new CustomBadRequestException("Prestamo no encontrado");
        }
        Prestamo prestamo = prestamoOptional.get();
        if(prestamo.getEstado() == Estado.DEVUELTO){
            throw new CustomBadRequestException("El prestamo ya ha sido devuelto");
        }
        prestamo.setEstado(Estado.DEVUELTO);



        Lector lector = prestamo.getLector();
        Libro libro = prestamo.getLibro();

        libro.setEjemplaresDisponibles(libro.getEjemplaresDisponibles()+1);

        LocalDate hoy = LocalDate.now();
        if(hoy.isAfter(prestamo.getFechaDevolucionMaxima())){
            lector.setActivo(false);
            lectorRepository.save(lector);
        }
        libroRepository.save(libro);
        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo renovarPrestamo(DevolverLibroDTO payload){
        Optional<Prestamo> prestamoOptional = prestamoRepository.findById(payload.getIdPrestamo());
        if(prestamoOptional.isEmpty()){
            throw new CustomBadRequestException("el prestamo no existe");
        }

        Prestamo prestamo = prestamoOptional.get();
        if(prestamo.getEstado() != Estado.PRESTADO){
            throw new CustomBadRequestException("El libro prestado no puede ser aplazado mas tiempo");
        }
        prestamo.setFechaDevolucionMaxima(prestamo.getFechaDevolucionMaxima().plusDays(7));
        return prestamoRepository.save(prestamo);

    }

    @Transactional
    public Lector rehabilitarLector(LectorDto payload){
        Optional<Lector> lectorOptional = lectorRepository.findById(payload.getIdlector());

        if(lectorOptional.isEmpty()){
            throw new CustomBadRequestException("El lector no existe");
        }
        Lector lector = lectorOptional.get();
        if(lector.isActivo()){
            throw new CustomBadRequestException("El lector esta activo");
        }
        lector.setActivo(true);
        return lectorRepository.save(lector);

    }


    @Transactional
    public List<Prestamo> obtenerPrestamos(Long lectorId){
        Optional<Lector> lectorOptional = lectorRepository.findById(lectorId);
        if(lectorOptional.isEmpty()){
            throw new CustomBadRequestException("El lector no existe");
        }

        List<Prestamo> prestamos = prestamoRepository.findByLectorIdAndEstadoIn(lectorId, List.of(Estado.PRESTADO,Estado.VENCIDO));
        if(prestamos.isEmpty()){
            throw new CustomBadRequestException("El usuario no tiene prestamos activos");
        }
        return prestamos;
    }
}
