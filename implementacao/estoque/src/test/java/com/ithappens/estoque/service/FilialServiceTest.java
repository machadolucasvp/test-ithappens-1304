package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.Filial;
import com.ithappens.estoque.repository.FilialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FilialServiceTest {

    @Mock
    FilialRepository filialRepository;

    @InjectMocks
    FilialService filialService;

   Filial filial;

   @BeforeEach
    void init(){filial = Filial.builder().id(1L).descricao("Cidade Operária").build();}

    @Test
    void aoSalvar_RetornarFilialSalva(){
       when(filialRepository.save(any(Filial.class))).thenReturn(filial);

       Filial novaFilial = filialService.salvar(Filial.builder().descricao("Cidade Operária").build());

       assertEquals(filial.getId(), novaFilial.getId());
    }

    @Test
    void aoAlterarFilial_RetornarDadosAtualizados(){
       Filial filialNovosDados = Filial.builder().id(1L).descricao("Mix Cidade Operária").build();

       when(filialRepository.findById(any(Long.class))).thenReturn(Optional.of(filial));
       when(filialRepository.save(any(Filial.class))).thenReturn(filialNovosDados);

       Filial filialAtualizada = filialService.alterar(filialNovosDados);

       assertEquals(filialAtualizada.getId(), filialNovosDados.getId());
       assertEquals(filialAtualizada.getDescricao(), filialNovosDados.getDescricao());

    }

    @Test
    void aoVerificarFilialInvalida_LancarExcecao(){
       when(filialRepository.findByDescricao(any(String.class))).thenReturn(Optional.of(filial));

       assertThrows(ServiceException.class, ()->filialService.salvar(filial));
    }
}
