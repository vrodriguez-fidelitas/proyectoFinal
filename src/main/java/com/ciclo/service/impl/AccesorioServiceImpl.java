package com.ciclo.service.impl;

import com.ciclo.domain.CategoriaAccesorio;
import com.ciclo.domain.ProductoAccesorio;
import com.ciclo.repository.CategoriaAccesorioRepository;
import com.ciclo.repository.ProductoAccesorioRepository;
import com.ciclo.service.AccesorioService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccesorioServiceImpl implements AccesorioService {

    @Autowired
    private ProductoAccesorioRepository accesorioRepository;

    @Autowired
    private CategoriaAccesorioRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoAccesorio> getAccesorios() {
        return accesorioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaAccesorio> getCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    @Transactional
    public void guardar(ProductoAccesorio accesorio) throws Exception {
        // Criterio HU-03: Precio y cantidad únicamente valores numéricos positivos/no negativos
        if (accesorio.getPrecio() == null || accesorio.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("El precio debe ser un valor numérico positivo.");
        }
        if (accesorio.getCantidad() == null || accesorio.getCantidad() < 0) {
            throw new Exception("La cantidad debe ser mayor o igual a cero.");
        }
        accesorioRepository.save(accesorio);
    }

    @Override
    @Transactional
    public void eliminar(ProductoAccesorio accesorio) {
        accesorioRepository.delete(accesorio);
    }
}