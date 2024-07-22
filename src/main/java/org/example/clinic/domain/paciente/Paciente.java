package org.example.clinic.domain.paciente;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.clinic.domain.medico.Direccion;
import org.example.clinic.domain.paciente.DTO.RegistraPacienteDTO;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Paciente")
@Table(name = "tbl_pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    private String telefono;

    private String documento;

    @Embedded
    private Direccion direccion;

//    private Boolean estadoCita;

    public Paciente(RegistraPacienteDTO paciente) {
        this.nombre= paciente.nombre();
        this.email= paciente.email();
        this.telefono= paciente.telefono();
        this.documento= paciente.documento();
        this.direccion= paciente.direccion();

    }
}