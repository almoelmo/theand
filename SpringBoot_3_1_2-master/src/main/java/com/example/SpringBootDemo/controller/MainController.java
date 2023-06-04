package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.model.Post;
import com.example.SpringBootDemo.model.Role;
import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер главной страницы и регистрации суперпользователя
 */
@Controller
public class MainController {
    @Autowired
    private AppService appService;

    /**
     * Основная страница приложения
     */
    @GetMapping("/index")
    public ModelAndView mainPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        modelAndView.addObject("authUser", user);
        modelAndView.addObject("users", appService.allUsers());
        modelAndView.addObject("user", new User());
        modelAndView.addObject("post", new Post());
        modelAndView.addObject("adminRole", Role.ROLE_ADMIN);
        modelAndView.addObject("userRole", Role.ROLE_USER);
        modelAndView.addObject("roles",Role.values());
        modelAndView.addObject("posts", appService.findPostsByUser(user));
        modelAndView.addObject("allPosts", appService.allPosts());
        return modelAndView;
    }

    /**
     * Страница регистрации суперпользователя
     */
    @GetMapping("/register")
    public String register(Model model){
        //если в системе уже есть пользователи, переход на страницу ошибки
        if(appService.count() != 0) {
            return "error";
        }
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Создание суперпользователя с правами администратора и обычного пользователя
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user){
        user.setRole(Arrays.stream(Role.values()).collect(Collectors.toSet()));
        appService.createUser(user);
        return "redirect:/login";
    }

    /**
     * Выполняет перенаправление на страницу регистрации, логина или главную страницу приложения
     */
    @GetMapping("/")
    public String home(){
        if(SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")){
            return appService.count() == 0L ? "redirect:/register" : "redirect:/login";
        } else{
            return "redirect:/index";
        }
    }
}
