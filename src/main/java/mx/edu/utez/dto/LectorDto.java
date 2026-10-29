package mx.edu.utez.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LectorDto {

    @NotNull(message = "no puede ir vacio el id")
    private Long Idlector;
}
