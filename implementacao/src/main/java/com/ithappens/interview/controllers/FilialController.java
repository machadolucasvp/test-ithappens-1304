package com.ithappens.interview.controllers;

import com.ithappens.interview.dtos.FilialDTO;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.Produto;
import com.ithappens.interview.services.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/filial")
public class FilialController {

    @Autowired
    FilialService filialService;

    @GetMapping("/{id}")
    public FilialDTO get(@PathVariable Integer id) {
        return filialService.asDTO(filialService.findById(id));
    }

    @PostMapping("/filial/{filialId}/produto")
    public ResponseEntity<Produto> postProduto(@RequestBody Produto produto,
                                               @RequestParam(value = "qtd", defaultValue = "1") Integer quantidade,
                                               @PathVariable Integer filialId) {

        Filial filial = filialService.findById(filialId);
        filialService.addProduto(produto, filial, quantidade);
        return new ResponseEntity<>(produto, HttpStatus.CREATED);
    }

}
