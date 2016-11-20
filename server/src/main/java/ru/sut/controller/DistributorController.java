package ru.sut.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sut.model.company.Company;
import ru.sut.model.doi.Suffix;
import ru.sut.model.path.ProductPath;
import ru.sut.repository.ProductsPathsRepository;

import java.util.List;


@RestController
@RequestMapping(value = "dst/")
public class DistributorController {

    private final ProductsPathsRepository productsPathsRepository;

    @Autowired
    public DistributorController(ProductsPathsRepository productsPathsRepository) {
        this.productsPathsRepository = productsPathsRepository;
    }

    @RequestMapping(value = "/setDistributor", method = RequestMethod.POST)
    public void setDistributor(@RequestParam String suffixId, @RequestParam String companyId){

        Suffix suffix = new Suffix();
        suffix.setId(Integer.parseInt(suffixId));

        Company company = new Company();
        company.setId(Integer.parseInt(companyId));

        ProductPath lastProductPath = productsPathsRepository
                .findBySuffixIdAndCompanyIdIsNull(Integer.parseInt(suffixId));

        ProductPath nextProductPath = new ProductPath(suffix, company, null);
        productsPathsRepository.save(nextProductPath);

        if (lastProductPath != null){
            lastProductPath.setProductPath(nextProductPath);
            productsPathsRepository.save(lastProductPath);
        }

    }

    @RequestMapping(value = "/getPath", method = RequestMethod.POST)
    public List<ProductPath> getPath(@RequestParam Integer suffixId){
        return productsPathsRepository.findBySuffixId(suffixId);
    }
}
