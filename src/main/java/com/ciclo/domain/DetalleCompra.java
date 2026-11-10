package com.ciclo.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_compra")
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_compra")
    private Compra compra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_accesorio")
    private ProductoAccesorio accesorio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costoUnitario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    public DetalleCompra() {
    }

    public DetalleCompra(ProductoAccesorio accesorio, int cantidad, BigDecimal costoUnitario) {
        this.accesorio = accesorio;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.subtotal = costoUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public Long getId() {
        return id;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public ProductoAccesorio getAccesorio() {
        return accesorio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}