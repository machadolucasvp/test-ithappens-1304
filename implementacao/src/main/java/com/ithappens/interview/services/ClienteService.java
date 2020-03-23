package com.ithappens.interview.services;

import com.ithappens.interview.dtos.ClienteDTO;
import com.ithappens.interview.models.Cliente;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    ClienteDTO asDTO(Cliente cliente) {
        return ClienteDTO.builder().id(cliente.getId())
                .nome(cliente.getNome())
                .cnpj(cliente.getCpf())
                .cpf(cliente.getCnpj())
                .build();
    }

}
