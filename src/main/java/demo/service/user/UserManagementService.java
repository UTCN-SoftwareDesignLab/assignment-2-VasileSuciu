package demo.service.user;

import demo.model.User;
import demo.model.validation.Notification;

import java.util.List;

public interface UserManagementService {

    Notification<Boolean> updateUser(String username, String password, List<String> roles);

    boolean deleteUser(String user);

    List<User> getAllUsers();

    User getUser(String user);
}
