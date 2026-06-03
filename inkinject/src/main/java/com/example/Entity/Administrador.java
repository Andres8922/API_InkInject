package com.example.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
//import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMINISTRADOR")
@Schema(description = "Administrador del sistema")
public class Administrador extends Actor {

}