package service;

import demo.model.Right;
import demo.model.Role;
import demo.model.User;
import demo.model.builder.BookBuilder;
import demo.model.builder.UserBuilder;
import demo.repository.security.RoleRepository;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserManagementServiceTest {
    @Mock
    RoleRepository roleRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserManagementServiceMySQL userManagementServiceMySQL;

    User user;

    @Before
    public void init() {
        List<Right> rightList = new ArrayList<>();
        Role role = new Role("administrator",rightList);
        Role role2 = new Role("employee",rightList);
        List<Role> rolesList = new ArrayList<Role>();
        rolesList.add(role);
        rolesList.add(role2);
        this.user = new UserBuilder()
                .setId(1L)
                .setUsername("user@mail.com")
                .setPassword("LongPassword!1")
                .setRoles(rolesList)
                .build();
    }

    @Test
    public void updateUserTest(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        when(roleRepository.findByRole(user.getRoles().get(0).getRole())).thenReturn(user.getRoles().get(0));
        when(roleRepository.findByRole(user.getRoles().get(1).getRole())).thenReturn(user.getRoles().get(1));
        Assert.assertFalse(userManagementServiceMySQL.updateUser(user.getUsername(),user.getPassword(),
                user.getRoles().stream().map(s->s.getRole()).collect(Collectors.toList())).hasErrors());
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
