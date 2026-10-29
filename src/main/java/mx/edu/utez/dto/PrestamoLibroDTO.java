package mx.edu.utez.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrestamoLibroDTO {
    @NotNull(message = "El id del lector no puede estar vacío")
    private Long idLector ;
    @NotNull(message = "El id del libro no puede estar vacío")
    private Long idLibro ;
}
