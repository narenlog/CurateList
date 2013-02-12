package curatelist.books

/**
 * Created by IntelliJ IDEA.
 * User: naren
 * Date: 1/26/13
 * Time: 6:40 PM
 * To change this template use File | Settings | File Templates.
 */
class BooksModel {
    
    
    String title
    String subtitle
    String authors //csv
    String description
    ImageLinks imageLinks
}


class ImageLinks{

     java.lang.String extraLarge;
     java.lang.String large;
     java.lang.String medium;
     java.lang.String small;
     java.lang.String smallThumbnail;
     java.lang.String thumbnail;

}