package com.ithappens.interview.controllers;


import com.ithappens.interview.dtos.ClienteDTO;
import com.ithappens.interview.dtos.UsuarioDTO;
import com.ithappens.interview.models.Cliente;
import com.ithappens.interview.models.Usuario;
import com.ithappens.interview.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<UsuarioDTO> getUsuario(@RequestBody Usuario usuario) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.asDTO(usuarioService.findById(usuario.getId())));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> postUsuario(@RequestBody Usuario usuario) {
        try {
            usuario.setId(null);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.asDTO(usuarioService.post(usuario)));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
