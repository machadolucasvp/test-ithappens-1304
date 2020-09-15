package com.ithappens.estoque.service;

import com.ithappens.estoque.exception.ServiceException;
import com.ithappens.estoque.model.FormaPagamento;
import com.ithappens.estoque.repository.FormaPagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private final FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento salvar(FormaPagamento formaPagamento){
        verificaFormaPagamentoValida(formaPagamento.getDescricao());
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento alterar(FormaPagamento formaPagamento){
        if (formaPagamento.getId()==null){
            throw new ServiceException("Id da Forma de Pagamento não informado");
        }
        return formaPagamentoRepository.findById(formaPagamento.getId()).map(formaPagamentoSalva -> {
            if(!formaPagamento.getDescricao().equals(formaPagamentoSalva.getDescricao())) verificaFormaPagamentoValida(formaPagamento.getDescricao());
            return formaPagamentoRepository.save(formaPagamento);
        }).orElseThrow(()->new ServiceException("Forma de Pagamento com id " + formaPagamento.getId() + " inexistente"));
    }

    public FormaPagamento buscaPorId(Long id){
        return formaPagamentoRepository.findById(id).orElseThrow(()->new ServiceException("Forma de Pagamento com id " + id + " inexistente"));
    }

    private void verificaFormaPagamentoValida(String descricao){
        if(formaPagamentoRepository.findByDescricao(descricao).isPresent()){
            throw new ServiceException("Forma de Pagamento já cadastrada");
        }
    }
}
