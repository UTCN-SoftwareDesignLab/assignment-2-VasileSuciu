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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class LogInController {

    @Autowired
    private AuthenticationServiceMySQL authenticationServiceMySQL;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginPage(HttpServletRequest request, ModelMap model) {
        model.addAttribute("user", new User());
        HttpSession session = request.getSession(true);
        String error = "";
        if (session.getAttribute("errorMessage")!=null){
            error = (String)session.getAttribute("errorMessage");
        }
        model.addAttribute("errorMessage", error);
        return "login";
    }


}
