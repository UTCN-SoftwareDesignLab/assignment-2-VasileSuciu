package demo.service.user;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {

    private BCryptPasswordEncoder brBCryptPasswordEncoder;

    public  PasswordEncoder(){
        this.brBCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String password){
        return brBCryptPasswordEncoder.encode(password);
    }
}
