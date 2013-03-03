package curatelist

import curatelist.books.BooksModel
import curatelist.books.BooksDataProvider
import org.apache.commons.dbcp.BasicDataSource

class BookService implements IBookService,Serializable
 {

    private static final long serialVersionUID = 7526471155622776147L;
    private transient BasicDataSource dataSource;     //otherwise throws NotSerializable exception http://stackoverflow.com/a/3583478/73935

    static transactional = true

    def List<BooksModel> findBooksByTitle(String title) {
            List<BooksModel> books = new BooksDataProvider().getBooksByTitle(title)
            for(BooksModel book : books){
                println("BOOK SERVICE ")
                println "**************************"
                println(book.getTitle())
                println(book.getImageLinks().getSmall())
                println(book.getImageLinks().getLarge())
                println(book.getImageLinks().getMedium())
                println(book.getImageLinks().getThumbnail())
                println "**************************"
            }
            
            return books
    }
}
