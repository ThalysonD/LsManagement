package LS.Management.Ls.Management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LS.Management.Ls.Management.model.Usuario;
import LS.Management.Ls.Management.repository.UsuarioRepository;
import LS.Management.Ls.Management.CustomExceptions.CustomExceptions.EmailDuplicadoException;
import LS.Management.Ls.Management.CustomExceptions.CustomExceptions.SenhaMinimaException;
import LS.Management.Ls.Management.CustomExceptions.CustomExceptions.TelefoneMinimoException;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvarUsuario(Usuario usuario) throws Exception {
        if (usuario.getTelefone() == null || usuario.getTelefone().length() < 12) {
            throw new TelefoneMinimoException("O telefone deve ter pelo menos 12 caracteres");
        }
        
        if (usuario.getSenha().length() < 6) {
            throw new SenhaMinimaException("Senha deve ter no mínimo 6 caracteres");
        }

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new EmailDuplicadoException("Email já cadastrado");  
        }
        return usuarioRepository.save(usuario);
    }
}