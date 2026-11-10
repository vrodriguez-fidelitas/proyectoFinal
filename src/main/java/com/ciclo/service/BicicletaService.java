package com.ciclo.service;

import com.ciclo.domain.Bicicleta;
import java.util.List;

public interface BicicletaService {
    List<Bicicleta> getBicicletas();
    List<Bicicleta> getBicicletasDisponibles();
    void guardar(Bicicleta bicicleta) throws Exception;
    void eliminar(Bicicleta bicicleta);
}