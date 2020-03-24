package com.ithappens.interview.controllers;

import com.ithappens.interview.dtos.FilialDTO;
import com.ithappens.interview.dtos.FilialProdutoDTO;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.services.FilialService;
import com.ithappens.interview.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/filial")
public class FilialController {

    @Autowired
    FilialService filialService;

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/{filialId}/produtos")
    public ResponseEntity<Page<FilialProdutoDTO>> getFilialProdutosPage(@PathVariable(value = "filialId") Integer filialId,
                                                                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                        @RequestParam(value = "size", defaultValue = "12") Integer size,
                                                                        @RequestParam(value = "direction", defaultValue = "DESC") String direction,
                                                                        @RequestParam(value = "orderBy", defaultValue = "id") String orderBy) {
        Page<FilialProdutoDTO> pageFilialProdutoDTO = filialService
                .findFilialProdutosPage(filialId, page, size, direction, orderBy);
        if (pageFilialProdutoDTO.hasContent()) {
            return ResponseEntity.status(HttpStatus.OK).body(pageFilialProdutoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping
    public ResponseEntity<FilialDTO> postFilial(@RequestBody Filial filial) {

        Optional<Filial> existentFilial = filialService.findByIdAsOptional(filial.getId());
        if (existentFilial.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(filialService.asDTO(filialService.post(filial)));

        }
    }

}
