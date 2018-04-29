package demo.database;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import demo.service.user.AuthenticationServiceMySQL;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class Bootstrap implements InitializingBean{

    @Autowired
    private  AuthenticationServiceMySQL authenticationService;

    @Override
    @Transactional()
    public void afterPropertiesSet() throws Exception {
        bootstrapUsers();
        bootstrapBooks();
        bootstrapSales();
    }

    private void bootstrapUsers(){
        System.out.println("Bootstrapping users");
        authenticationService.register("admin@UT.com","LongPassword!1", Constants.Roles.ADMINISTRATOR);
        authenticationService.register("employee@UT.com","LongPassword!2");
        System.out.println("Done bootstrapping users");
    }

    private void bootstrapBooks(){
        System.out.println("Bootstrapping books");
        System.out.println("Done bootstrapping books");

    }

    private void bootstrapSales(){
        System.out.println("Bootstrapping sales");

        System.out.println("Done bootstrapping sales");

    }

}
