package com.ithappens.interview.repositories;

import com.ithappens.interview.models.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilialRepository extends JpaRepository<Filial, Integer> {
}
