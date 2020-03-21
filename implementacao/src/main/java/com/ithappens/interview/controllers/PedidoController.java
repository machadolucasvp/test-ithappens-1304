package com.ithappens.interview.controllers;



import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.Pedido;
import com.ithappens.interview.models.Produto;
import com.ithappens.interview.services.FilialService;
import com.ithappens.interview.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private FilialService filialService;

    @GetMapping
    public List<Pedido> getAll() {
        return pedidoService.findAll();
    }

    @PostMapping("/filial/{filialId}/entrada")
    public ResponseEntity<Produto> postProduto(@RequestBody Produto produto,
                                               @RequestParam Integer quantidade,
                                               @PathVariable Integer filialId) {
        Filial filial = filialService.findById(filialId);
        filialService.addProduto(produto,filial,quantidade);
        return new ResponseEntity<>(produto,HttpStatus.CREATED);
    }

}
