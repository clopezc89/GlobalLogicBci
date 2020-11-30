package cl.global.logic.servicio;

import cl.global.logic.Excepcion.PreconditionFailedExcepcion;
import cl.global.logic.domain.PhonesDto;
import cl.global.logic.domain.UsuarioDto;
import cl.global.logic.entidad.EntidadPhones;
import cl.global.logic.entidad.EntidadUsuarios;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static cl.global.logic.servicio.impl.UsuariosImpl.validarEmail;
import static cl.global.logic.servicio.impl.UsuariosImpl.validarPassword;

@Component
public class MapperUsuarios {

    public UsuarioDto mapUusarioToDto(EntidadUsuarios entidadUsuarios) {
        if (entidadUsuarios == null) {
            return null;
        }

        UsuarioDto usuarioDto = UsuarioDto.builder()
                .id(entidadUsuarios.getId())
                .name(entidadUsuarios.getName())
                .isActive(entidadUsuarios.getIsActive())
                .lastLogin(entidadUsuarios.getLastLogin())
                .password(entidadUsuarios.getPassword())
                .email(entidadUsuarios.getEmail())
                .created(dateToLocaldate(entidadUsuarios.getCreatedDate()))
                .modified(dateToLocaldate(entidadUsuarios.getModifiedDate()))
                .build();

        if (null != entidadUsuarios.getEntidadPhones() && !entidadUsuarios.getEntidadPhones().isEmpty()) {
            usuarioDto.setPhones(mapPhoneToDto(entidadUsuarios));
        }

        return usuarioDto;
    }

    public EntidadUsuarios mapDtoToUsuario(UsuarioDto usuarioDto, String token) {
        if (usuarioDto == null) {
            return null;
        }


        EntidadUsuarios entidadUsuarios = EntidadUsuarios.builder()
                .id(generateType4UUID().toString())
                .name(usuarioDto.getName())
                .isActive(true)
                .lastLogin(LocalDate.now())
                .token(token)
                .build();

        if (null != usuarioDto.getPhones() && !usuarioDto.getPhones().isEmpty())
            entidadUsuarios.setEntidadPhones(mapDtoToPhone(usuarioDto));

        if (validarEmail(usuarioDto.getEmail())) {
            entidadUsuarios.setEmail(usuarioDto.getEmail());
        }else {
            throw new PreconditionFailedExcepcion("Email invalido");
        }

        if (validarPassword(usuarioDto.getPassword())) {
            entidadUsuarios.setEmail(usuarioDto.getPassword());
        }else {
            throw new PreconditionFailedExcepcion("Password invalido");
        }

        return entidadUsuarios;
    }


    public List<PhonesDto> mapPhoneToDto(EntidadUsuarios entidadUsuarios) {
        if (entidadUsuarios == null) {
            return null;
        }

        List<PhonesDto> phonesDtoList = new ArrayList<>();

        for (EntidadPhones phoneEntidad : entidadUsuarios.getEntidadPhones()) {
            PhonesDto phonesDto = PhonesDto.builder()
                    .cityCode(phoneEntidad.getCityCode())
                    .contryCode(phoneEntidad.getContryCode())
                    .number(phoneEntidad.getNumber())
                    .build();
            phonesDtoList.add(phonesDto);
        }

        return phonesDtoList;
    }

    public List<EntidadPhones> mapDtoToPhone(UsuarioDto usuarioDto) {

        if (usuarioDto == null) {
            return null;
        }

        List<EntidadPhones> entidadPhonesList = new ArrayList<>();

        for (PhonesDto phonesDto : usuarioDto.getPhones()) {
            EntidadPhones entidadPhones = EntidadPhones.builder()
                    .cityCode(phonesDto.getCityCode())
                    .contryCode(phonesDto.getContryCode())
                    .number(phonesDto.getNumber())
                    .build();
            entidadPhonesList.add(entidadPhones);
        }

        return entidadPhonesList;
    }

    private static UUID generateType4UUID() {
        return UUID.randomUUID();
    }

    public List<UsuarioDto> findAll(List<EntidadUsuarios> entidadUsuariosList) {

        List<UsuarioDto> usuarioDtoList = new ArrayList<>();

        for (EntidadUsuarios entidadUsuarios : entidadUsuariosList) {
            UsuarioDto usuarioDto = UsuarioDto.builder()
                    .id(entidadUsuarios.getId())
                    .name(entidadUsuarios.getName())
                    .isActive(entidadUsuarios.getIsActive())
                    .lastLogin(entidadUsuarios.getLastLogin())
                    .password(entidadUsuarios.getPassword())
                    .email(entidadUsuarios.getEmail())
                    .phones(mapPhoneToDto(entidadUsuarios))
                    .created(dateToLocaldate(entidadUsuarios.getCreatedDate()))
                    .modified(dateToLocaldate(entidadUsuarios.getModifiedDate()))
                    .build();

            usuarioDtoList.add(usuarioDto);

        }

        return usuarioDtoList;
    }


    public LocalDate dateToLocaldate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
