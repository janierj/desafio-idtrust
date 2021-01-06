package br.desafio.idtrust.entity;

import br.desafio.idtrust.entity.enumeration.Culture;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "request")
public class Request extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "culture")
    private Culture culture;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "json_response_moeda")
    private String jsonResponseMoeda;

    @Column(name = "json_response_cepea")
    private String jsonResponseCEPEA;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Culture getCulture() {
        return culture;
    }

    public void setCulture(Culture culture) {
        this.culture = culture;
    }

    public Request culture(Culture culture) {
        this.culture = culture;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Request date(LocalDate date) {
        this.date = date;
        return this;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Request valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public String getJsonResponseMoeda() {
        return jsonResponseMoeda;
    }

    public void setJsonResponseMoeda(String jsonResponseMoeda) {
        this.jsonResponseMoeda = jsonResponseMoeda;
    }

    public Request jsonResponseMoeda(String jsonResponseMoeda) {
        this.jsonResponseMoeda = jsonResponseMoeda;
        return this;
    }

    public String getJsonResponseCEPEA() {
        return jsonResponseCEPEA;
    }

    public void setJsonResponseCEPEA(String jsonResponseCEPEA) {
        this.jsonResponseCEPEA = jsonResponseCEPEA;
    }

    public Request jsonResponseCEPEA(String jsonResponseCEPEA) {
        this.jsonResponseCEPEA = jsonResponseCEPEA;
        return this;
    }

}
