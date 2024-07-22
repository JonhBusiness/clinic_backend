package org.example.clinic.domain.medico.DTO;

import jakarta.validation.constraints.NotNull;
import org.example.clinic.domain.medico.Direccion;
import org.example.clinic.domain.medico.Especialidad;

public record ActulizaMedicoDTO(@NotNull Long id,
                                String nombre,
                                Especialidad especialidad,
                                String telefono,
                                Direccion direccion) {
}
