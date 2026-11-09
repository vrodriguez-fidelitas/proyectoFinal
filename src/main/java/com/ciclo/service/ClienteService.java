package com.ciclo.service;

import com.ciclo.domain.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> getClientes();
    Cliente getClientePorIdentificacion(String identificacion);
    void guardar(Cliente cliente) throws Exception;
    void eliminar(Cliente cliente);
}