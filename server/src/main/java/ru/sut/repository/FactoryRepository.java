package ru.sut.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.company.Factory;

import java.util.List;

public interface FactoryRepository extends JpaRepository<Factory, Integer>{

    List<Factory> findByCompanyId(int companyId);
}
