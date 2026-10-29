package mx.edu.utez.controller;

import jakarta.validation.Valid;
import mx.edu.utez.dto.DevolverLibroDTO;
import mx.edu.utez.dto.LectorDto;
import mx.edu.utez.dto.PrestamoLibroDTO;
import mx.edu.utez.model.Lector;
import mx.edu.utez.model.Prestamo;
import mx.edu.utez.service.BibliotecaService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/")
@CrossOrigin("*")

public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping("/prestamo")
    public ResponseEntity<Prestamo> hacerPrestamo(@RequestBody @Valid PrestamoLibroDTO prestamoLibroDTO) {

        return ResponseEntity.status(201).body(bibliotecaService.hacerPrestamo(prestamoLibroDTO));


 }

 @PutMapping("/devolver")
 public ResponseEntity<Prestamo> devolverPrestamo(@RequestBody @Valid DevolverLibroDTO payload) {
        return ResponseEntity.status(201).body(bibliotecaService.devolverPrestamo(payload));
 }

 @PutMapping("/renovar")
 public ResponseEntity<Prestamo> renovarPrestamo (@RequestBody @Valid DevolverLibroDTO payload){
        return ResponseEntity.status(201).body(bibliotecaService.renovarPrestamo(payload));
 }

 @PutMapping("/rehabilitarLector")
    public ResponseEntity<Lector> reahabilitarLector(@Valid @RequestBody LectorDto payload){
        return ResponseEntity.status(201).body(bibliotecaService.rehabilitarLector(payload));

 }

 @GetMapping("/obtener/{idPrestamo}")
    public ResponseEntity<List<Prestamo>> obtenerPrestamos(@PathVariable Long idPrestamo){
        return ResponseEntity.status(201).body(bibliotecaService.obtenerPrestamos(idPrestamo));
 }







}
