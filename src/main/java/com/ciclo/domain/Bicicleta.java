package com.ciclo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "bicicleta")
public class Bicicleta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bicicleta")
    private Long idBicicleta;

    @NotBlank(message = "La marca es obligatoria")
    @Column(name = "marca", nullable = false, length = 60)
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    @Column(name = "modelo", nullable = false, length = 60)
    private String modelo;

    @NotBlank(message = "La talla es obligatoria")
    @Column(name = "talla", nullable = false, length = 10)
    private String talla;

    @NotBlank(message = "El tipo de bicicleta es obligatorio")
    @Column(name = "tipo", nullable = false, length = 40)
    private String tipo;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @NotBlank(message = "El número de serie es obligatorio")
    @Column(name = "numero_serie", nullable = false, unique = true, length = 50)
    private String numeroSerie;

    @Column(name = "disponible")
    private Boolean disponible = true;

    public Bicicleta() {
    }

    public Bicicleta(String marca, String modelo, String talla, String tipo, BigDecimal precio, String numeroSerie, Boolean disponible) {
        this.marca = marca;
        this.modelo = modelo;
        this.talla = talla;
        this.tipo = tipo;
        this.precio = precio;
        this.numeroSerie = numeroSerie;
        this.disponible = disponible;
    }

    public Long getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(Long idBicicleta) {
        this.idBicicleta = idBicicleta;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}