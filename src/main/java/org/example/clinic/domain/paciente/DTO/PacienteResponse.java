package org.example.clinic.domain.paciente.DTO;

import org.example.clinic.domain.medico.Direccion;
import org.example.clinic.domain.paciente.Paciente;

public record PacienteResponse(
        Long id,
        String nombre,
        String email,
        String documento,
        String telefono,
        Direccion direccion
) {
    public PacienteResponse(Paciente newPaciente) {
        this(newPaciente.getId(),
                newPaciente.getNombre(),
                newPaciente.getEmail(),
                newPaciente.getDocumento(),
                newPaciente.getTelefono(),
                newPaciente.getDireccion());
    }
}
