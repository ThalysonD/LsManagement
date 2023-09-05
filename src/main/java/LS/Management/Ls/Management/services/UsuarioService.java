package LS.Management.Ls.Management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LS.Management.Ls.Management.CustomExceptions.CustomExceptions;
import LS.Management.Ls.Management.model.Usuario;
import LS.Management.Ls.Management.repository.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) throws Exception {
    if (usuarioRepository.existsByEmail(usuario.getEmail())) {
        throw new CustomExceptions.EmailDuplicadoException("Já existe um usuário cadastrado com este email");
    }
    return usuarioRepository.save(usuario);
    }
}