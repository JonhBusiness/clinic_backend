package org.example.clinic.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.clinic.domain.medico.DTO.RegistraMedicoDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Medico")
@Table(name = "tbl_medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String telefono;
    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;

    private Boolean activo; // activo en el sistema de la clinica

    public Medico(RegistraMedicoDTO medico) {
       this.nombre= medico.nombre();
       this.email = medico.email();
       this.telefono = medico.telefono();
       this.documento = medico.documento();
       this.especialidad = medico.especialidad();
       this.direccion = medico.direccion();
       this.activo=true;
    }

    public void Desactivar() {
        this.activo = false;
    }
}