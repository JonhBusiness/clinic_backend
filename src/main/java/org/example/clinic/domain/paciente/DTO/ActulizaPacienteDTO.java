package org.example.clinic.domain.paciente.DTO;

import jakarta.validation.constraints.NotNull;
import org.example.clinic.domain.medico.Direccion;

public record ActulizaPacienteDTO(
        @NotNull
        Long id,
        String nombre,
        String telefono,
        Direccion direccion
) {
}
