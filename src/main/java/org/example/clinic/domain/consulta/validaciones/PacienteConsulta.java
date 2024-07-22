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
public class PacienteConsulta implements IValidaConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(AgendaConsultaDTO datos) throws ValidacionException {
        var primerHorario = datos.fecha().withHour(7);
        var ultimoHorario= datos.fecha().withHour(18);

        var pacienteConConsulta=consultaRepository.existsByPacienteNombreAndFechaBetween(datos.nombrePaciente(),primerHorario,ultimoHorario);

        if(pacienteConConsulta){
            throw new ValidacionException("el paciente ya tiene una consulta para ese dia");
        }

    }
}
