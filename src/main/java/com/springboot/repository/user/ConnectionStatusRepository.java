package com.springboot.repository.user;

import com.springboot.entity.user.ConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionStatusRepository extends JpaRepository<ConnectionStatus, Long> {
}