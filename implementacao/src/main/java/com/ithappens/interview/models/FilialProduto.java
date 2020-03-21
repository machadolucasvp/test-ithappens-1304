package com.ithappens.interview.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@Table(name = "filial_produto")
@AllArgsConstructor
@NoArgsConstructor
public class FilialProduto implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @Builder.Default
    private FilialProdutoPK id = new FilialProdutoPK();

    @ManyToOne
    @MapsId("filial_id")
    private Filial filial;

    @ManyToOne
    @MapsId("produto_id")
    private Produto produto;

    private Integer quantidadeProdutos;

}
