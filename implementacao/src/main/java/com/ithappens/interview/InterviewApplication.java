package com.ithappens.interview;

import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.enums.Status;
import com.ithappens.interview.models.*;
import com.ithappens.interview.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class InterviewApplication implements CommandLineRunner {

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FilialProdutoRepository filialProdutoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    PagamentoRepository pagamentoRepository;


    public static void main(String[] args) {
        SpringApplication.run(InterviewApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Filial filial1 = Filial.builder()
                .nome("Renascença")
                .produtos(new HashSet<>())
                .build();

        Cliente client1 = Cliente.builder()
                .nome("Lucas Vinicius Pereira Machado")
                .cpf("51494649020")
                .build();

        Usuario usuario1 = Usuario.builder()
                .nome("Lucas Machado")
                .email("lucasvufma@gmail.com")
                .password("123")
                .ativo(true)
                .build();

        Produto produto1 = Produto.builder()
                .id(1).codigoDeBarras("123456789012555444332221568754456745")
                .sequencial(000000000001)
                .descricao("Refrigerante de 2 litros. Referido popularmente como coca," +
                        " é o refrigerante preferido pela população mundial")
                .nome("Coca-Cola")
                .custo(5.50)
                .filiais(new HashSet<>()).build();

        Produto produto2 = Produto.builder()
                .id(2).nome("Biscoito de Polvilho")
                .codigoDeBarras("123456789012551444332221568754456745")
                .sequencial(000000000002)
                .descricao("Biscoito de polvilho, excelente para tomar com café")
                .custo(7.49).filiais(new HashSet<>()).build();

        Pedido request1 = Pedido.builder()
                .observacao("Entregar no deposito 40")
                .tipo(Tipo.SAIDA).build();

        ItemPedido requestItem1 = ItemPedido.builder()
                .status(Status.PROCESSADO)
                .custoUnitario(2.0)
                .quantidade(40)
                .build();

        ItemPedido requestItem2 = ItemPedido.builder()
                .status(Status.ATIVO)
                .custoUnitario(4.0)
                .quantidade(20)
                .build();

        Pagamento pagamento1 = Pagamento.Cartao.builder().numeroDeParcelas(2)
                .numeroDoCartao(123123)
                .custo(2.0)
                .build();

        //Use case of add a product to filial
        FilialProduto filialProduto = FilialProduto.builder()
                .produto(produto1)
                .filial(filial1)
                .quantidadeEstoque(1)
                .build();
        filialRepository.save(filial1);
        produtoRepository.save(produto1);
        filialProdutoRepository.save(filialProduto);
        clienteRepository.save(client1);
        pagamentoRepository.save(pagamento1);

        //Use case to add a product to a request item
        requestItem1.setProduto(produto1);
        request1.setUsuario(usuario1);
        request1.setCliente(client1);
        request1.setPagamento(pagamento1);
        request1.setFilial(filial1);
        requestItem1.setPedido(request1);

        usuarioRepository.save(usuario1);
        produtoRepository.saveAll(Arrays.asList(produto1,produto2));
        pedidoRepository.save(request1);
        itemPedidoRepository.saveAll(Arrays.asList(requestItem1,requestItem2));
        System.out.println(request1.getItemsPedido());

    }

}
