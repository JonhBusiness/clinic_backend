package org.example.clinic.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.clinic.domain.medico.DTO.ActulizaMedicoDTO;
import org.example.clinic.domain.medico.DTO.ListaMedicosDTO;
import org.example.clinic.domain.medico.DTO.RegistraMedicoDTO;
import org.example.clinic.service.MedicoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
//@SuppressWarnings("all")
public class MedicoController {

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping
    @Operation(
            summary = "Obtiene el listado de Medicos",
            description = "",
            tags = { "Medico", "Get" })
    public ResponseEntity<Page<ListaMedicosDTO>> getMedicos(@PageableDefault(size = 100,sort = {"nombre"}) Pageable pageable) {
       Page<ListaMedicosDTO> medicos =   medicoService.getAll(pageable);
        return ResponseEntity.ok(medicos);
    }

    @PostMapping("/registrar")
    @Operation(
            summary = "registra un Medico en la base de datos",
            description = "",
            tags = { "Medico", "Post" })
    public ResponseEntity<?> registrar(@RequestBody @Valid RegistraMedicoDTO medico,
                                                    UriComponentsBuilder ucBuilder) {
        System.out.println("datos recibidos: " + medico.toString());
        return medicoService.registrar(medico,ucBuilder);
    }
 @PutMapping("/actualizar")
 @Operation(
         summary = "actualiza un Medico en la base de datos",
         description = "",
         tags = { "Medico", "Put" })
 public ResponseEntity<?> actualizarMedico(@RequestBody @Valid ActulizaMedicoDTO medico,
                                           UriComponentsBuilder ucBuilder){
     return medicoService.actualizar(medico,ucBuilder);
 }

    @DeleteMapping("/desactivar/{id}")
    @Operation(
            summary = "Desactiva un medico en base de datos",
            description = "",
            tags = { "Medico", "delete" })
    public ResponseEntity<?> desactivarMedico(@PathVariable Long id){
        return medicoService.desactivar(id);
    }

}
