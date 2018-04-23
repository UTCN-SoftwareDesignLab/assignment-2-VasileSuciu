package demo.controllers;

import demo.database.Constants;
import demo.model.Book;
import demo.model.validation.Notification;
import demo.service.book.BookServiceMySQL;
import demo.service.report.ReportGeneratorFactory;
import demo.service.report.ReportGeneratorService;
import demo.service.sale.SaleServiceMySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class EmployeeController {
    @Autowired
    private BookServiceMySQL bookService;
    @Autowired
    private SaleServiceMySQL saleServiceMySQL;
    @Autowired
    private LoggedUser loggedUser;
    @Autowired
    private ReportGeneratorFactory reportGeneratorFactory;
    @Autowired
    private ReportGeneratorService reportGeneratorService;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String showAdministratorPage(ModelMap model){
        if (loggedUser.isLogged()) {
            model.addAttribute("errorMessage3", "");
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "employee";
        }
        return "redirect:login";
    }

    @RequestMapping(value = "/employee", params="logoutBtn", method = RequestMethod.GET)
    public String handleLogOut(ModelMap model){
        loggedUser.logOut();
        return "redirect:login";
    }

    @RequestMapping(value = "/employee", params="deleteBtn", method = RequestMethod.POST)
    public String handleBookDelete(ModelMap model,  @ModelAttribute("book") Book book) {
        bookService.deleteBook(book.getId());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "employee";
    }

    @RequestMapping(value = "/employee", params="updateBtn", method = RequestMethod.POST)
    public String handleBookUpdate(ModelMap model,  @ModelAttribute("book") Book book) {
        Notification<Boolean> notification = bookService.updateBook(book.getId(), book.getTitle(),
            book.getAuthor(), book.getGenre(), book.getStock(), book.getPrice());
        if (notification.hasErrors()) {
            model.addAttribute("errorMessage3", notification.getFormattedErrors());
        } else {
             model.addAttribute("errorMessage3", "");
        }
        model.addAttribute("bookList", bookService.getAllBooks());
        return "employee";
    }

    @RequestMapping(value = "/employee", params="createBtn", method = RequestMethod.POST)
    public String handleBookCreate(ModelMap model,  @ModelAttribute("book") Book book) {
        Notification<Boolean> notification =  bookService.addBook(book.getTitle(),
                book.getAuthor(), book.getGenre(), book.getStock(), book.getPrice());
        if (notification.hasErrors()){
            model.addAttribute("errorMessage3",notification.getFormattedErrors());
        }
        else{
            model.addAttribute("errorMessage3", "");
        }
        model.addAttribute("bookList", bookService.getAllBooks());
        return "employee";
    }

    @RequestMapping(value = "/employee", params="sellBtn", method = RequestMethod.POST)
    public String handleBookSale(ModelMap model,  @ModelAttribute("book") Book book) {
        Notification<Boolean> notification =  saleServiceMySQL.makeSale(book.getId(),book.getStock());
        if (notification.hasErrors()){
            model.addAttribute("errorMessage3",notification.getFormattedErrors());
        }
        else{
            model.addAttribute("errorMessage3", "");
        }
        model.addAttribute("bookList", bookService.getAllBooks());
        return "employee";
    }

    @RequestMapping(value = "/employee", params="searchBtn", method = RequestMethod.POST)
    public String handleBookSearch(ModelMap model,  @ModelAttribute("book") Book book) {
        List<Book> books = bookService.searchForBooks(book.getTitle(),
                book.getAuthor(), book.getGenre());
        model.addAttribute("bookList", books);
        return "employee";
    }

    @RequestMapping(value = "/employee", params="switchBtn", method = RequestMethod.GET)
    public String handleSwitchView(ModelMap model) {
        if (loggedUser.isAdministrator()){
            return "redirect:administrator";
        }
        return "redirect:employee";
    }

    @RequestMapping(value = "/employee", params="pdfBtn", method = RequestMethod.POST)
    public String handlePDFReportGeneration(ModelMap model) {
        System.out.println("Entering PDF EmployeeController");
        reportGeneratorService.setReportGenerator(reportGeneratorFactory
                .getReportGenerator(Constants.Reports.PDF));
        System.out.println("Done PDF EmployeeController");
        reportGeneratorService.generateReport();
        return "redirect:employee";
    }

    @RequestMapping(value = "/employee", params="csvBtn", method = RequestMethod.POST)
    public String handleCSVReportGeneration(ModelMap model) {
        System.out.println("Entering CSV EmployeeController");
        reportGeneratorService.setReportGenerator(reportGeneratorFactory
                .getReportGenerator(Constants.Reports.CSV));
        reportGeneratorService.generateReport();
        System.out.println("Done CSV EmployeeController");
        return "redirect:employee";
    }

    @RequestMapping(value = "/employee", params="googleSearchBtn", method = RequestMethod.GET)
    public String handleGoogleSearch(ModelMap model){
        if (!loggedUser.isLogged()){
            return "redirect:login";
        }
        return "redirect:bookAPI";
    }



}
