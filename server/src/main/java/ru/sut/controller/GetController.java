package ru.sut.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sut.model.company.Factory;
import ru.sut.model.company.Product;
import ru.sut.model.doi.Preffix;
import ru.sut.model.doi.Suffix;
import ru.sut.model.user.User;
import ru.sut.repository.*;

import java.util.List;


@RestController
@RequestMapping(value = "get/")
public class GetController {

    private final PreffixRepository preffixRepository;
    private final SuffixRepository suffixRepository;
    private final FactoryRepository factoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public GetController(PreffixRepository preffixRepository,
                         SuffixRepository suffixRepository, FactoryRepository factoryRepository,
                         ProductRepository productRepository, UserRepository userRepository) {
        this.preffixRepository = preffixRepository;
        this.suffixRepository = suffixRepository;
        this.factoryRepository = factoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/productInfo", method = RequestMethod.POST)
    public Suffix getProductInfo(
            @RequestParam String registry, @RequestParam String registrant, @RequestParam String suffix){

        Preffix preffix = preffixRepository.findByRegistryAndRegistrant(
                Integer.parseInt(registry), Integer.parseInt(registrant));

        return suffixRepository.findByPreffixIdAndSuffix(preffix.getId(), suffix);
    }

    @RequestMapping(value = "/factoryList", method = RequestMethod.POST)
    public List<Factory> getFactoryList(@RequestParam String companyId){
        return factoryRepository.findByCompanyId(Integer.parseInt(companyId));
    }

    @RequestMapping(value = "/productList", method = RequestMethod.POST)
    public List<Product> getProductList(@RequestParam String companyId){
        return productRepository.findByCompanyId(Integer.parseInt(companyId));
    }

    @RequestMapping(value = "/preffixInfo", method = RequestMethod.POST)
    public Preffix getPreffixInfo(@RequestParam String userId){
        return preffixRepository.findByUserId(Integer.parseInt(userId));
    }

    @RequestMapping(value = "/preffixList", method = RequestMethod.POST)
    public List<Preffix> getPreffixList(){
        return preffixRepository.findAll();
    }

    @RequestMapping(value = "/userList", method = RequestMethod.POST)
    public List<User> getUsers(){
        return userRepository.findAll();
    }


}
