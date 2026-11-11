package com.staylanka.staylanka.repository;

import com.staylanka.staylanka.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser,Long> {
    List<AdminUser> findAdminUserByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUserNameAndIdNot(String userName, Long id);
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);

}
