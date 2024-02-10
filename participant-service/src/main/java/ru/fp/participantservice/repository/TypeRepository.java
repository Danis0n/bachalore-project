package ru.fp.participantservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  ru.fp.participantservice.entity.Type;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long>
{
    Type findOneByName(String name);
}
