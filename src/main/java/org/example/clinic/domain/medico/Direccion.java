package org.example.clinic.domain.medico;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Direccion {
  private String calle;
  private String numero;
  private String complemento;
  private String postal;
  private String ciudad;

  @Enumerated(EnumType.STRING)
  private Estado estado;
}