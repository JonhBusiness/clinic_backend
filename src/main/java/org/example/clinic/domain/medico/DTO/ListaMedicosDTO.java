package org.example.clinic.domain.medico.DTO;

import org.example.clinic.domain.medico.Direccion;
import org.example.clinic.domain.medico.Medico;
import org.springframework.data.domain.Page;

public record ListaMedicosDTO(Long id,
                              String nombre,
                              String especialidad,
                              String documento,
                              String email,
                              String telefono,
                              Direccion direccion) {

    public ListaMedicosDTO(Medico medico) {
        this(medico.getId(),
                medico.getNombre(),
                medico.getEspecialidad().name(),
                medico.getDocumento(),
                medico.getEmail(),
                medico.getTelefono(),
                medico.getDireccion());
    }
}
