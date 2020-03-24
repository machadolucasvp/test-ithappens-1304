package com.ithappens.interview.controllers;

import com.ithappens.interview.dtos.ClienteDTO;
import com.ithappens.interview.models.Cliente;
import com.ithappens.interview.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/cliente"))
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> getCliente(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.asDTO(clienteService.findById(cliente.getId())));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> postCliente(@RequestBody Cliente cliente) {
        try {
            cliente.setId(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.asDTO(clienteService.post(cliente)));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
