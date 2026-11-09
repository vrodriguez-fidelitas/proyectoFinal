package com.ciclo.controller;

import com.ciclo.domain.Bicicleta;
import com.ciclo.domain.ProductoAccesorio;
import com.ciclo.service.AccesorioService;
import com.ciclo.service.BicicletaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private BicicletaService bicicletaService;

    @Autowired
    private AccesorioService accesorioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("bicicletas", bicicletaService.getBicicletas());
        model.addAttribute("accesorios", accesorioService.getAccesorios());
        model.addAttribute("categorias", accesorioService.getCategorias());
        model.addAttribute("bicicleta", new Bicicleta());
        model.addAttribute("accesorio", new ProductoAccesorio());
        return "inventario/listado";
    }

    @PostMapping("/bicicleta/guardar")
    public String guardarBicicleta(Bicicleta bicicleta, RedirectAttributes flash) {
        try {
            bicicletaService.guardar(bicicleta);
            flash.addFlashAttribute("exito", "Bicicleta registrada en el inventario.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/inventario/listado";
    }

    @PostMapping("/accesorio/guardar")
    public String guardarAccesorio(ProductoAccesorio accesorio, RedirectAttributes flash) {
        try {
            accesorioService.guardar(accesorio);
            flash.addFlashAttribute("exito", "Componente/accesorio registrado exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/inventario/listado";
    }
}