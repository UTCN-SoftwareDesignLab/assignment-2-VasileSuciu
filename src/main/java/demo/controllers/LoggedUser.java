package demo.controllers;

import demo.database.Constants;
import demo.model.User;
import org.springframework.stereotype.Service;

@Service
public class LoggedUser {
    private User user;

    public LoggedUser(){
        user = null;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void logOut(){
        this.user = null;
    }

    public boolean isLogged(){
        return user!=null;
    }

    public boolean isAdministrator(){
        if (user !=null){
            return user.getRoles().stream().anyMatch(s->s.getRole().equalsIgnoreCase(Constants.Roles.ADMINISTRATOR));
        }
        return false;
    }
}
