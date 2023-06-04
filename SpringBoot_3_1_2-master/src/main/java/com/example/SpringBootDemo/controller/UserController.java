package com.example.SpringBootDemo.controller;

import com.example.SpringBootDemo.model.User;
import com.example.SpringBootDemo.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Контроллер пользователей
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private AppService appService;

    /**
     * Создание нового пользователя
     */
    @PostMapping(value = "/new")
    public String create(@ModelAttribute("user") User user){
        appService.createUser(user);
        return "redirect:/index";
    }

    /**
     * Обновление пользователя
     */
    @PostMapping(value = "/edit")
    public String updateUser(@ModelAttribute("user") User user){
        appService.updateUser(user);
        return "redirect:/index";
    }

    /**
     * Удаление пользователя по его идентификатору
     * @param id идентификатор удаляемого пользователя
     */
    @PostMapping(value = "/{id}/delete")
    public String deleteUser(@PathVariable(name = "id") long id){
        appService.deleteUser(id);
        return "redirect:/index";
    }
}
