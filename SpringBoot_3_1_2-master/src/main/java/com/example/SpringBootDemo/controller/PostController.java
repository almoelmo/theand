package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер постов
 */
@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private AppService service;

    /**
     * Контроллер создания нового поста
     */
    @PostMapping(value = "/new")
    public String createPost(@ModelAttribute("post") Post post){
        post.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        service.createPost(post);
        return "redirect:/index";
    }

    /**
     * Контроллер обновления поста
     */
    @PostMapping(value = "/edit")
    public String updatePost(@ModelAttribute("post") Post post){
        post.setUser((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        service.updatePost(post);
        return "redirect:/index";
    }

    /**
     * Контроллер удаления поста
     */
    @PostMapping(value = "/{id}/delete")
    public String deletePost(@PathVariable(name = "id") long id){
        service.deletePost(id);
        return "redirect:/index";
    }
}
