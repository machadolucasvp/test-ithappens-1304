package com.ithappens.interview;

import com.ithappens.interview.enums.Tipo;
import com.ithappens.interview.enums.Status;
import com.ithappens.interview.models.Filial;
import com.ithappens.interview.models.Cliente;
import com.ithappens.interview.models.Pedido;
import com.ithappens.interview.models.ItemPedido;
import com.ithappens.interview.repositories.FilialRepository;
import com.ithappens.interview.repositories.ClienteRepository;
import com.ithappens.interview.repositories.ItemPedidoRepository;
import com.ithappens.interview.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class InterviewApplication implements CommandLineRunner {

    @Autowired
    FilialRepository filialRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    public static void main(String[] args) {
        SpringApplication.run(InterviewApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Filial filial1 = new Filial(0, "Renascença");
        Filial filial2 = new Filial(0, "Cohama");

        Cliente client1 = new Cliente();
        Cliente client2 = new Cliente(0, "Lucas Machado");

        Pedido request1 = Pedido.builder().nota("Alguma observação").tipo(Tipo.SAIDA).build();
        Pedido request2 = Pedido.builder().nota("Alguma outra observação").tipo(Tipo.SAIDA).build();

        ItemPedido requestItem1 = ItemPedido.builder().status(Status.PROCESSANDO).custoUnitario(2.0).quantidade(40).build();
        ItemPedido requestItem2 = ItemPedido.builder().status(Status.ATIVO).custoUnitario(4.0).quantidade(20).build();

        requestItem1.setPedido(request1);


        clienteRepository.saveAll(Arrays.asList(client1, client2));
        filialRepository.saveAll(Arrays.asList(filial1, filial2));
        pedidoRepository.saveAll(Arrays.asList(request1, request2));
        itemPedidoRepository.saveAll(Arrays.asList(requestItem1,requestItem2));
        System.out.println(request1.getItemsPedido());

    }

}
