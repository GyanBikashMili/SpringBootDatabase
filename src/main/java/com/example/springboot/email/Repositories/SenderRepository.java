package com.example.springboot.email.Repositories;

import com.example.springboot.email.Models.SenderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<SenderDetails, Long> {

}
