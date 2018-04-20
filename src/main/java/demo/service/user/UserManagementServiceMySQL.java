package demo.service.user;

import demo.model.User;
import demo.model.validation.Notification;
import demo.model.validation.UserValidator;
import demo.repository.security.RoleRepository;
import demo.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserManagementServiceMySQL implements UserManagementService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository rightsRolesRepository;


    @Override
    public Notification<Boolean> updateUser(String username, String password, List<String> roles) {
        User user = userRepository.findByUsername(username);
        boolean passwordChanged = false;
        if (user!=null) {
            user.setRoles(roles.stream().map(rightsRolesRepository::findByRole).collect(Collectors.toList()));
            passwordChanged = (password != null) && (password.trim().length() > 0) && (!user.getPassword().equals(password));
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
        }
        Notification<Boolean> userNotification = new Notification<>();

        if (!userValid){
            userValidator.getErrors().forEach(userNotification::addError);
            userNotification.setResult(Boolean.FALSE);
            return userNotification;
        }
        else {
            userNotification.setResult(Boolean.TRUE);
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
