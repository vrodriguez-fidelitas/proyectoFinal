package com.ciclo.controller;

import com.ciclo.domain.Compra;
import com.ciclo.repository.CompraRepository;
import com.ciclo.repository.ProductoAccesorioRepository;
import com.ciclo.repository.ProveedorRepository;
import com.ciclo.service.CompraService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/compras")
public class CompraController {

    private final CompraService servicio;
    private final CompraRepository compras;
    private final ProveedorRepository proveedores;
    private final ProductoAccesorioRepository accesorios;

    public CompraController(
        CompraService servicio,
        CompraRepository compras,
        ProveedorRepository proveedores,
        ProductoAccesorioRepository accesorios
    ) {
        this.servicio = servicio;
        this.compras = compras;
        this.proveedores = proveedores;
        this.accesorios = accesorios;
    }

    @GetMapping
    public String listado(Model model) {

        model.addAttribute(
            "proveedores", proveedores.findAllByOrderByNombreAsc()
        );

        model.addAttribute(
            "accesorios", accesorios.findAll()
        );

        model.addAttribute(
            "compras", compras.findAllByOrderByFechaDesc()
        );

        return "compras/listado";
    }

    @PostMapping
    public String registrar(
        @RequestParam Long proveedorId,
        @RequestParam("accesorioId") List<Long> accesorioIds,
        @RequestParam("cantidad") List<Integer> cantidades,
        @RequestParam("costo") List<BigDecimal> costos,
        RedirectAttributes redirect
    ) {

        try {

            Compra compra = servicio.registrar(
                proveedorId,
                accesorioIds,
                cantidades,
                costos
            );

            redirect.addFlashAttribute(
                "exito",
                "Compra registrada. Las existencias fueron actualizadas."
            );

            return "redirect:/compras/" + compra.getId();

        } catch (IllegalArgumentException e) {

            redirect.addFlashAttribute(
                "error", e.getMessage()
            );

            return "redirect:/compras";
        }
    }

    @GetMapping("/{id}")
    public String detalle(
        @PathVariable Long id,
        Model model
    ) {

        model.addAttribute(
            "compra", servicio.detalle(id)
        );

        return "compras/detalle";
    }
}