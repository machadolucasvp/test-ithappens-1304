package com.ithappens.interview.repositories;


import com.ithappens.interview.models.RequestItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestItemRepository extends JpaRepository<RequestItem, Integer> {
}
