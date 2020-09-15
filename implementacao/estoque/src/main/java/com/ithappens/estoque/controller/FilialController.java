package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.Cliente;
import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.repository.FilialRepository;
import com.ithappens.estoque.service.FilialService;
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
@Api(value = "Operações Filiais")
@CrossOrigin(origins = "*")
public class FilialController {

    private final FilialService filialService;
    private final FilialRepository filialRepository;

    @GetMapping("/filiais")
    @ApiOperation(value = "Retorna as filiais cadastradas")
    public ResponseEntity<List<Filial>> buscarTodas(){
        List<Filial> filiaisCadastradas = filialRepository.findAll();

        if(filiaisCadastradas.isEmpty()){
            return noContent().build();
        }else{
            return ok(filiaisCadastradas);
        }
    }

    @GetMapping("/filialId/{id}")
    @ApiOperation(value = "Busca uma filial por id")
    public ResponseEntity<Filial> buscaPorId(@PathVariable Long id){
        return ok(filialService.buscaPorId(id));
    }

    @PostMapping("/filial")
    @ApiOperation(value = "Cadastra uma nova filial")
    public ResponseEntity<Filial>cadastrar(@Valid @RequestBody Filial filial){
        Filial novaFilial = filialService.salvar(filial);
        return new ResponseEntity<>(novaFilial, HttpStatus.CREATED);
    }

    @PutMapping("/filialUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de uma filial")
    public ResponseEntity<Filial> atualizar(@Valid @RequestBody Filial filial){
        Filial filialAtualizada = filialService.alterar(filial);
        return new ResponseEntity<>(filialAtualizada, HttpStatus.CREATED);
    }

    @DeleteMapping("/filialId/{id}")
    @ApiOperation(value = "Deleta uma filial")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        filialRepository.delete(filialService.buscaPorId(id));
        return noContent().build();
    }

}
