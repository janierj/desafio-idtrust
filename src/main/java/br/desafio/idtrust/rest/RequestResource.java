package br.desafio.idtrust.rest;

import br.desafio.idtrust.entity.enumeration.Culture;
import br.desafio.idtrust.entity.enumeration.Currency;
import br.desafio.idtrust.service.RequestService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequestMapping("/api")
@RestController
public class RequestResource {

    private final RequestService requestService;

    public RequestResource(RequestService requestService) {
        this.requestService = requestService;
    }

    /**
     * {@code GET  /cotacao/{cultura}/{dataCotacao}} : Calcular cotação.
     *
     * @param cultura A {@link br.desafio.idtrust.entity.enumeration.Culture} a consultar
     * @param dataCotacao A data da cotação em formato: yyyy-MM-dd
     * @return O valor da cotaçao
     */
    @GetMapping("/cotacao/{cultura}/{dataCotacao}")
    public ResponseEntity<String> getCotacao(@PathVariable Culture cultura,
                                                     @PathVariable(value = "dataCotacao")
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataCotacao) {

        BigDecimal valorCotacao = requestService.findCotacao(cultura, dataCotacao);
        return ResponseEntity.ok().body(Currency.BRL + " " + valorCotacao);
    }
}
