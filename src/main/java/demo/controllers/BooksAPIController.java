package demo.controllers;

import demo.model.Book;
import demo.service.googleBooks.GoogleBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class BooksAPIController {
    @Autowired
    private GoogleBooks googleBooks;
    @Autowired
    private LoggedUser loggedUser;

    @RequestMapping(value = "/bookAPI", method = RequestMethod.GET)
    public String showAdministratorPage(ModelMap model){
        if (loggedUser.isLogged()) {
            model.addAttribute("title", "");
            model.addAttribute("author", "");
            model.addAttribute("subject", "");
            model.addAttribute("bookList", new ArrayList<Book>());
            return "bookAPI";
        }
        return "redirect:login";
    }

    @RequestMapping(value = "/bookAPI", params="searchTitleBtn", method = RequestMethod.GET)
    public String handleTitleSearch(ModelMap model, @ModelAttribute("title") String title){
        if (!loggedUser.isLogged()){
            return  "redirect:login";
        }
        model.addAttribute(googleBooks.getBooksByTitle(title));
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="searchAuthorBtn", method = RequestMethod.GET)
    public String handleAuthorSearch(ModelMap model, @ModelAttribute("author") String author){
        if (!loggedUser.isLogged()){
            return  "redirect:login";
        }
        model.addAttribute(googleBooks.getBooksByAuthor(author));
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="searchSubjectBtn", method = RequestMethod.GET)
    public String handleSubjectSearch(ModelMap model, @ModelAttribute("subject") String subject){
        if (!loggedUser.isLogged()){
            return  "redirect:login";
        }
        model.addAttribute(googleBooks.getBooksBySubject(subject));
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="logoutBtn", method = RequestMethod.GET)
    public String handleLogOut(ModelMap model){
        loggedUser.logOut();
        return "redirect:login";
    }
}
