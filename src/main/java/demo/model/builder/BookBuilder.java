package demo.model.builder;

import demo.model.Book;

public class BookBuilder {
    private Book book;

    public BookBuilder() {
        this.book = new Book();
    }

    public BookBuilder setId(Long id){
        book.setId(id);
        return this;
    }

    public BookBuilder setTitle(String title){
        book.setTitle(title);
        return this;
    }

    public BookBuilder setAuthor(String author){
        book.setAuthor(author);
        return this;
    }

    public BookBuilder setGenre(String genre){
        book.setGenre(genre);
        return this;
    }

    public BookBuilder setStock(int stock){
        book.setStock(stock);
        return this;
    }

    public Book build(){
        return book;
    }
}
