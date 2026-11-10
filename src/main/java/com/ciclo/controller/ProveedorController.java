package com.ciclo.controller;

import com.ciclo.domain.Proveedor;
import com.ciclo.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("proveedores", proveedorService.getProveedores());
        return "proveedor/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Proveedor proveedor, RedirectAttributes flash) {
        boolean esNuevo = proveedor.getIdProveedor() == null;
        try {
            proveedorService.guardar(proveedor);
            flash.addFlashAttribute("exito", esNuevo
                    ? "Proveedor registrado exitosamente."
                    : "Los datos del proveedor se actualizaron correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/proveedor/listado";
    }
}