package com.ciclo.service;

import com.ciclo.domain.*;
import com.ciclo.repository.*;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompraService {

    private final CompraRepository compras;
    private final ProveedorRepository proveedores;
    private final ProductoAccesorioRepository accesorios;

    public CompraService(
        CompraRepository compras,
        ProveedorRepository proveedores,
        ProductoAccesorioRepository accesorios
    ) {
        this.compras = compras;
        this.proveedores = proveedores;
        this.accesorios = accesorios;
    }

    @Transactional
    public Compra registrar(
        Long proveedorId,
        List<Long> accesorioIds,
        List<Integer> cantidades,
        List<BigDecimal> costos
    ) {

        if (proveedorId == null) {
            throw new IllegalArgumentException(
                "Debe seleccionar un proveedor"
            );
        }

        Proveedor proveedor = proveedores.findById(proveedorId)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "El proveedor no existe"
                )
            );

        if (accesorioIds == null || accesorioIds.isEmpty()
                || cantidades == null || costos == null
                || accesorioIds.size() != cantidades.size()
                || accesorioIds.size() != costos.size()) {
            throw new IllegalArgumentException(
                "Debe agregar al menos un producto a la compra"
            );
        }

        Compra compra = new Compra();
        compra.setProveedor(proveedor);

        for (int i = 0; i < accesorioIds.size(); i++) {

            Long accesorioId = accesorioIds.get(i);
            Integer cantidad = cantidades.get(i);
            BigDecimal costo = costos.get(i);

            if (accesorioId == null) {
                throw new IllegalArgumentException(
                    "Debe seleccionar el producto de cada línea"
                );
            }

            if (cantidad == null || cantidad < 1) {
                throw new IllegalArgumentException(
                    "La cantidad recibida debe ser mayor que cero"
                );
            }

            if (costo == null
                    || costo.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                    "El costo unitario debe ser un valor positivo"
                );
            }

            ProductoAccesorio a = accesorios
                .findByIdForUpdate(accesorioId)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "El producto no existe"
                    )
                );

            compra.agregar(new DetalleCompra(a, cantidad, costo));

            // Criterio HU-08 b): las existencias aumentan
            a.setCantidad(a.getCantidad() + cantidad);
        }

        // Criterio HU-08 c): la compra guarda su monto total
        BigDecimal total = compra.getDetalles()
            .stream()
            .map(DetalleCompra::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        compra.setTotal(total);

        return compras.save(compra);
    }

    @Transactional(readOnly = true)
    public Compra detalle(Long id) {
        return compras.findWithDetallesById(id)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Compra no encontrada"
                )
            );
    }
}