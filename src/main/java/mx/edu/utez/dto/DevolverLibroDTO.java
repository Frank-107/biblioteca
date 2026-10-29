package mx.edu.utez.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DevolverLibroDTO {

    @NotNull(message = "El id del prestamo no puede estar vacío")
    private Long idPrestamo;

}
