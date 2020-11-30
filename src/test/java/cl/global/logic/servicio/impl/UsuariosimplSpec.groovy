package cl.global.logic.servicio.impl

import cl.global.logic.domain.PhonesDto
import cl.global.logic.domain.UsuarioDto
import cl.global.logic.entidad.EntidadPhones
import cl.global.logic.entidad.EntidadUsuarios
import cl.global.logic.repositorio.RepositorioUsuarios
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class UsuariosimplSpec extends Specification {

    @Autowired
    private UsuariosImpl usuarios;

    def "Crear usuario"() {

        given: "Stub del save del repositorio"

        def stubbedRepositorioUsuarios = Stub(RepositorioUsuarios) {
            save(*_) >> new EntidadUsuarios("1", "Pepe", "asd5@gmail.com", "Abc12yu#", true, "jwt", LocalDate.now(), new ArrayList<EntidadPhones>())
        }

        when: "Se llame a crear el usuario"

        def usuario = stubbedRepositorioUsuarios.save()
        def usuario2 = usuarios.crearUsuario(new UsuarioDto(id: "1", name: "Pepe",email: "asd5@gmail.com",
                password: "Abc12yu#", isActive: true, lastLogin: LocalDate.now(),
                phones: new ArrayList<PhonesDto>(),created: LocalDate.now(),modified: LocalDate.now()), "jwt")

        then:
        usuario2.name == usuario.name


    }

}
