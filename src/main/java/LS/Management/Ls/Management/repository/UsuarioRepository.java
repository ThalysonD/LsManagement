package LS.Management.Ls.Management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import LS.Management.Ls.Management.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
}