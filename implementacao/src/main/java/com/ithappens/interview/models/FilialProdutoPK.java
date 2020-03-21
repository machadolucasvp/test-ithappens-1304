package com.ithappens.interview.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilialProdutoPK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer filial_id;

    private Integer produto_id;

}
