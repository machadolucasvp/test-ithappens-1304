package com.ithappens.interview.controllers;

import com.ithappens.interview.dtos.ClienteDTO;
import com.ithappens.interview.models.Cliente;
import com.ithappens.interview.services.ClienteService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(("/cliente"))
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable Integer clienteId) {
        Optional<Cliente> cliente = clienteService.findByIdAsOptional(clienteId);
        if (cliente.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(clienteService.asDTO(clienteService.findById(clienteId)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping
    public ResponseEntity<ClienteDTO> postCliente(@RequestBody Cliente cliente) {
        Optional<Cliente> clienteOptional = clienteService.findByCpfAndCnpj(cliente.getCpf(), cliente.getCnpj());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.asDTO(clienteService.post(cliente)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
