package com.ciclo.service;

import com.ciclo.domain.CategoriaAccesorio;
import com.ciclo.domain.ProductoAccesorio;
import java.util.List;

public interface AccesorioService {
    List<ProductoAccesorio> getAccesorios();
    List<CategoriaAccesorio> getCategorias();
    void guardar(ProductoAccesorio accesorio) throws Exception;
    void eliminar(ProductoAccesorio accesorio);
}