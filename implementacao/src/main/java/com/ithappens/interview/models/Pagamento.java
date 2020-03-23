package com.ithappens.interview.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "forma")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Pagamento.Cartao.class, name = "cartao"),
        @JsonSubTypes.Type(value = Pagamento.Boleto.class, name = "boleto"),
        @JsonSubTypes.Type(value = Pagamento.Avista.class, name = "a vista")
})
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double custo;


    @JsonTypeName("cartao")
    @SuperBuilder
    @Entity
    @NoArgsConstructor
    public static class Cartao extends Pagamento {
        public Integer numeroDeParcelas;
        public Integer numeroDoCartao;
    }

    @JsonTypeName("boleto")
    @SuperBuilder
    @Entity
    @NoArgsConstructor
    public static class Boleto extends Pagamento {
        public String dataVencimento;
        public String dataEmissao;
    }

    @JsonTypeName("a vista")
    @SuperBuilder
    @Entity
    @NoArgsConstructor
    public static class Avista extends Pagamento {
    }

}
