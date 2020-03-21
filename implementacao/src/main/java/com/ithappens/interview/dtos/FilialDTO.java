package com.ithappens.interview.dtos;

import com.ithappens.interview.models.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilialDTO {

    private Integer id;

    private String nome;

    @Builder.Default
    Set<ProdutoDTO> produtos = new HashSet<>(0);

}
