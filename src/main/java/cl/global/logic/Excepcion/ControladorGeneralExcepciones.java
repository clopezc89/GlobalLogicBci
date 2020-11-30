package cl.global.logic.Excepcion;

import cl.global.logic.domain.RespuestaDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControladorGeneralExcepciones {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(value = PreconditionFailedExcepcion.class)
    public ResponseEntity<RespuestaDto> solicitarGestionExcepcion(Exception ex) {

        PreconditionFailedExcepcion spv = (PreconditionFailedExcepcion) ex;
        return new ResponseEntity<>(new RespuestaDto("1", spv.getMensaje()), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<RespuestaDto> procesarErrorNoControlado(Exception ex) {

        logger.error("Error", ex);

        if (ex instanceof DataIntegrityViolationException) {
            return new ResponseEntity<>(new RespuestaDto("2", "El correo ya esta registrado"), HttpStatus.PRECONDITION_FAILED);
        } else if (ex instanceof ExpiredJwtException || ex instanceof UnsupportedJwtException || ex instanceof MalformedJwtException) {
            return new ResponseEntity<>(new RespuestaDto("3", "El token es invalido"), HttpStatus.PRECONDITION_FAILED);
        }


        return new ResponseEntity<>(new RespuestaDto("9999", "Error general"), HttpStatus.BAD_REQUEST);
    }


}

