package com.ciclo.controller;

import com.ciclo.domain.Cliente;
import com.ciclo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("clientes", clienteService.getClientes());
        model.addAttribute("cliente", new Cliente());
        return "cliente/listado";
    }

    @PostMapping("/guardar")
    public String guardar(Cliente cliente, RedirectAttributes flash) {
        try {
            clienteService.guardar(cliente);
            flash.addFlashAttribute("exito", "Cliente registrado exitosamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cliente/listado";
    }
}