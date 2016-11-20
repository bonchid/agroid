package ru.sut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.sut.model.company.Company;
import ru.sut.model.company.Factory;
import ru.sut.model.company.Product;
import ru.sut.model.doi.Preffix;
import ru.sut.model.doi.Suffix;
import ru.sut.model.user.User;
import ru.sut.repository.*;

@RestController
@RequestMapping(value = "set/")
public class SetController {

    private final PreffixRepository preffixRepository;
    private final SuffixRepository suffixRepository;
    private final FactoryRepository factoryRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Autowired
    public SetController(PreffixRepository preffixRepository,
                         SuffixRepository suffixRepository, FactoryRepository factoryRepository,
                         ProductRepository productRepository, CompanyRepository companyRepository,
                         UserRepository userRepository) {
        this.preffixRepository = preffixRepository;
        this.suffixRepository = suffixRepository;
        this.factoryRepository = factoryRepository;
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "newUser", method = RequestMethod.POST)
    public void setUser(@RequestBody User user){

        userRepository.save(user);
    }

    @RequestMapping(value = "newFactory", method = RequestMethod.POST)
    public void setFactory(@RequestParam String address, @RequestParam String companyId){

        Company company = new Company();
        company.setId(Integer.parseInt(companyId));

        Factory factory = new Factory();
        factory.setAddress(address);
        factory.setCompany(company);

        factoryRepository.saveAndFlush(factory);
    }

    @RequestMapping(value = "newProduct", method = RequestMethod.POST)
    public void setProduct(@RequestParam String companyId, @RequestParam String productName,
                           @RequestParam String shelfTime, @RequestParam String info){

        Company company = new Company();
        company.setId(Integer.parseInt(companyId));

        Product product = new Product(productName, info, Integer.parseInt(shelfTime), company);

        productRepository.saveAndFlush(product);
    }

    @RequestMapping(value = "newCompany", method = RequestMethod.POST)
    public void setCompany(@RequestBody Company company){
        companyRepository.saveAndFlush(company);
    }

    @RequestMapping(value = "newPreffix", method = RequestMethod.POST)
    public void setPreffix(@RequestParam String registry, @RequestParam String registrant,
                           @RequestParam String name, @RequestParam String address,
                           @RequestParam String userId){

        User user = new User();
        user.setId(Integer.parseInt(userId));

        Company company = new Company(name, address);
        companyRepository.save(company);

        Preffix preffix = new Preffix(Integer.parseInt(registry), Integer.parseInt(registrant), company, user);
        preffixRepository.saveAndFlush(preffix);
    }

    @RequestMapping(value = "newSuffix", method = RequestMethod.POST)
    public void setSuffix(@RequestParam String suffix, @RequestParam String preffixId,
                          @RequestParam String productId, @RequestParam String manufactureDate,
                          @RequestParam String factoryId){
        Preffix preffix = new Preffix();
        preffix.setId(Integer.parseInt(preffixId));

        Product product = new Product();
        product.setId(Integer.parseInt(productId));

        Factory factory = new Factory();
        factory.setId(Integer.parseInt(factoryId));

        Suffix s = new Suffix(suffix, preffix, product, manufactureDate, factory);

        suffixRepository.saveAndFlush(s);
    }
}
