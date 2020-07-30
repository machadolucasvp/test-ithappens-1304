package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteServiceTest {

    @Mock
    ClienteRepository clienteRepository;

    @InjectMocks
    ClienteService clienteService;

    Cliente cliente;

    @BeforeEach
    void init(){ cliente = Cliente.builder().id(1L).cpf("29266976008").nome("Maria").build(); }

    @Test
    void aoSalvar_RetornarClienteSalvo(){
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente novoCliente = clienteService.salvar(Cliente.builder().cpf("292.669.760-08").nome("Maria").build());

        assertEquals(cliente.getId(), novoCliente.getId());
    }

    @Test
    void aoAlterarCliente_RetornarDadosAtualizados(){
        Cliente clienteAtualizado = Cliente.builder().nome("Maria Dias").cpf("29266976008").build();

        when(clienteRepository.findById(any(Long.class))).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        clienteService.alterar(1L, clienteAtualizado);

        assertEquals(cliente.getNome(), clienteAtualizado.getNome());
    }

    @Test
    void aoVerificarCPFCadastrado_LancarExcecao(){
        when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(cliente));

        assertThrows(ServiceException.class, ()->clienteService.salvar(cliente));
    }
}
