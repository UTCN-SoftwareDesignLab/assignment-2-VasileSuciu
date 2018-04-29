package demo.service.user;

import demo.database.Constants;
import demo.model.User;
import demo.model.validation.Notification;
import demo.model.validation.UserValidator;
import demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserManagementServiceMySQL implements UserManagementService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Notification<Boolean> updateUser(String username, String password, String roles) {
        User user = userRepository.findByUsername(username);
        boolean passwordChanged = false;
        if (user!=null) {
            if (roles!= null && roles.trim().length()>0) {
                StringBuilder stringBuilder = new StringBuilder();
                Arrays.stream(Constants.Roles.ROLES).filter(s->roles.toLowerCase().contains(s.toLowerCase())).forEach(s->stringBuilder.append(s+" "));
                user.setRoles(stringBuilder.toString());
            }
            passwordChanged = (password != null) && (password.trim().length() > 0);
            if (passwordChanged) {
                user.setPassword(password);
            }
        }
        UserValidator userValidator = new UserValidator(user);
        boolean userValid;
        if (passwordChanged){
            userValid = userValidator.validate();
        }
        else {
            userValid = userValidator.validateExceptPassword();
            user.setPassword(passwordEncoder.encodePassword(password));        }
        Notification<Boolean> userNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
            return userNotification;
        }
        else {
            userNotification.setResult(Boolean.TRUE);
            userRepository.save(user);
        }
        return userNotification;
    }

    @Override
    public User getUser(String user) {
        return userRepository.findByUsername(user);
    }

    @Override
    public boolean deleteUser(String user) {
        User user1 = userRepository.findByUsername(user);
        if (user1 != null) {
            userRepository.delete(userRepository.findByUsername(user));
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(users::add);
        return users;
    }
}
