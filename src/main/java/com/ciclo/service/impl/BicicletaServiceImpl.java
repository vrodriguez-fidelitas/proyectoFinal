package com.ciclo.service.impl;

import com.ciclo.domain.Bicicleta;
import com.ciclo.repository.BicicletaRepository;
import com.ciclo.service.BicicletaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BicicletaServiceImpl implements BicicletaService {

    @Autowired
    private BicicletaRepository bicicletaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Bicicleta> getBicicletas() {
        return bicicletaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bicicleta> getBicicletasDisponibles() {
        return bicicletaRepository.findByDisponibleTrue();
    }

    @Override
    @Transactional
    public void guardar(Bicicleta bicicleta) throws Exception {
        // Criterio HU-02: Número de serie único
        var existente = bicicletaRepository.findByNumeroSerie(bicicleta.getNumeroSerie());
        if (existente.isPresent() && (bicicleta.getIdBicicleta() == null || !existente.get().getIdBicicleta().equals(bicicleta.getIdBicicleta()))) {
            throw new Exception("Ya existe una bicicleta registrada con el número de serie: " + bicicleta.getNumeroSerie());
        }
        // Criterio HU-02: La bicicleta registrada aparece como disponible
        if (bicicleta.getIdBicicleta() == null) {
            bicicleta.setDisponible(true);
        }
        bicicletaRepository.save(bicicleta);
    }

    @Override
    @Transactional
    public void eliminar(Bicicleta bicicleta) {
        bicicletaRepository.delete(bicicleta);
    }
}