package cl.global.logic.servicio;

import cl.global.logic.domain.AutenticarDto;
import cl.global.logic.domain.UsuarioDto;

import java.util.List;

public interface IUsuarios {

    UsuarioDto findUsuarioById(String id);

    UsuarioDto crearUsuario(UsuarioDto usuariosDto, String token);

    List<UsuarioDto> findAll();

    AutenticarDto autenticar();
}
