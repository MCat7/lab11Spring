package by.iba.springproject.controller;

import by.iba.springproject.form.UserForm;
import by.iba.springproject.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private static List<User> users = new ArrayList<User>();

    static {
        users.add(new User("Olga", "Pertova"));
        users.add(new User("Ivan", "Ivanov"));
    }
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        return modelAndView;
    }
    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        model.addAttribute("users", users);
        return modelAndView;
    }
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("add");
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return modelAndView;
    }

    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, @ModelAttribute("userForm") UserForm userForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        String firstName = userForm.getFirstName();
        String lastName = userForm.getLastName();
        if (firstName != null && firstName.length() > 0 && lastName != null && lastName.length() > 0) {
            User newUser = new User(firstName, lastName);
            users.add(newUser);
            model.addAttribute("users", users);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("add");
        return modelAndView;
    }

}
