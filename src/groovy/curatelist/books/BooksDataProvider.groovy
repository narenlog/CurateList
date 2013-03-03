package curatelist.books

import curatelist.googlebooksapi.GoogleBooksDataProvider
import curatelist.amazonProductAdvertisingAPI.AmazonBooksDataProvider

/**
 * Created by IntelliJ IDEA.
 * User: naren
 * Date: 1/26/13
 * Time: 6:56 PM
 * To change this template use File | Settings | File Templates.
 */
class BooksDataProvider {

   def   List<BooksModel> getBooksByTitle(String searchString){

       PROVIDER defaultProvider = getDefaultProvider()
       switch(defaultProvider){

           case PROVIDER.AMAZON:
               AmazonBooksDataProvider.getBooksByTitle(searchString)
               break;
               
           case PROVIDER.GOOGLE:
               GoogleBooksDataProvider.queryGoogleBooks(searchString)
               break;

           default:
               log.error("Unknown books data provider")
               break;
       }

   }

   def PROVIDER getDefaultProvider(){
       PROVIDER.AMAZON
       //PROVIDER.GOOGLE
   }

}

private enum PROVIDER{
    GOOGLE,  //Google Books API
    AMAZON, //Product Advertising API
    INGRAM, //used by GoodReads
    SKIMLINKS, //Affiliate aggregator
    VIGLINKS   //Google Ventures' backed affiliate aggregator

}
