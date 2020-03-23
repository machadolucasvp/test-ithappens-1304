package com.ithappens.interview.dtos;

import com.ithappens.interview.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilialDTO {

    private Integer id;

    private String nome;

    @Builder.Default
    Set<FilialProdutoDTO> produtos = new HashSet<>(0);

}
