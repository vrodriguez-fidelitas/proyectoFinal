
package com.ciclo.controller;

import com.ciclo.domain.Venta;
import com.ciclo.repository.*;
import com.ciclo.service.VentaService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService servicio;
    private final VentaRepository ventas;
    private final ClienteRepository clientes;
    private final BicicletaRepository bicicletas;
    private final ProductoAccesorioRepository accesorios;

    public VentaController(
        VentaService servicio,
        VentaRepository ventas,
        ClienteRepository clientes,
        BicicletaRepository bicicletas,
        ProductoAccesorioRepository accesorios
    ) {
        this.servicio = servicio;
        this.ventas = ventas;
        this.clientes = clientes;
        this.bicicletas = bicicletas;
        this.accesorios = accesorios;
    }

    @GetMapping
    public String listado(Model model) {

        model.addAttribute(
            "clientes", clientes.findAll()
        );

        model.addAttribute(
            "bicicletas",
            bicicletas.findByDisponibleTrue()
        );

        model.addAttribute(
            "accesorios", accesorios.findAll()
        );

        model.addAttribute(
            "ventas",
            ventas.findAllByOrderByFechaDesc()
        );

        return "ventas/listado";
    }

    @PostMapping
    public String registrar(
        @RequestParam Long clienteId,
        @RequestParam String tipo,
        @RequestParam Long productoId,
        @RequestParam int cantidad,
        RedirectAttributes redirect
    ) {

        try {

            Venta venta = servicio.registrar(
                clienteId,
                tipo,
                productoId,
                cantidad
            );

            return "redirect:/ventas/" +
                venta.getId() + "/comprobante";

        } catch (IllegalArgumentException e) {

            redirect.addFlashAttribute(
                "error", e.getMessage()
            );

            return "redirect:/ventas";
        }
    }

    @GetMapping("/{id}/comprobante")
    public String comprobante(
        @PathVariable Long id,
        Model model
    ) {

        model.addAttribute(
            "venta", servicio.comprobante(id)
        );

        return "ventas/comprobante";
    }
}
