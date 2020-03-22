package com.ithappens.interview.controllers;


import com.ithappens.interview.enums.Tipo;
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

    @PostMapping("/filial/{filialId}/pedido")
    public ResponseEntity<Pedido> postPedido(@RequestParam(value = "tipo", defaultValue = "entrada") String tipo,
                                             @RequestBody Pedido pedido,
                                             @PathVariable Integer filialId) {
        if (tipo.equals(Tipo.getValueOf(Tipo.ENTRADA))) {

            pedidoService.addPedidoEntrada(filialId, pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);

        } else if (tipo.equals(Tipo.getValueOf(Tipo.SAIDA))) {
            pedidoService.addPedidoSaida(filialId, pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
