package com.ithappens.interview.controllers;

import com.ithappens.interview.dtos.FilialDTO;
import com.ithappens.interview.dtos.FilialProdutoDTO;
import com.ithappens.interview.dtos.PedidoDTO;
import com.ithappens.interview.dtos.ProdutoDTO;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.Produto;
import com.ithappens.interview.services.FilialService;
import com.ithappens.interview.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filial")
public class FilialController {

    @Autowired
    FilialService filialService;

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/{filialId}/produtos")
    public Page<FilialProdutoDTO> getFilialProdutosPage(@PathVariable(value = "filialId") Integer filialId,
                                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                        @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                                        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
        return filialService.findFilialProdutosPage(filialId, page, size, direction, orderBy);
    }

    @PostMapping
    public ResponseEntity<FilialDTO> postFilial(@RequestBody Filial filial){
        try{
            filialService.findById(filial.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(filialService.asDTO(filial));

        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
