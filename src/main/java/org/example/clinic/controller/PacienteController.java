package org.example.clinic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.clinic.domain.paciente.DTO.ActulizaPacienteDTO;
import org.example.clinic.domain.paciente.DTO.RegistraPacienteDTO;
import org.example.clinic.domain.paciente.Paciente;
import org.example.clinic.service.PacienteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
//@SuppressWarnings("all")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    @Operation(
            summary = "Obtiene el listado de Pacientes",
            description = "",
            tags = { "Paciente", "Get" })
    public ResponseEntity<Page<?>> getPacientes(@PageableDefault(page = 0, size = 20 ,sort = {"nombre"}) Pageable pageable) {
        Page<?> pacientes=pacienteService.getAll(pageable);
        return ResponseEntity.ok(pacientes);
    }
    @PostMapping("/registrar")
    @Operation(
            summary = "registra un Paciente en la base de datos",
            description = "",
            tags = { "Paciente", "Post" })
    public ResponseEntity<?> registrar(@RequestBody @Valid RegistraPacienteDTO paciente,
                                       UriComponentsBuilder uriBuilder) {
        return pacienteService.registrar(paciente,uriBuilder);
    }
    @PutMapping("/actualizar")
    @Operation(
            summary = "actualiza un Paciente en la base de datos",
            description = "",
            tags = { "Paciente", "Put" })
    public ResponseEntity<?> actualizar(@RequestBody @Valid ActulizaPacienteDTO paciente,
                                        UriComponentsBuilder uriBuilder) {
      return pacienteService.actualizar(paciente,uriBuilder);
    }

}
