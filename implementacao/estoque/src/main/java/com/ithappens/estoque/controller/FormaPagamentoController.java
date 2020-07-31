package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.FormaPagamento;
import com.ithappens.estoque.model.TipoPedido;
import com.ithappens.estoque.repository.FormaPagamentoRepository;
import com.ithappens.estoque.service.FormaPagamentoService;
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
@Api(value = "Operações Formas de Pagamento")
@CrossOrigin(origins = "*")
public class FormaPagamentoController {

    private final FormaPagamentoRepository formaPagamentoRepository;
    private final FormaPagamentoService formaPagamentoService;

    @GetMapping("/formaspagamento")
    @ApiOperation(value = "Retorna as formas de pagamento cadastradas")
    public ResponseEntity<List<FormaPagamento>> buscarTodos(){
        List<FormaPagamento> formasPagamentoCadastradas = formaPagamentoRepository.findAll();

        if(formasPagamentoCadastradas.isEmpty()){
            return noContent().build();
        }else{
            return ok(formasPagamentoCadastradas);
        }
    }

    @GetMapping("/formaPagamentoId/{id}")
    @ApiOperation(value = "Busca uma forma de pagamento por id")
    public ResponseEntity<FormaPagamento> buscaPorId(@PathVariable Long id){
        return ok(formaPagamentoService.buscaPorId(id));
    }

    @PostMapping("/formaPagamento")
    @ApiOperation(value = "Cadastra uma nova forma de pagamento")
    public ResponseEntity<FormaPagamento>cadastrar(@Valid @RequestBody FormaPagamento formaPagamento){
        FormaPagamento novaFormaPagamento = formaPagamentoService.salvar(formaPagamento);
        return new ResponseEntity<>(novaFormaPagamento, HttpStatus.CREATED);
    }

    @PutMapping("/formaPagamentoUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de uma forma de pagamento")
    public ResponseEntity<FormaPagamento> atualizar(@Valid @RequestBody FormaPagamento formaPagamento){
        FormaPagamento formaPagamentoAtualizada = formaPagamentoService.alterar(formaPagamento);
        return new ResponseEntity<>(formaPagamentoAtualizada, HttpStatus.CREATED);
    }

    @DeleteMapping("/formaPagamentoId/{id}")
    @ApiOperation(value = "Deleta uma forma de pagamento")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        formaPagamentoRepository.delete(formaPagamentoService.buscaPorId(id));
        return noContent().build();
    }

}
