package demo.service.user;

import demo.model.User;
import demo.model.validation.Notification;
import demo.repository.user.AuthenticationException;

/**
 * Created by Alex on 11/03/2017.
 */
public interface AuthenticationService {

    Notification<Boolean> register(String username, String password);

    Notification<Boolean> register(String username, String password, String role);

    Notification<User> login(String username, String password) throws AuthenticationException;

    boolean logout(User user);

}
