package demo.service.user;

import demo.database.Constants;
import demo.model.Role;
import demo.model.User;
import demo.model.builder.UserBuilder;
import demo.model.validation.Notification;
import demo.model.validation.UserValidator;
import demo.repository.security.RoleRepository;
import demo.repository.user.AuthenticationException;
import demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;


@Service
public class AuthenticationServiceMySQL implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Notification<Boolean> register(String username, String password, String role) {
        Role customerRole = roleRepository.findByRole(role);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        } else {
            user.setPassword(encodePassword(password));
            userRepository.save(user);
            userRegisterNotification.setResult(Boolean.TRUE);
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role customerRole = roleRepository.findByRole(Constants.Roles.EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        } else {
            user.setPassword(encodePassword(password));
            userRepository.save(user);
            userRegisterNotification.setResult(Boolean.TRUE);
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<User> login(String username, String password) throws AuthenticationException {
        Notification<User> notification = new Notification<>();
        if (password!=null && username !=null) {
            User user = userRepository.findByUsernameAndPassword(username, encodePassword(password));
            if (user != null) {
                notification.setResult(user);
            } else {
                notification.addError("Invalid username or password!");
            }
        }
        else {
            notification.addError("Invalid username or password!");
        }
        return notification;
    }

    @Override
    public boolean logout(User user) {
        return true;
    }


    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
