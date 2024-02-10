package ru.fp.billingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fp.billingservice.entity.decriptor.Descriptor;

@Repository
public interface DescriptorRepository extends JpaRepository<Descriptor, Long> {
    Descriptor findByName(String name);
}
