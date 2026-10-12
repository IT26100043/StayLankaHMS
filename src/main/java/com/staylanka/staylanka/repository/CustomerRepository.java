package com.staylanka.staylanka.repository;

import com.staylanka.staylanka.entity.CustomerUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerUser,Long> {
    List<CustomerUser> findCustomerUserByUserName(String userName);
}
