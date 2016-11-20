package ru.sut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.doi.Suffix;

public interface SuffixRepository extends JpaRepository<Suffix, Integer>{

    Suffix findByPreffixIdAndSuffix(int preffixId, String suffix);
}
