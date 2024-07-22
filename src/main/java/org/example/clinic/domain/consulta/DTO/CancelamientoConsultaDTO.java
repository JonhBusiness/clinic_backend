package org.example.clinic.domain.consulta.DTO;

import jakarta.validation.constraints.NotNull;
import org.example.clinic.domain.consulta.MotivoCancelamiento;

public record CancelamientoConsultaDTO(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelamiento motivo
) {
}
