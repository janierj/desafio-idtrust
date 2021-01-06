package br.desafio.idtrust.repository;

import br.desafio.idtrust.entity.Request;
import br.desafio.idtrust.entity.enumeration.Culture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findOneByCultureAndDate(Culture culture, LocalDate date);

}
