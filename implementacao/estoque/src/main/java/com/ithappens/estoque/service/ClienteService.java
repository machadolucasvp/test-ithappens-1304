package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.model.Compra;
import com.ithappens.estoque.repository.ClienteRepository;
import com.ithappens.estoque.repository.CompraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CompraRepository compraRepository;

    public Cliente salvar(Cliente cliente){
        verificaCPFValido(cliente.getCpf());
        return clienteRepository.save(cliente);
    }

    public Cliente alterar(Cliente cliente){
        if(cliente.getId() == null){
            throw new ServiceException("Id do Cliente não informado");
        }
        return clienteRepository.findById(cliente.getId()).map(clienteSalvo -> {
            if(!cliente.getCpf().equals(clienteSalvo.getCpf())) verificaCPFValido(cliente.getCpf());


            return clienteRepository.save(cliente);

        }).orElseThrow(()->new ServiceException("Cliente com id " + cliente.getId() + " inexistente"));

    }

    public void deletar(Long id){
        Cliente cliente = buscaPorId(id);

        if(!compraRepository.findByClienteId(cliente.getId()).isEmpty()){
            throw new ServiceException("Existem compras (operações de saída) registradas para esse cliente. Exclua as compras para que possa excluir o cliente.");
        }else{
            clienteRepository.delete(cliente);
        }
    }

    public Cliente buscaPorId(Long id){
        return clienteRepository.findById(id).orElseThrow(()-> new ServiceException("Cliente com id " + id + " inexistente"));
    }

    public Cliente buscaPorCPF(String cpf){
        return clienteRepository.findByCpf(cpf).orElseThrow(()-> new ServiceException("Cliente com cpf " + cpf + " inexistente"));
    }

    private void verificaCPFValido(String cpf){
        if(clienteRepository.findByCpf(cpf).isPresent()){
            throw new ServiceException("CPF já cadastrado");
        }
    }




}
