package ru.fp.participantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  ru.fp.participantservice.entity.Address;

import java.util.Optional;

@Repository
public interface AddresRepository extends JpaRepository<Address, Long> { }
