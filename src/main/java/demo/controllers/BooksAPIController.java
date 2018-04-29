package demo.controllers;

import demo.model.Book;
import demo.service.book.BookServiceMySQL;
import demo.service.googleBooks.GoogleBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BooksAPIController {
    @Autowired
    private GoogleBooks googleBooks;
    @Autowired
    private BookServiceMySQL bookServiceMySQL;

    @RequestMapping(value = "/bookAPI", method = RequestMethod.GET)
    public String showAdministratorPage(ModelMap model){
            model.addAttribute("title", "");
            model.addAttribute("author", "");
            model.addAttribute("subject", "");
            model.addAttribute("bookList", new ArrayList<Book>());
            model.addAttribute("id",0);
            model.addAttribute("errorMessage3","");
            return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="addToDatabaseBtn", method = RequestMethod.POST)
    public String handleDatabaseUpdate(HttpServletRequest request, ModelMap model, @ModelAttribute("id") Integer id){
        HttpSession session = request.getSession(true);
        List<Book> bookList = (List<Book>)session.getAttribute("bookList");
        if (id == null){
            model.addAttribute("errorMessage3","Please select an ID");
        }
        else {
            if (bookList==null){
                model.addAttribute("errorMessage3","You must perform a search first!");
            }
            else {
                if (id>bookList.size() || id<1){
                    System.out.println(id);
                    model.addAttribute("errorMessage3","Invalid id");
                }
                else {
                    Book book = bookList.get(id-1);
                    bookServiceMySQL.addBook(book.getTitle(), book.getAuthor(), book.getGenre(),
                            book.getStock(), book.getPrice());
                    model.addAttribute("bookList0,bookList");
                }
            }
        }
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="searchTitleBtn", method = RequestMethod.GET)
    public String handleTitleSearch(HttpServletRequest request, ModelMap model, @ModelAttribute("title") String title){

        HttpSession session = request.getSession(true);
        List<Book> bookList =googleBooks.getBooksByTitle(title);
        session.setAttribute("bookList",bookList);
        model.addAttribute(bookList);
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="searchAuthorBtn", method = RequestMethod.GET)
    public String handleAuthorSearch(HttpServletRequest request, ModelMap model, @ModelAttribute("author") String author){

        HttpSession session = request.getSession(true);
        List<Book> bookList = googleBooks.getBooksByAuthor(author);
        model.addAttribute(bookList);
        session.setAttribute("bookList", bookList);
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="searchSubjectBtn", method = RequestMethod.GET)
    public String handleSubjectSearch(HttpServletRequest request, ModelMap model, @ModelAttribute("subject") String subject){


        HttpSession session = request.getSession(true);
        List<Book> bookList= googleBooks.getBooksBySubject(subject);
        session.setAttribute("bookList",bookList);
        model.addAttribute(bookList);
        return "bookAPI";
    }

    @RequestMapping(value = "/bookAPI", params="backBtn", method = RequestMethod.GET)
    public String handleBackBtn(ModelMap model){
        return "redirect:/employee";
    }

}
