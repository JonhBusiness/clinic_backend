package org.example.clinic.service.interfaces;

import org.example.clinic.domain.consulta.DTO.CancelamientoConsultaDTO;

public interface IValidaCancelamiento {
    void validar(CancelamientoConsultaDTO datos);
}
