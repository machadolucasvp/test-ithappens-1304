package com.ithappens.interview;

import com.ithappens.interview.enums.Status;
import com.ithappens.interview.models.Branch;
import com.ithappens.interview.models.Client;
import com.ithappens.interview.models.Request;
import com.ithappens.interview.repositories.BranchRepository;
import com.ithappens.interview.repositories.ClientRepository;
import com.ithappens.interview.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class InterviewApplication implements CommandLineRunner {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    ClientRepository clientRepository;

    public static void main(String[] args) {
        SpringApplication.run(InterviewApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Branch filial1 = new Branch(0, "Renascença");
        Branch filial2 = new Branch(0, "Cohama");

        Client client1 = new Client();
        Client client2 = new Client(0, "Lucas Machado");

        Request request1 = new Request(0, "Compra ontem", Status.OPEN);
        Request request2 = new Request(0, "Compra hoje", Status.PROCESSING);

        clientRepository.saveAll(Arrays.asList(client1, client2));
        branchRepository.saveAll(Arrays.asList(filial1, filial2));
        requestRepository.saveAll(Arrays.asList(request1, request2));

    }

}
