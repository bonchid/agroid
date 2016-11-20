package ru.sut.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.company.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{

    Company findById(int id);
}
