package com.ciclo.service;

import com.ciclo.domain.Proveedor;
import java.util.List;

public interface ProveedorService {
    List<Proveedor> getProveedores();
    Proveedor getProveedor(Long idProveedor);
    void guardar(Proveedor proveedor) throws Exception;
}