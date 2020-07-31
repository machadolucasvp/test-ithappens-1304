package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.model.Produto;
import com.ithappens.estoque.repository.ProdutoRepository;
import com.ithappens.estoque.service.ProdutoService;
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
@Api(value = "Operações Produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final ProdutoService produtoService;

    @GetMapping("/produtos")
    @ApiOperation(value = "Retorna os produtos cadastrados")
    public ResponseEntity<List<Produto>> buscarTodos(){
        List<Produto> produtosCadastrados = produtoRepository.findAll();

        if(produtosCadastrados.isEmpty()){
            return noContent().build();
        }else{
            return ok(produtosCadastrados);
        }
    }

    @GetMapping("/produtoId/{id}")
    @ApiOperation(value = "Busca um produto por id")
    public ResponseEntity<Produto> buscaPorId(@PathVariable Long id){
        return ok(produtoService.buscaPorId(id));
    }

    @GetMapping("/produtoCdgBarras/{cdgBarras}")
    @ApiOperation(value = "Busca um produto pelo código de barras")
    public ResponseEntity<Produto> buscaPorCdgBarras(@PathVariable String cdgBarras){
        return ok(produtoService.buscaPorCdgBarras(cdgBarras));
    }


    @PostMapping("/produto")
    @ApiOperation(value = "Cadastra um novo produto")
    public ResponseEntity<Produto>cadastrar(@Valid @RequestBody Produto produto){
        Produto novoProduto = produtoService.salvar(produto);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping("/produtoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um produto")
    public ResponseEntity<Produto> atualizar(@Valid @RequestBody Produto produto){
        Produto produtoAtualizado = produtoService.alterar(produto);
        return new ResponseEntity<>(produtoAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/produtoId/{id}")
    @ApiOperation(value = "Deleta um produto")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        produtoRepository.delete(produtoService.buscaPorId(id));
        return noContent().build();
    }


}
