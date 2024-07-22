package org.example.clinic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import org.example.clinic.domain.consulta.DTO.AgendaConsultaDTO;
import org.example.clinic.infra.exceptions.ValidacionException;
import org.example.clinic.service.interfaces.IValidaConsultas;
import org.springframework.http.HttpStatus;

import java.time.DayOfWeek;

public class HorariosDeAtencion implements IValidaConsultas {
    @Override
    public void validar(AgendaConsultaDTO datos) throws ValidacionException {
        var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());

        var antesdDeApertura=datos.fecha().getHour()<7;
        var despuesDeCierre=datos.fecha().getHour()>19;
        if(domingo || antesdDeApertura || despuesDeCierre){
            throw  new ValidacionException("El horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
