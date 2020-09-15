package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.repository.ClienteRepository;
import com.ithappens.estoque.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Operações Clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;

    @GetMapping("/clientes")
    @ApiOperation(value = "Retorna os clientes cadastrados")
    public ResponseEntity<List<Cliente>> buscarTodos(){
        List<Cliente> clientesCadastrados = clienteRepository.findAll();

        if(clientesCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(clientesCadastrados);
        }
    }

    @GetMapping("/clienteId/{id}")
    @ApiOperation(value = "Busca um cliente por id")
    public ResponseEntity<Cliente> buscaPorId(@PathVariable Long id){
        return ok(clienteService.buscaPorId(id));
    }

    @GetMapping("/clienteCpf/{cpf}")
    @ApiOperation(value = "Busca cliente por CPF")
    public ResponseEntity<Cliente>buscaPorCPF(@PathVariable String cpf){
        return ok(clienteService.buscaPorCPF(cpf));
    }

    @PostMapping("/cliente")
    @ApiOperation(value = "Cadastra um novo cliente")
    public ResponseEntity<Cliente>cadastrar(@Valid @RequestBody Cliente cliente){
        Cliente novoCliente = clienteService.salvar(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/clienteUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um cliente")
    public ResponseEntity<Cliente> atualizar(@Valid @RequestBody Cliente cliente){
        Cliente clienteAtualizado = clienteService.alterar(cliente);
        return new ResponseEntity<>(clienteAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/clienteId/{id}")
    @ApiOperation(value = "Deleta um cliente")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return noContent().build();
    }

}
