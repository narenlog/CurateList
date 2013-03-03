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
    String linkToBuyBook

    public BooksModel(){
        this.imageLinks = new ImageLinks()
    }
}


class ImageLinks{

     java.lang.String extraLarge;//Google only
     java.lang.String large;
     java.lang.String medium;
     java.lang.String small;
     java.lang.String smallThumbnail;//Google only
     java.lang.String thumbnail;
     java.lang.String tinyImage;//Amazon only
     java.lang.String swatchImage;//Amazon only (very small image patch)

}