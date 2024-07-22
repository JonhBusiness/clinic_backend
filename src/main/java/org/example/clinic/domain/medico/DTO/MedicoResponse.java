package org.example.clinic.domain.medico.DTO;

import org.example.clinic.domain.medico.Direccion;
import org.example.clinic.domain.medico.Medico;
import org.springframework.http.ResponseEntity;

public record MedicoResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        String especialidad,
        Direccion direccion
) {

    public MedicoResponse(Medico newMedico) {
        this(newMedico.getId(),
                newMedico.getNombre(),
                newMedico.getEmail(),
                newMedico.getTelefono(),
                newMedico.getDocumento(),
                newMedico.getEspecialidad().name(),
                newMedico.getDireccion());
    }
}
