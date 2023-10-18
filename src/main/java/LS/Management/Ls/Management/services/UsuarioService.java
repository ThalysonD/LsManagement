package LS.Management.Ls.Management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import LS.Management.Ls.Management.model.Funcionario;
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
		validarUsuario(usuario);
		return usuarioRepository.save(usuario);
	}

	public Funcionario adicionarFuncionario(Long usuarioId, Funcionario funcionario) {
	    Usuario usuario = usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

	    validarFuncionario(funcionario);

	    if (usuario.getFuncionarios().stream().anyMatch(f -> f.getUsername().equals(funcionario.getUsername()))) {
	        throw new RuntimeException("Nome do funcionário já em uso para este usuário");
	    }

	    usuario.adicionarFuncionario(funcionario);

	    return funcionario;
	}


	private void validarUsuario(Usuario usuario) throws Exception {
		if (usuario.getTelefone() == null || usuario.getTelefone().length() < 12) {
			throw new TelefoneMinimoException("O telefone deve ter pelo menos 12 caracteres");
		}

		if (usuario.getSenha() == null || usuario.getSenha().length() < 6) {
			throw new SenhaMinimaException("Senha deve ter no mínimo 6 caracteres");
		}

		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new EmailDuplicadoException("Email já cadastrado");
		}
	}

	private void validarFuncionario(Funcionario funcionario) {
		if (funcionario.getUsername() == null || funcionario.getUsername().isEmpty()) {
			throw new RuntimeException("Nome do funcionário é obrigatório");
		}

		if (funcionario.getSenha() == null || funcionario.getSenha().length() < 6) {
			throw new RuntimeException("Senha do funcionário deve ter no mínimo 6 caracteres");
		}
	}
}