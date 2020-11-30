package cl.global.logic.servicio.impl;


import cl.global.logic.Excepcion.PreconditionFailedExcepcion;
import cl.global.logic.domain.AutenticarDto;
import cl.global.logic.domain.PhonesDto;
import cl.global.logic.domain.UsuarioDto;
import cl.global.logic.entidad.EntidadPhones;
import cl.global.logic.entidad.EntidadUsuarios;
import cl.global.logic.repositorio.RepositorioUsuarios;
import cl.global.logic.servicio.IUsuarios;
import cl.global.logic.servicio.MapperUsuarios;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuariosImpl implements IUsuarios {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final Pattern REGEX_EMAIL =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern REGEX_PASSWORD =
            Pattern.compile("^(?=.*[0-9]{2})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);

    @Value("${token.secret}")
    private String tokenSecret;

    @Autowired
    private RepositorioUsuarios repositorioUsuarios;

    @Autowired
    private MapperUsuarios mapperUsuarios;

    @Autowired
    private TokenHelper tokenHelper;

    private final String[] user = {"Pepe", "Pipo", "Pato", "Papu"};

    private final List<PhonesDto> phones = Arrays.asList(new PhonesDto("123", "1", "2"),
            new PhonesDto("456", "3", "4"));

    private final List<String> emails = Arrays.asList("asd@gmail.com", "asd2@gmail.com", "asd3@gmail.com", "asd4@gmail.com");

    @PostConstruct
    public void init() {

        Iterator<String> iterator = emails.iterator();

        for (String name : user) {

            List<EntidadPhones> entidadPhonesList = new ArrayList<>();

            for (PhonesDto phone : phones) {
                EntidadPhones entidadPhones = EntidadPhones.builder()
                        .cityCode(phone.getCityCode())
                        .contryCode(phone.getContryCode())
                        .number(phone.getNumber())
                        .build();

                entidadPhonesList.add(entidadPhones);
            }

            EntidadUsuarios entidadUsuarios = EntidadUsuarios.builder()
                    .id(generateType4UUID().toString())
                    .name(name)
                    .isActive(true)
                    .lastLogin(LocalDate.now())
                    .password(hashPassword("12345"))
                    .entidadPhones(entidadPhonesList)
                    .token(crearAccessToken())
                    .build();

            String email = iterator.next();

            if (validarEmail(email)) {
                entidadUsuarios.setEmail(email);
            } else {
                throw new PreconditionFailedExcepcion("Email invalido");
            }

            repositorioUsuarios.saveAndFlush(entidadUsuarios);
        }


    }


    @Override
    public UsuarioDto findUsuarioById(String id) {
        EntidadUsuarios entidadUsuarios = repositorioUsuarios.findById(id).orElseThrow(() -> new IllegalStateException("the customer is not there"));
        return mapperUsuarios.mapUusarioToDto(entidadUsuarios);
    }

    @Override
    public List<UsuarioDto> findAll() {

        List<EntidadUsuarios> entidadUsuariosList = repositorioUsuarios.findAll();
        return mapperUsuarios.findAll(entidadUsuariosList);

    }

    @Override
    public UsuarioDto crearUsuario(UsuarioDto usuariosDto, String token) {
        EntidadUsuarios entidadUsuarios = repositorioUsuarios.save(mapperUsuarios.mapDtoToUsuario(usuariosDto, token));
        return mapperUsuarios.mapUusarioToDto(entidadUsuarios);

    }


    @Override
    public AutenticarDto autenticar() {

        AutenticarDto autenticarDto = AutenticarDto.builder()
                .token(crearAccessToken())
                .build();

        logger.info("Token creado con exito");

        return autenticarDto;
    }


    private String crearAccessToken() {

        Calendar calendar = Calendar.getInstance();
        long timeInMillis = calendar.getTimeInMillis();

        return Jwts.builder()
                .setId(tokenHelper.getJwtId())
                .setIssuedAt(new Date(timeInMillis))
                .setExpiration(tokenHelper.getExpirationDate(timeInMillis))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }


    private static UUID generateType4UUID() {
        return UUID.randomUUID();
    }

    private String hashPassword(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public static boolean validarEmail(String emailStr) {
        Matcher matcher = REGEX_EMAIL.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validarPassword(String password) {
        Matcher matcher = REGEX_PASSWORD.matcher(password);
        return matcher.find();
    }
}
