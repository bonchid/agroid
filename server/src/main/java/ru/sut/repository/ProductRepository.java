package ru.sut.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.company.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>{

    List<Product> findByCompanyId(int companyId);
}
