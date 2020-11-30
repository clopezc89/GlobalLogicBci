package cl.global.logic.repositorio;

import cl.global.logic.entidad.EntidadUsuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositorioUsuarios extends JpaRepository<EntidadUsuarios, String> {

}
