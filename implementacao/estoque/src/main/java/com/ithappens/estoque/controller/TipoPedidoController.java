package com.ithappens.estoque.controller;


import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.model.TipoPedido;
import com.ithappens.estoque.repository.TipoPedidoRepository;
import com.ithappens.estoque.service.TipoPedidoService;
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
@Api(value = "Operações Tipo Pedido")
@CrossOrigin(origins = "*")
public class TipoPedidoController {

    private final TipoPedidoRepository tipoPedidoRepository;
    private final TipoPedidoService tipoPedidoService;

    @GetMapping("/tipospedido")
    @ApiOperation(value = "Retorna os tipos de pedido cadastrados")
    public ResponseEntity<List<TipoPedido>> buscarTodos(){
        List<TipoPedido> tiposPedidoCadastrados = tipoPedidoRepository.findAll();

        if(tiposPedidoCadastrados.isEmpty()){
            return noContent().build();
        }else{
            return ok(tiposPedidoCadastrados);
        }
    }

    @GetMapping("/tipoPedidoId/{id}")
    @ApiOperation(value = "Busca um tipo de pedido por id")
    public ResponseEntity<TipoPedido> buscaPorId(@PathVariable Long id){
        return ok(tipoPedidoService.buscaPorId(id));
    }

    @PostMapping("/tipoPedido")
    @ApiOperation(value = "Cadastra um novo tipo de pedido")
    public ResponseEntity<TipoPedido>cadastrar(@Valid @RequestBody TipoPedido tipoPedido){
        TipoPedido novoTipoPedido = tipoPedidoService.salvar(tipoPedido);
        return new ResponseEntity<>(novoTipoPedido, HttpStatus.CREATED);
    }

    @PutMapping("/tipoPedidoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um tipo de pedido")
    public ResponseEntity<TipoPedido> atualizar(@Valid @RequestBody TipoPedido tipoPedido){
        TipoPedido tipoPedidoAtualizado = tipoPedidoService.alterar(tipoPedido);
        return new ResponseEntity<>(tipoPedidoAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/tipoPedidoId/{id}")
    @ApiOperation(value = "Deleta um tipo de pedido")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        tipoPedidoRepository.delete(tipoPedidoService.buscaPorId(id));
        return noContent().build();
    }

}
