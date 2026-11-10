package com.ciclo.service.impl;

import com.ciclo.domain.Proveedor;
import com.ciclo.repository.ProveedorRepository;
import com.ciclo.service.ProveedorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Proveedor> getProveedores() {
        return proveedorRepository.findAllByOrderByNombreAsc();
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor getProveedor(Long idProveedor) {
        return proveedorRepository.findById(idProveedor).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Proveedor proveedor) throws Exception {
        // Criterio HU-07 a): nombre, teléfono, correo y dirección son obligatorios
        if (esVacio(proveedor.getNombre()) || esVacio(proveedor.getTelefono())
                || esVacio(proveedor.getCorreo()) || esVacio(proveedor.getDireccion())) {
            throw new Exception("Debe completar nombre, teléfono, correo y dirección del proveedor.");
        }
        if (!proveedor.getCorreo().trim().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new Exception("Debe ingresar un formato de correo válido.");
        }

        // Criterio HU-07 b): los datos pueden editarse posteriormente
        if (proveedor.getIdProveedor() != null
                && !proveedorRepository.existsById(proveedor.getIdProveedor())) {
            throw new Exception("El proveedor que intenta editar no existe.");
        }

        proveedor.setNombre(proveedor.getNombre().trim());
        proveedor.setTelefono(proveedor.getTelefono().trim());
        proveedor.setCorreo(proveedor.getCorreo().trim());
        proveedor.setDireccion(proveedor.getDireccion().trim());

        proveedorRepository.save(proveedor);
    }

    private boolean esVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}