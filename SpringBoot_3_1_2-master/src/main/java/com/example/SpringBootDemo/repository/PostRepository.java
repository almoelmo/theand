package com.example.SpringBootDemo.repository;

import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий постов пользователя
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    /**
     * Возвращает все посты для указанного пользователя
     * @param user аутентифицированный пользователь
     * @return список постов, принадлежащих пользователю
     */
    List<Post> findAllByUser(User user);
}
