package com.ciclo.servicee;

import com.ciclo.domain.Proveedor;
import java.util.List;

public interface ProveedorServicee {
    List<Proveedor> getProveedores();
    Proveedor getProveedor(Long idProveedor);
    void guardar(Proveedor proveedor) throws Exception;
}