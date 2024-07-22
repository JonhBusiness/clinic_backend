package org.example.clinic.domain.paciente.DTO;

import org.example.clinic.domain.medico.Direccion;
import org.example.clinic.domain.paciente.Paciente;

public record ListaPacienteDTO(Long id,
                               String nombre,
                               String email,
                               String documento,
                               String telefono ,
                               Direccion direccion) {
    public ListaPacienteDTO(Paciente paciente) {
        this(paciente.getId(),
                paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento(),
                paciente.getTelefono(),
                paciente.getDireccion());
    }
}
