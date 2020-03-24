package com.ithappens.interview.services;

import com.ithappens.interview.dtos.ClienteDTO;
import com.ithappens.interview.models.Cliente;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.repositories.ClienteRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente findById(Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.orElseThrow(() -> new ObjectNotFoundException(id, this.getClass().getName()));
    }

    ClienteDTO asDTO(Cliente cliente) {
        return ClienteDTO.builder().id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .cnpj(cliente.getCnpj())
                .build();
    }

}
