package com.ithappens.interview.controllers;


import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.models.Pedido;
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

    @GetMapping
    public List<Pedido> getAll() {
        return pedidoService.findAll();
    }

    @PostMapping
    public ResponseEntity<Pedido> postPedido(@RequestParam(value = "tipo", defaultValue = "entrada") String tipo,
                                             @RequestParam(value = "filial") Integer filialId,
                                             @RequestBody Pedido pedido) {
        if (tipo.equals(Tipo.getValueOf(Tipo.ENTRADA))) {
            pedidoService.addPedido(filialId, pedido, Tipo.ENTRADA);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);

        } else if (tipo.equals(Tipo.getValueOf(Tipo.SAIDA))) {

            pedidoService.addPedido(filialId, pedido, Tipo.SAIDA);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PutMapping("/{pedidoId}/item")
    public ResponseEntity<Pedido> updateItemPedidoStatus(@PathVariable Integer pedidoId,
                                                         @RequestParam(value = "id", required = false) Integer itemId,
                                                         @RequestParam(value = "status", defaultValue = "CANCELADO") String status,
                                                         @RequestParam(value = "finalizar", defaultValue = "false") Boolean isToProcess) {

        boolean isPresent = pedidoService.updateItemPedidoStatus(pedidoId, itemId, status, isToProcess);
        if (isPresent) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
