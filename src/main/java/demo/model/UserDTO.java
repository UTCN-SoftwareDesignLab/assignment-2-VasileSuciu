package demo.model;

import demo.database.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;
    private String password;
    private String roles;

    public UserDTO(){

    }

    public UserDTO(User user){
        this.username = user.getUsername();
        this.password  = user.getPassword();
        this.roles = processRoles(user.getRoles());

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        if (roles!=null) {
            this.roles = processRoles(roles);
        }
    }

    public List<String> getRoleList(){
        List<String> list = new ArrayList<String>();
        if (this.roles!=null){
            Arrays.stream(Constants.Roles.ROLES).filter(s->this.roles.toLowerCase().contains(s.toLowerCase())).forEach(list::add);
        }
        return list;
    }

    private String processRoles(String roles){
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(Constants.Roles.ROLES).filter(s->roles.toLowerCase().contains(s.toLowerCase())).forEach(s-> stringBuilder.append(s+" "));
        return stringBuilder.toString();
    }

    private String processRoles(List<Role> roles){
        StringBuilder stringBuilder = new StringBuilder();
        roles.forEach(s->stringBuilder.append(s.getRole()+" "));
        return stringBuilder.toString();
    }



}
