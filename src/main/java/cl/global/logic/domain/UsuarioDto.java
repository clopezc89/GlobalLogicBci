package cl.global.logic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioDto {

    private String id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    private LocalDate lastLogin;
    private List<PhonesDto> phones;
    private LocalDate created;
    private LocalDate modified;
}
