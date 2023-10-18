package LS.Management.Ls.Management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import LS.Management.Ls.Management.CustomExceptions.CustomExceptions;
import LS.Management.Ls.Management.model.Funcionario;
import LS.Management.Ls.Management.model.Usuario;
import LS.Management.Ls.Management.repository.UsuarioRepository;
import LS.Management.Ls.Management.services.UsuarioService;

@RestController
@RequestMapping("/users")
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<Object> criarUsuario(@RequestBody Usuario usuario) throws Exception {
		try {
			// Verifica se o email já existe
			if (usuarioRepository.existsByEmail(usuario.getEmail())) {
				throw new CustomExceptions.EmailDuplicadoException("Este email já está em uso.");
			}

			// Verifica se o telefone tem pelo menos 12 caracteres
			if (usuario.getTelefone() == null || usuario.getTelefone().length() < 12) {
				throw new CustomExceptions.TelefoneMinimoException("O telefone deve ter pelo menos 12 caracteres");
			}

			// Verifica se a senha tem pelo menos 6 caracteres
			if (usuario.getSenha().length() < 6) {
				throw new CustomExceptions.SenhaMinimaException("A senha deve ter pelo menos 6 caracteres");
			}

			// Adiciona o funcionário ao usuário
			Funcionario funcionario = new Funcionario();
			funcionario.setUsername("Funcionario1");
			funcionario.setSenha("senha123");
			usuario.adicionarFuncionario(funcionario);

			// Salva o usuário no banco de dados
			Usuario novoUsuario = usuarioService.salvarUsuario(usuario);

			// Retorna o usuário criado com um status de sucesso (por exemplo, 201 Created)
			return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
		} catch (CustomExceptions.EmailDuplicadoException | CustomExceptions.SenhaMinimaException
				| CustomExceptions.TelefoneMinimoException e) {
			// Trata a exceção de email duplicado retornando um status de erro adequado (por
			// exemplo, 400 Bad Request)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/{usuarioId}/funcionarios")
	public ResponseEntity<Object> adicionarFuncionario(@PathVariable Long usuarioId,
			@RequestBody Funcionario funcionario) {
		try {
			Funcionario novoFuncionario = usuarioService.adicionarFuncionario(usuarioId, funcionario);
			return ResponseEntity.status(HttpStatus.CREATED).body(novoFuncionario);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		usuarios.forEach(usuario -> usuario.setFuncionarios(null));
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{userId}/funcionarios")
	public ResponseEntity<List<Funcionario>> listarFuncionarios(@PathVariable Long userId) {
		Usuario usuario = usuarioRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		List<Funcionario> funcionarios = usuario.getFuncionarios();
		return ResponseEntity.ok(funcionarios);
	}
}