package org.example.clinic.service.interfaces;

import jakarta.validation.ValidationException;
import org.example.clinic.domain.consulta.DTO.AgendaConsultaDTO;

public interface IValidaConsultas {
    public void validar(AgendaConsultaDTO datos) throws ValidationException;
}
