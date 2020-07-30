package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente){
        verificaCPFValido(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Long id, Cliente cliente){
        if(id == null){
            throw new ServiceException("Id do Cliente não informado");
        }else{
            return clienteRepository.findById(id).map(clienteSalvo -> {
                if(!cliente.getCpf().equals(clienteSalvo.getCpf())) verificaCPFValido(cliente.getCpf());

                return clienteRepository.save(cliente);

            }).orElseThrow(()->new ServiceException("Cliente com id " + id + " inexistente"));

        }
    }

    public Cliente buscaPorId(Long id){
        return clienteRepository.findById(id).orElseThrow(()-> new ServiceException("Cliente com id " + id + " inexistente"));
    }

    private void verificaCPFValido(String cpf){
        if(clienteRepository.findByCpf(cpf).isPresent()){
            throw new ServiceException("CPF já cadastrado");
        }
    }


}
