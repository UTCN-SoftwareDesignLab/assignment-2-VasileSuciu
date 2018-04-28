package demo.controllers;

import demo.database.Constants;
import demo.model.User;
import demo.model.UserDTO;
import demo.model.validation.Notification;
import demo.service.user.AuthenticationServiceMySQL;
import demo.service.user.UserManagementServiceMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.prefs.NodeChangeEvent;

@Controller
public class AdministratorController {
    @Autowired
    private AuthenticationServiceMySQL authenticationServiceMySQL;
    @Autowired
    private UserManagementServiceMySQL userManagementServiceMySQL;
    @Autowired
    private LoggedUser loggedUser;

    @RequestMapping(value = "/administrator", method = RequestMethod.GET)
    public String showAdministratorPage(ModelMap model){
        if (loggedUser.isLogged() && loggedUser.isAdministrator()) {
            List<User> userList = userManagementServiceMySQL.getAllUsers();
            model.addAttribute("userList", userList);
            model.addAttribute("userDTO", new UserDTO());
            model.addAttribute("errorMessage2", "");
            return "administrator";
        }
        return "redirect:login";
    }

    @RequestMapping(value = "/administrator", params="logoutBtn", method = RequestMethod.GET)
    public String handleLogOut(ModelMap model){
        loggedUser.logOut();
        return "redirect:login";
    }

    @RequestMapping(value = "/administrator", params="deleteBtn", method = RequestMethod.POST)
    public String handleUserDelete(ModelMap model,  @ModelAttribute("userDTO") UserDTO userDTO) {
        if (!loggedUser.isLogged()){
            return "redirect:login";
        }
        userManagementServiceMySQL.deleteUser(userDTO.getUsername());

        model.addAttribute("userList",userManagementServiceMySQL.getAllUsers());
        return "administrator";
    }

    @RequestMapping(value = "/administrator", params="updateBtn", method = RequestMethod.POST)
    public String handleUserUpdate(ModelMap model,  @ModelAttribute("userDTO") UserDTO userDTO) {
        if (!loggedUser.isLogged()){
            return "redirect:login";
        }
        Notification<Boolean> notification =  userManagementServiceMySQL.updateUser(userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getRoleList());
        if (notification.hasErrors()){
            model.addAttribute("errorMessage2",notification.getFormattedErrors());
        }
        else{
            model.addAttribute("errorMessage2", "");
        }
        model.addAttribute("userList",userManagementServiceMySQL.getAllUsers());
        return "administrator";
    }

    @RequestMapping(value = "/administrator", params="createBtn", method = RequestMethod.POST)
    public String handleUserCreate(ModelMap model, @ModelAttribute("userDTO") UserDTO userDTO){
        if (!loggedUser.isLogged()){
            return "redirect:login";
        }
        Notification<Boolean> notification = authenticationServiceMySQL.register(userDTO.getUsername(),
                userDTO.getPassword());
        if (notification.getResult()){
            model.addAttribute("errorMessage2",notification.getFormattedErrors());
        }
        else{
            model.addAttribute("errorMessage2", "");
        }
        model.addAttribute("userList",userManagementServiceMySQL.getAllUsers());
        return "administrator";
    }

    @RequestMapping(value = "/administrator", params="switchBtn", method = RequestMethod.GET)
    public String handleSwitchView(ModelMap model){
        if (!loggedUser.isLogged()){
            return "redirect:login";
        }
        return "redirect:/employee";
    }
}
