package ru.sut.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.path.ProductPath;

import java.util.List;

public interface ProductsPathsRepository extends JpaRepository<ProductPath, Integer>{

    List<ProductPath> findBySuffixId(int suffixId);
    ProductPath findBySuffixAndCompanyId(String suffix, int companyId);
    ProductPath findBySuffixIdAndCompanyIdIsNull(int suffixId);
}
