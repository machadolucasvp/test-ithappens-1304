package com.ithappens.interview.controllers;


import com.ithappens.interview.dtos.UsuarioDTO;
import com.ithappens.interview.models.Usuario;
import com.ithappens.interview.services.UsuarioService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Integer usuarioId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarioService.asDTO(usuarioService.findById(usuarioId)));
        } catch (ObjectNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> postUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOptional = usuarioService.findByEmailAsOptional(usuario.getEmail());
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.asDTO(usuarioService.post(usuario)));
        }

    }

}
