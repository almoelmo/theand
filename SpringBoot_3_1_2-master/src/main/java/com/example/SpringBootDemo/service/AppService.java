package com.example.SpringBootDemo.service;

import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.repository.PostRepository;
import com.example.SpringBootDemo.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервисный слой приложения
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppService implements UserDetailsService {
    @Autowired UserRepository userRepository;

    @Autowired PostRepository postRepository;

    @Autowired PasswordEncoder encoder;

    /**
     * Сохранение созданного пользователя
     * @param user добавленный пользователь
     */
    @Transactional
    public void createUser(User user) {
        //преобразование пароля в хэш
        user.setPassword(encoder.encode(user.getPassword()));
        user.setConfirmPassword(user.getPassword());
        userRepository.save(user);
    }

    /**
     * Возвращает все посты для указанного пользователя
     * @param user авторизованный пользователь
     * @return список постов пользователя
     */
    public List<Post> findPostsByUser(User user){
        return postRepository.findAllByUser(user);
    }

    /**
     * @return список всех созданных пользователей приложения
     */
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /**
     * Удаление пользователя по указанному идентификатору
     * @param id идентификатор пользователя
     */
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Поиск пользователя по адресу электронной почты
     * @param email адрес электронной почты
     * @return авторизуемый пользователь
     * @throws UsernameNotFoundException - пользователь с указанным адресом почты не найден в базе данных
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    /**
     * Удаление поста по указанному идентификатору
     * @param id идентификатор поста
     */
    public void deletePost(long id){
        postRepository.deleteById(id);
    }

    /**
     * @return список всех созданных постов, сортированный по дате создания
     */
    public List<Post> allPosts(){
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
    }

    /**
     * Создание нового поста
     * @param post Заполненный пользователем пост
     */
    @Transactional
    public void createPost(Post post) {
        post.setCreatedOn(LocalDateTime.now());
        postRepository.save(post);
    }

    /**
     * Обновление поста
     * @param post Обновленный пост
     */
    public void updatePost(Post post) {
        Post p = postRepository.findById(post.getId()).get();
        p.setTitle(post.getTitle());
        p.setContent(post.getContent());
        postRepository.save(p);
    }

    /**
     * Обновление указанного пользователя
     * @param user
     */
    public void updateUser(User user) {
        User u = userRepository.findById(user.getId()).get();
        if(!user.getPassword().isEmpty()){
            u.setName(user.getName());
        }
        if(!user.getPassword().isEmpty()){
            u.setPassword(encoder.encode(user.getPassword()));
            u.setConfirmPassword(u.getPassword());
        }
        if(user.getAge() != 0){
            u.setAge(user.getAge());
        }
        if(!user.getPassword().isEmpty()){
            u.setEmail(user.getEmail());
        }

        if(!user.getRole().isEmpty()){
            u.setRole(user.getRole());
        }
        userRepository.save(u);
    }

    /**
     * @return количество созданных пользователей в таблице базы данных
     */
    public long count() {
        return userRepository.count();
    }
}
