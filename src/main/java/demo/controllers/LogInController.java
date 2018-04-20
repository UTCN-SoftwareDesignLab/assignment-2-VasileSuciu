package demo.controllers;


import demo.database.Constants;
import demo.model.User;
import demo.model.validation.Notification;
import demo.service.user.AuthenticationServiceMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class LogInController {

    @Autowired
    private AuthenticationServiceMySQL authenticationServiceMySQL;
    @Autowired
    private LoggedUser loggedUser;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("errorMessage", "");
        return "login";
    }

    @RequestMapping(value = "/login", params="loginBtn", method = RequestMethod.POST)
    public String handleUserLogin(ModelMap model, @ModelAttribute("user") User user) {
        Notification<User> notification = null;
        try {
            notification= authenticationServiceMySQL.login(user.getUsername(), user.getPassword());
            if (notification.hasErrors()){
                model.put("errorMessage", notification.getFormattedErrors());
                return "login";
            }
            loggedUser.setUser(notification.getResult());

        }
        catch(Exception e){
            e.printStackTrace();
        }
        if (notification !=null && loggedUser.isAdministrator()){
            return "redirect:/administrator";
        }
        return "redirect:/employee";
    }

    @RequestMapping(value = "/login", params="registerBtn", method = RequestMethod.POST)
    public String handleUserRegistration(ModelMap model, @ModelAttribute("user") User user) {
        try {
            Notification<Boolean> notification = authenticationServiceMySQL.register(user.getUsername(), user.getPassword());
            if (notification.hasErrors()){
                model.addAttribute("errorMessage",notification.getFormattedErrors());
            }
            else {
                model.addAttribute("errorMessage","Registration successful!");
            }
            return "login";

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "login";
    }


}
