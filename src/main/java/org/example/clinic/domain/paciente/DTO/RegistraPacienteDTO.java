package org.example.clinic.domain.paciente.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.example.clinic.domain.medico.Direccion;

public record RegistraPacienteDTO(
        @NotBlank
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 0, max = 20)
        String telefono,

//        @Pattern(regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\.-\\d{2}")
        @Pattern(regexp = "\\d{6}")
        @NotBlank
        String documento,

        @NotNull @Valid
        Direccion direccion

) {
}
