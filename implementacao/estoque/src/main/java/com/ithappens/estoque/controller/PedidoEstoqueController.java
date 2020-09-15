package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.ItemPedido;
import com.ithappens.estoque.model.PedidoEstoque;
import com.ithappens.estoque.model.TipoPedido;
import com.ithappens.estoque.repository.ItemPedidoRepository;
import com.ithappens.estoque.repository.PedidoEstoqueRepository;
import com.ithappens.estoque.service.ItemPedidoService;
import com.ithappens.estoque.service.PedidoEstoqueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Operações Item Pedido")
public class PedidoEstoqueController {

    private final PedidoEstoqueRepository pedidoEstoqueRepository;
    private final PedidoEstoqueService pedidoEstoqueService;

    @GetMapping("/pedidosEstoque")
    @ApiOperation(value = "Retorna os pedidos de estoque cadastrados")
    public ResponseEntity<List<PedidoEstoque>> buscarTodos(){
        List<PedidoEstoque> pedidoEstoqueCadastrados = pedidoEstoqueRepository.findAll();

        if(pedidoEstoqueCadastrados.isEmpty()){
            return noContent().build();
        }else{
            return ok(pedidoEstoqueCadastrados);
        }
    }

    @GetMapping("/pedidoEstoqueId/{id}")
    @ApiOperation(value = "Busca um pedido de estoque por id")
    public ResponseEntity<PedidoEstoque> buscaPorId(@PathVariable Long id){
        return ok(pedidoEstoqueService.buscaPorId(id));
    }

    @GetMapping("/pedidoEstoqueFilial/{id}")
    @ApiOperation(value = "Busca os pedidos de estoque de uma filial")
    public ResponseEntity<List<PedidoEstoque>> buscaPorFilial(@PathVariable Long id){
        List<PedidoEstoque> pedidosEstoqueFilial = pedidoEstoqueService.buscaPorFilial(id);
        if(pedidosEstoqueFilial.isEmpty()){
            return noContent().build();
        }else{
            return ok(pedidosEstoqueFilial);
        }
    }

    @GetMapping("/pedidoEstoqueTipoPedido/{id}")
    @ApiOperation(value = "Busca os pedidos de estoque por tipo de pedido")
    public ResponseEntity<List<PedidoEstoque>> buscaPorTipoPedido(@PathVariable Long id){
        List<PedidoEstoque> pedidosEstoque = pedidoEstoqueService.buscaPorTipoPedido(id);
        if(pedidosEstoque.isEmpty()){
            return noContent().build();
        }else{
            return ok(pedidosEstoque);
        }
    }

    @GetMapping("/pedidoEstoqueEntrada/{id}")
    @ApiOperation(value = "Busca os pedidos de estoque do tipo entrada")
    public ResponseEntity<List<PedidoEstoque>> buscaPorTipoEntrada(){
        List<PedidoEstoque> pedidosEstoque = pedidoEstoqueService.buscaPedidosEtoqueTipoEntrada();
        if(pedidosEstoque.isEmpty()){
            return noContent().build();
        }else{
            return ok(pedidosEstoque);
        }
    }

    @GetMapping("/pedidoEstoqueSaida/{id}")
    @ApiOperation(value = "Busca os pedidos de estoque do tipo saída")
    public ResponseEntity<List<PedidoEstoque>> buscaPorTipoSaida(){
        List<PedidoEstoque> pedidosEstoque = pedidoEstoqueService.buscaPedidosEstoqueTipoSaida();
        if(pedidosEstoque.isEmpty()){
            return noContent().build();
        }else{
            return ok(pedidosEstoque);
        }
    }

    @GetMapping("/pedidoEstoqueItens/{id}")
    @ApiOperation(value = "Busca os itens de um pedido de estoque")
    public ResponseEntity<List<ItemPedido>> buscaItensPedido(Long id){
        List<ItemPedido> itensPedido = pedidoEstoqueService.buscaTodosItensDoPedidoEstoque(id);
        if(itensPedido.isEmpty()){
            return noContent().build();
        }else{
            return ok(itensPedido);
        }
    }

    @GetMapping("/pedidoEstoqueItensAtivos/{id}")
    @ApiOperation(value = "Busca os itens com status ativo de um pedido de estoque")
    public ResponseEntity<List<ItemPedido>> buscaItensAtivosPedido(Long id){
        List<ItemPedido> itensPedido = pedidoEstoqueService.buscaItensDoPedidoEstoqueStatusAtivo(id);
        if(itensPedido.isEmpty()){
            return noContent().build();
        }else{
            return ok(itensPedido);
        }
    }

    @GetMapping("/pedidoEstoqueItensCancelados/{id}")
    @ApiOperation(value = "Busca os itens com status cancelado de um pedido de estoque")
    public ResponseEntity<List<ItemPedido>> buscaItensCanceladosPedido(Long id){
        List<ItemPedido> itensPedido = pedidoEstoqueService.buscaItensDoPedidoEstoqueStatusCancelado(id);
        if(itensPedido.isEmpty()){
            return noContent().build();
        }else{
            return ok(itensPedido);
        }
    }

    @GetMapping("/pedidoEstoqueItensProcessados/{id}")
    @ApiOperation(value = "Busca os itens com status processado de um pedido de estoque")
    public ResponseEntity<List<ItemPedido>> buscaItensProcessadosPedido(Long id){
        List<ItemPedido> itensPedido = pedidoEstoqueService.buscaItensDoPedidoEstoqueStatusProcessado(id);
        if(itensPedido.isEmpty()){
            return noContent().build();
        }else{
            return ok(itensPedido);
        }
    }



    @PostMapping("/pedidoEstoque")
    @ApiOperation(value = "Cadastra um novo pedido de estoque")
    public ResponseEntity<PedidoEstoque>cadastrar(@Valid @RequestBody PedidoEstoque pedidoEstoque){
        PedidoEstoque novoPedidoEstoque = pedidoEstoqueService.salvar(pedidoEstoque);
        return new ResponseEntity<>(novoPedidoEstoque, HttpStatus.CREATED);
    }

    @PutMapping("/pedidoEstoqueUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um pedido de estoque")
    public ResponseEntity<PedidoEstoque> atualizar(@Valid @RequestBody PedidoEstoque pedidoEstoque){
        PedidoEstoque pedidoEstoqueAtualizado = pedidoEstoqueService.alterar(pedidoEstoque);
        return new ResponseEntity<>(pedidoEstoqueAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/pedidoEstoqueId/{id}")
    @ApiOperation(value = "Deleta um pedido de estoque")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        pedidoEstoqueService.deletePedidoEstoque(id);
        return noContent().build();
    }

}
