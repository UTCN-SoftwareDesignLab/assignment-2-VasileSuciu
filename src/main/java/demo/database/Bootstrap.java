package demo.database;

import demo.model.Right;
import demo.model.Role;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import demo.repository.book.BookRepository;
import demo.repository.sale.SaleRepository;
import demo.repository.security.RightsRepository;
import demo.repository.security.RoleRepository;
import demo.service.user.AuthenticationServiceMySQL;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class Bootstrap implements InitializingBean{
    @Autowired
    private  RightsRepository rightsRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  AuthenticationServiceMySQL authenticationService;
    @Autowired
    private  BookRepository bookRepository;
    @Autowired
    private  SaleRepository saleRepository;

    @Override
    @Transactional()
    public void afterPropertiesSet() throws Exception {
        //bootstrapRights();
        //bootstrapRoles();
        //bootstrapUsers();
        //bootstrapBooks();
        //bootstrapSales();
    }

    private  void bootstrapRights(){
        System.out.println("Bootstrapping rights");
        for (String right: Constants.Rights.RIGHTS){
            this.rightsRepository.save(new Right(right));
        }
        System.out.println("Done bootstrapping rights");
    }

    private void bootstrapRoles(){
        System.out.println("Bootstrapping roles");
        for (String role: Constants.Roles.ROLES){
            List<String> rightsString = Constants.getRolesRights().get(role);
            List<Right> rights = rightsString.stream().map(rightsRepository::findByRight).collect(Collectors.toList());
            roleRepository.save(new Role(role,rights));
        }
        System.out.println("Done bootstrapping roles");
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
