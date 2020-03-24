package com.ithappens.interview.controllers;


import com.ithappens.interview.dtos.PedidoDTO;
import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.models.Pedido;
import com.ithappens.interview.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/{pedidoId}")
    public ResponseEntity<PedidoDTO> getAll(@PathVariable Integer pedidoId) {
        Optional<PedidoDTO> pedido = Optional.of(pedidoService.asDTO(pedidoService.findById(pedidoId)));
        return pedido.map(pedidoDTO ->
                ResponseEntity.status(HttpStatus.OK).body(pedidoDTO))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> postPedido(@RequestParam(value = "tipo", defaultValue = "entrada") String tipo,
                                                @RequestParam(value = "filialId") Integer filialId,
                                                @RequestBody Pedido pedido) {
        if (tipo.equals(Tipo.getValueOf(Tipo.ENTRADA))) {
            try {
                PedidoDTO pedidoDTO = pedidoService.addPedido(filialId, pedido, Tipo.ENTRADA);
                return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);
            } catch (DataIntegrityViolationException exception) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
            }

        } else if (tipo.equals(Tipo.getValueOf(Tipo.SAIDA))) {
            PedidoDTO pedidoDTO = pedidoService.addPedido(filialId, pedido, Tipo.SAIDA);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDTO);

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

    @GetMapping("/page")
    public Page<PedidoDTO> findPedidosPageable(@RequestParam(value = "filialId", defaultValue = "0") Integer filialId,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page,
                                               @RequestParam(value = "linesPerPage", defaultValue = "12") Integer size,
                                               @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
                                               @RequestParam(value = "direction", defaultValue = "DESC") String direction) {

        return pedidoService.findPedidosPageable(page, size, direction, orderBy, filialId);
    }

}
