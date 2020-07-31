package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.StatusPedido;
import com.ithappens.estoque.model.TipoPedido;
import com.ithappens.estoque.repository.StatusPedidoRepository;
import com.ithappens.estoque.service.StatusPedidoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Operações Status de Pedido")
@CrossOrigin(origins = "*")
public class StatusPedidoController {

    private final StatusPedidoRepository statusPedidoRepository;
    private final StatusPedidoService statusPedidoService;

    @GetMapping("/statuspedidos")
    @ApiOperation(value = "Retorna os status de pedido cadastrados")
    public ResponseEntity<List<StatusPedido>> buscarTodos(){
        List<StatusPedido> statusPedidoCadastrados = statusPedidoRepository.findAll();

        if(statusPedidoCadastrados.isEmpty()){
            return noContent().build();
        }else{
            return ok(statusPedidoCadastrados);
        }
    }

    @GetMapping("/statuspedidoId/{id}")
    @ApiOperation(value = "Busca um status de pedido por id")
    public ResponseEntity<StatusPedido> buscaPorId(@PathVariable Long id){
        return ok(statusPedidoService.buscaPorId(id));
    }

    @PostMapping("/statuspedido")
    @ApiOperation(value = "Cadastra um novo status de pedido")
    public ResponseEntity<StatusPedido>cadastrar(@Valid @RequestBody StatusPedido statusPedido){
        StatusPedido novoStatusPedido = statusPedidoService.salvar(statusPedido);
        return new ResponseEntity<>(novoStatusPedido, HttpStatus.CREATED);
    }

    @PutMapping("/statusPedidoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um status de pedido cadastrado")
    public ResponseEntity<StatusPedido> atualizar(@Valid @RequestBody StatusPedido statusPedido){
        StatusPedido statusPedidoAtualizado = statusPedidoService.alterar(statusPedido);
        return new ResponseEntity<>(statusPedidoAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/statuspedidoId/{id}")
    @ApiOperation(value = "Deleta um status de pedido")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        statusPedidoRepository.delete(statusPedidoService.buscaPorId(id));
        return noContent().build();
    }
}
