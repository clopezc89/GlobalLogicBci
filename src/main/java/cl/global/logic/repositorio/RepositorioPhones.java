package cl.global.logic.repositorio;

import cl.global.logic.entidad.EntidadPhones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RepositorioPhones extends JpaRepository<EntidadPhones, Long> {

}
