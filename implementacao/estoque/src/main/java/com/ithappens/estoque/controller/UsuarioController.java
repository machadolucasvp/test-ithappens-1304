package com.ithappens.estoque.controller;

import com.ithappens.estoque.model.Usuario;
import com.ithappens.estoque.repository.UsuarioRepository;
import com.ithappens.estoque.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
@Api(value = "Operações Usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    @ApiOperation(value = "Retorna os usuários cadastrados")
    public ResponseEntity<List<Usuario>> buscarTodos(){
        List<Usuario> usuariosCadastrados = usuarioRepository.findAll();

        if(usuariosCadastrados.isEmpty()) {
            return noContent().build();
        }else{
            return ok(usuariosCadastrados);
        }
    }

    @GetMapping("/usuarioId/{id}")
    @ApiOperation(value = "Busca um usuário por id")
    public ResponseEntity<Usuario> buscaPorId(@PathVariable Long id){
        return ok(usuarioService.buscaPorId(id));
    }

    @PostMapping("/usuario")
    @ApiOperation(value = "Cadastra um novo usuário")
    public ResponseEntity<Usuario>cadastrar(@Valid @RequestBody Usuario usuario){
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/usuarioUpdate/{id}")
    @ApiOperation(value = "Atualiza dados de um usuário")
    public ResponseEntity<Usuario> atualizar(@Valid @RequestBody Usuario usuario){
        Usuario usuarioAtualizado = usuarioService.alterar(usuario);
        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.CREATED);
    }

    @DeleteMapping("/usuarioId/{id}")
    @ApiOperation(value = "Deleta um usuário")
    public ResponseEntity<Void>deletar(@PathVariable Long id){
        usuarioService.deletar(id);
        return noContent().build();
    }

}
