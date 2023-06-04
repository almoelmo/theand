package com.example.SpringBootDemo.repository;

import com.example.SpringBootDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий пользователей
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользователя по адресу электронной почты, указанной как параметр метода
     * @param email адрес электронной почты
     * @return найденного пользователя с указанным адресом электронной почты
     */
    User findByEmail(String email);
}
