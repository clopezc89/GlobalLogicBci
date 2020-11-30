package cl.global.logic.controlador;

import cl.global.logic.domain.AutenticarDto;
import cl.global.logic.domain.UsuarioDto;
import cl.global.logic.servicio.IUsuarios;
import cl.global.logic.servicio.impl.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
public class Controlador {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IUsuarios iUsuarios;

    @Autowired
    private TokenHelper tokenHelper;

    @GetMapping(path = "/{id}")
    public UsuarioDto getUsuarioById(@PathVariable String id, HttpServletRequest request) {
        logger.info("Consultando id: " + id);

        tokenHelper.validarToken(request);

        return iUsuarios.findUsuarioById(id);

    }

    @GetMapping(path = "/all")
    public List<UsuarioDto> getUsuarioFindAll(HttpServletRequest request) {

        tokenHelper.validarToken(request);

        return iUsuarios.findAll();

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public UsuarioDto crearUsuario(@RequestBody UsuarioDto usuariosDto, HttpServletRequest request) {
        logger.info(usuariosDto.toString());

        String token = tokenHelper.validarToken(request);

        return iUsuarios.crearUsuario(usuariosDto, token);
    }

    @GetMapping(path = "/autenticar")
    public AutenticarDto auntenticarUsuario() {
        logger.info("Autenticando");
        return iUsuarios.autenticar();
    }


}
