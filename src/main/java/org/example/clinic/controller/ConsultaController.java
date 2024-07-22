package org.example.clinic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.example.clinic.domain.consulta.DTO.AgendaConsultaDTO;
import org.example.clinic.domain.consulta.DTO.CancelamientoConsultaDTO;
import org.example.clinic.domain.medico.DTO.ActulizaMedicoDTO;
import org.example.clinic.service.ConsultaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
//@SuppressWarnings("all")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    @Operation(
            summary = "Obtiene el listado de Consultas",
            description = "",
            tags = { "Consulta", "Get" })
    public ResponseEntity<Page<?>> consultas(@PageableDefault(size = 20,sort = {"fecha"}) Pageable pageable) {
       var response=consultaService.getAll(pageable);
       return ResponseEntity.ok(response);
    }
@PostMapping("/registrar")
@Operation(
        summary = "registra una Consulta en la base de datos",
        description = "",
        tags = { "Consulta", "Post" })
    public ResponseEntity<?> registrar(@Valid @RequestBody AgendaConsultaDTO consulta) {
    try {
        System.out.println("Datos recibidos: "+consulta); //Datos recibidos: AgendaConsultaDTO[id=null, nombrePaciente=Luis Fernández, nombreMedico=Maria Lopez, fecha=2024-10-25T11:00]
        var response = consultaService.agendar(consulta);
        return ResponseEntity.ok(response);
    } catch (ValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    @PutMapping("/actualizar")
    @Operation(
            summary = "actualiza una Consulta en la base de datos",
            description = "",
            tags = { "Consulta", "Put" })
    public ResponseEntity<?> actualizarConsulta(@RequestBody @Valid AgendaConsultaDTO consulta){
        try {

            var response = consultaService.actulizarAgenda(consulta);
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
@DeleteMapping("/cancelar")
@Operation(
        summary = "Desactiva la Consulta en la base de datos",
        description = "",
        tags = { "Consulta", "Delete" })
public ResponseEntity<?> cancelar(@Valid @RequestBody CancelamientoConsultaDTO consulta) {
    var response=consultaService.cancelarCita(consulta);

    return ResponseEntity.ok(response);
}

//    @GetMapping("/medicos-activos")
//    public ResponseEntity<Page<?>> obtenerMedicosDisponibles(@PageableDefault(sort = {"nombre"}) Pageable pageable) {
//        var medicosDisponibles = consultaService.obtenerMedicosActivos(pageable);
//        return ResponseEntity.ok(medicosDisponibles);
//    }


}
