package service;

import demo.repository.user.AuthenticationException;
import demo.repository.user.UserRepository;
import demo.service.user.AuthenticationServiceMySQL;
import demo.service.user.PasswordEncoder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AutheticationServiceTest {
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AuthenticationServiceMySQL authenticationServiceMySQL;

    @Test
    public void registerTest(){
        when(passwordEncoder.encodePassword("LongPassword!1")).thenReturn(new PasswordEncoder().encodePassword("LongPassword!1"));
        Assert.assertFalse(authenticationServiceMySQL.register("user@mail.com",
                "LongPassword!1").hasErrors());
    }

    @Test
    public void loginTest(){
        try {
            Assert.assertTrue(authenticationServiceMySQL.login(null, null).hasErrors());
        }
        catch (AuthenticationException e){
            e.printStackTrace();
        }
    }

    @Test
    public void logoutTest(){
        Assert.assertTrue(authenticationServiceMySQL.logout(null));
    }
}
