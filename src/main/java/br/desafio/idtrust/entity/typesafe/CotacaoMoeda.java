package br.desafio.idtrust.entity.typesafe;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class CotacaoMoeda {

    private Currency code;

    private Currency codein;

    private String name;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal varBid;

    private BigDecimal pctChange;

    private BigDecimal bid;

    private BigDecimal ask;

    private Long timestamp;

    @JsonProperty("create_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    public Currency getCode() {
        return code;
    }

    public void setCode(Currency code) {
        this.code = code;
    }

    public Currency getCodein() {
        return codein;
    }

    public void setCodein(Currency codein) {
        this.codein = codein;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getVarBid() {
        return varBid;
    }

    public void setVarBid(BigDecimal varBid) {
        this.varBid = varBid;
    }

    public BigDecimal getPctChange() {
        return pctChange;
    }

    public void setPctChange(BigDecimal pctChange) {
        this.pctChange = pctChange;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}