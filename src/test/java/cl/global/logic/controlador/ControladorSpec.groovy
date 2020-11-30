package cl.global.logic.controlador

import cl.global.logic.domain.AutenticarDto
import cl.global.logic.domain.RespuestaDto
import cl.global.logic.domain.UsuarioDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(value = "test")
class ControladorSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate


    def "Crear usuario error 412"() {

        given: "Usuario a crear con token jwt expirado"
        def usuarioDto = new UsuarioDto(id: "1", email: "asd5@gmail.com", password: "12345")
        def headers = new HttpHeaders();
        headers.add("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1MmM1ZmIxZi02NzFmLTRmMzEtYmVhZC0xZmFhNzg4YmRhNzEiLCJpYXQiOjE2MDY2MDk2ODIsImV4cCI6MTYwNjYwOTczMn0.lLDI_baRY16W75q6hy7AXw16qS_jpAx8zvTZx27CfiTTyENPu5AGt84ZX8xfYcihX7uGVYbumRGcTElrA4SRmg")

        HttpEntity<UsuarioDto> request = new HttpEntity<>(usuarioDto, headers);

        when: "Se llame al controlador"
        def entity = restTemplate.postForEntity("/usuario", request, RespuestaDto.class)

        then:
        entity.statusCode == HttpStatus.PRECONDITION_FAILED
        entity.body.codigo == "3"
    }

    def "Crear usuario ok 200"() {

        given: "Usuario a crear con token jwt valido"

        def entityLogin = restTemplate.getForEntity("/usuario/autenticar", AutenticarDto.class)
        def usuarioDto = new UsuarioDto(email: "asd5@gmail.com",password: "Abcd12yu#")
        def headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + entityLogin.body.token)
        HttpEntity<UsuarioDto> request = new HttpEntity<>(usuarioDto, headers);

        when: "Se llame al controlador"

        def entity = restTemplate.postForEntity("/usuario", request, UsuarioDto.class)

        then:
        entity.statusCode == HttpStatus.OK
    }


    def "Crear usuario sin enviar token jwt"() {

        given: "Usuario a crear con token jwt expirado"
        def usuarioDto = new UsuarioDto(id: "1", email: "asd5@gmail.com", password: "12345")
        def headers = new HttpHeaders();
        headers.add("Authorization", "")

        HttpEntity<UsuarioDto> request = new HttpEntity<>(usuarioDto, headers);

        when: "Se llame al controlador"
        def entity = restTemplate.postForEntity("/usuario", request, RespuestaDto.class)

        then:
        entity.statusCode == HttpStatus.PRECONDITION_FAILED
        entity.body.codigo == "1"
        entity.body.mensaje == "No existe token"
    }

    def "Crear usuario correo ya existe"() {

        given: "Usuario a crear con token jwt valido"

        def entityLogin = restTemplate.getForEntity("/usuario/autenticar", AutenticarDto.class)
        def usuarioDto = new UsuarioDto(email: "asd@gmail.com",password: "Abcd12yu#")
        def headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + entityLogin.body.token)
        HttpEntity<UsuarioDto> request = new HttpEntity<>(usuarioDto, headers);

        when: "Se llame al controlador"
        def entity = restTemplate.postForEntity("/usuario", request, RespuestaDto.class)

        then:
        entity.statusCode == HttpStatus.PRECONDITION_FAILED
        entity.body.codigo == "2"
    }

    def "Buscar todos los usuarios"() {

        given: "Usuarios a buscar con token jwt valido"

        def entityLogin = restTemplate.getForEntity("/usuario/autenticar", AutenticarDto.class)
        def headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + entityLogin.body.token)
        HttpEntity<Object> request = new HttpEntity<>(headers);

        when: "Se llame al controlador"
        def entity = restTemplate.exchange("/usuario/all", HttpMethod.GET, new HttpEntity<Object>(headers),
                List.class)

        then:
        entity.statusCode == HttpStatus.OK
    }


}
