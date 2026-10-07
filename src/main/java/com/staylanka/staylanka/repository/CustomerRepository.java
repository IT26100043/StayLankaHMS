package com.staylanka.staylanka.repository;

import com.staylanka.staylanka.entity.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerUser,Long> {
}
