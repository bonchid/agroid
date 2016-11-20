package ru.sut.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.sut.model.user.User;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByLoginAndPassword(String login, String password);
    User findById(int id);
}
