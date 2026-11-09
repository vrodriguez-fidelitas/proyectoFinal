package com.ciclo.service.impl;

import com.ciclo.domain.Cliente;
import com.ciclo.repository.ClienteRepository;
import com.ciclo.service.ClienteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente getClientePorIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion).orElse(null);
    }

    @Override
    @Transactional
    public void guardar(Cliente cliente) throws Exception {
        // Criterio HU-01: No permitir registrar dos clientes con la misma identificación
        var existente = clienteRepository.findByIdentificacion(cliente.getIdentificacion());
        if (existente.isPresent() && (cliente.getIdCliente() == null || !existente.get().getIdCliente().equals(cliente.getIdCliente()))) {
            throw new Exception("Ya existe un cliente con la identificación: " + cliente.getIdentificacion());
        }
        clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Cliente cliente) {
        clienteRepository.delete(cliente);
    }
}