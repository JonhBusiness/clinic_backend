package org.example.clinic.domain.consulta.DTO;

import org.example.clinic.domain.consulta.Consulta;

import java.time.LocalDateTime;

public record ConsultaResponse(
        Long id,
        String nombreMedico,
        String especialidadMedico,
        String documentoMedico,
        String nombrePaciente,
        LocalDateTime fecha
) {
    public ConsultaResponse(Consulta c) {
        this(
                c.getId(),
                c.getMedico().getNombre(),
                c.getMedico().getEspecialidad().name(),
                c.getMedico().getDocumento(),
                c.getPaciente().getNombre(),
                c.getFecha());
    }
}
