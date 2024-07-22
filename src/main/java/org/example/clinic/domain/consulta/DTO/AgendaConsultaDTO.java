package org.example.clinic.domain.consulta.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.example.clinic.domain.medico.Especialidad;

import java.time.LocalDateTime;

public record AgendaConsultaDTO(
        Long id,
        @NotNull
        String nombrePaciente,
        String nombreMedico,
        @NotNull
        @Future
        LocalDateTime fecha
//        Especialidad especialidad
) {
}
