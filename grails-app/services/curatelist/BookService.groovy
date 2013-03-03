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
                System.out.println("BOOK SERVICE " + book.getTitle())
               /* System.out.println(book.getImageLinks().getSmall())
                System.out.println(book.getImageLinks().getSmallThumbnail())
                System.out.println(book.getImageLinks().getExtraLarge())
                System.out.println(book.getImageLinks().getLarge())
                System.out.println(book.getImageLinks().getMedium())
                System.out.println(book.getImageLinks().getThumbnail())   */
            }
            
            return books
    }
}
