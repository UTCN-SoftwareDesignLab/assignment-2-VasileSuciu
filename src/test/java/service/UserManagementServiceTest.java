package service;

import demo.database.Constants;
import demo.model.User;
import demo.model.builder.UserBuilder;
import demo.repository.user.UserRepository;
import demo.service.user.UserManagementServiceMySQL;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserManagementServiceMySQL userManagementServiceMySQL;

    User user;

    @Before
    public void init() {
        this.user = new UserBuilder()
                .setId(1L)
                .setUsername("user@mail.com")
                .setPassword("LongPassword!1")
                .setRoles(Constants.Roles.ADMINISTRATOR+ " "+ Constants.Roles.EMPLOYEE)
                .build();
    }

    @Test
    public void updateUserTest(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Assert.assertFalse(userManagementServiceMySQL.updateUser(user.getUsername(),user.getPassword(),
                Arrays.asList(Constants.Roles.ROLES)).hasErrors());
    }

    @Test
    public void getUserTest(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Assert.assertEquals(user.getUsername(), userManagementServiceMySQL.getUser(user.getUsername()).getUsername());
    }

    @Test
    public void deleteUserTest(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Assert.assertTrue(userManagementServiceMySQL.deleteUser(user.getUsername()));
    }

    @Test
    public void getAllUsersTest(){
        when(userRepository.findAll()).thenReturn(new ArrayList<User>());
        Assert.assertEquals(userManagementServiceMySQL.getAllUsers().size(),0);
    }
}
