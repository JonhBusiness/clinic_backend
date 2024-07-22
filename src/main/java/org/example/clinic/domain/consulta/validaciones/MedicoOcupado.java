package org.example.clinic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import org.example.clinic.domain.consulta.ConsultaRepository;
import org.example.clinic.domain.consulta.DTO.AgendaConsultaDTO;

import org.example.clinic.infra.exceptions.ValidacionException;
import org.example.clinic.service.interfaces.IValidaConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MedicoOcupado implements IValidaConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;

    @Override
    public void validar(AgendaConsultaDTO datos) {
        if (consultaRepository.existsByMedicoNombreAndFecha(datos.nombreMedico(), datos.fecha())) {
            throw new ValidacionException("Este medico ya tiene una consulta en ese horario");
        }
    }
}