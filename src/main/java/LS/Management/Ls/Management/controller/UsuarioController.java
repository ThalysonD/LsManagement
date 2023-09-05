package LS.Management.Ls.Management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import LS.Management.Ls.Management.CustomExceptions.CustomExceptions;
import LS.Management.Ls.Management.CustomExceptions.CustomExceptions.EmailDuplicadoException;
import LS.Management.Ls.Management.model.Usuario;
import LS.Management.Ls.Management.repository.UsuarioRepository;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) throws Exception {
        try {
            // Verifique se o email já existe
            if (usuarioRepository.existsByEmail(usuario.getEmail())) 
            {
                throw new CustomExceptions.EmailDuplicadoException("Este email já está em uso.");
            }

            // Salve o usuário no banco de dados
            Usuario novoUsuario = usuarioRepository.save(usuario);

            // Retorne o usuário criado com um status de sucesso (por exemplo, 201 Created)
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
            } catch (EmailDuplicadoException e) {
            // Trate a exceção de email duplicado retornando um status de erro adequado (por exemplo, 400 Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }   
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
}