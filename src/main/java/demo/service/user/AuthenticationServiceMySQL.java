package demo.service.user;

import demo.database.Constants;
import demo.model.User;
import demo.model.builder.UserBuilder;
import demo.model.validation.Notification;
import demo.model.validation.UserValidator;
import demo.repository.user.AuthenticationException;
import demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceMySQL implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Notification<Boolean> register(String username, String password, String role) {
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(role)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        } else {
            user.setPassword(passwordEncoder.encodePassword(password));
            userRepository.save(user);
            userRegisterNotification.setResult(Boolean.TRUE);
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Constants.Roles.EMPLOYEE)
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
            return userRegisterNotification;
        } else {
            user.setPassword(passwordEncoder.encodePassword(password));
            userRepository.save(user);
            userRegisterNotification.setResult(Boolean.TRUE);
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<User> login(String username, String password) throws AuthenticationException {
        Notification<User> notification = new Notification<>();
        if (password!=null && username !=null) {
            User user = userRepository.findByUsernameAndPassword(username,passwordEncoder.encodePassword(password));
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



}
