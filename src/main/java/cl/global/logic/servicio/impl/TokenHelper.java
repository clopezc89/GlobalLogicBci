package cl.global.logic.servicio.impl;


import cl.global.logic.Excepcion.PreconditionFailedExcepcion;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Component
public class TokenHelper {

    private static final String PREFIX = "Bearer";
    private static final String HEADER = "Authorization";

    @Value("${token.secret}")
    private String tokenSecret;

    @Value("${token.tiempo.valido}")
    private Integer tiempoToken;

    private void getTokenId(String token) {

        Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();

    }

    public String validarToken(HttpServletRequest request) {


        if (!existeJWTToken(request)) {
            throw new PreconditionFailedExcepcion("No existe token");
        }

        String token = request.getHeader(HEADER);

        if (token.startsWith(PREFIX + " ")) {

            token = token.replace(PREFIX + " ", "");

            getTokenId(token);

        }

        return token;
    }

    private boolean existeJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    public String getJwtId() {
        return UUID.randomUUID().toString();
    }

    public Date getExpirationDate(long timeInMillis) {
        return new Date(timeInMillis + tiempoToken);
    }
}
