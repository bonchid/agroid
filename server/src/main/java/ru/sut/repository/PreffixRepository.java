package ru.sut.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.doi.Preffix;

public interface PreffixRepository extends JpaRepository<Preffix, Integer>{

    Preffix findByRegistryAndRegistrant(int registry, int registrant);
    Preffix findByUserId(int userId);
}
