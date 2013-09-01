package curatelist.amazonProductAdvertisingAPI

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.DocumentBuilder
import org.w3c.dom.Document
import org.w3c.dom.Node
import curatelist.books.BooksModel

/**
 * Created by IntelliJ IDEA.
 * User: naren
 * Date: 3/3/13
 * Time: 12:04 PM
 * To change this template use File | Settings | File Templates.
 */
class AmazonBooksDataProvider {
/*
     * Your AWS Access Key ID, as taken from the AWS Your Account page.
     */
    private static final String AWS_ACCESS_KEY_ID = "AKIAJGO4TWZC7OABCBJQ";// "YOUR_ACCESS_KEY_ID_HERE";

    /*
     * Your AWS Secret Key corresponding to the above ID, as taken from the AWS
     * Your Account page.
     */
    private static final String AWS_SECRET_KEY = "M/ywf+yj1E1+6OAzdMqAk7LHYS7rcQLJu/QSPZYn";//"YOUR_SECRET_KEY_HERE";

    /*
     * Use one of the following end-points, according to the region you are
     * interested in:
     *
     *      US: ecs.amazonaws.com
     *      CA: ecs.amazonaws.ca
     *      UK: ecs.amazonaws.co.uk
     *      DE: ecs.amazonaws.de
     *      FR: ecs.amazonaws.fr
     *      JP: ecs.amazonaws.jp
     *
     */
    private static final String ENDPOINT = "ecs.amazonaws.com";

    /*
     * The Item ID to lookup. The value below was selected for the US locale.
     * You can choose a different value if this value does not work in the
     * locale of your choice.
     */
   // private static final String ITEM_ID = "0545010225";
    private static final String AFFILIATE_CODE = "DUMMY";

    private static final String API_VERSION = "2011-08-01"

    public static void main(String[] args){
        for(BooksModel book: getBooksByTitle("Startup Life: Surviving and Thriving in a Relationship with an Entrepreneur","Books"))        {
                System.out.println book
                break
        }
    }

    /*
    * Search by keywords
    *
    * @searchString : keywords( Ex: Lean Startup)
    * @searchCategory :  Books, Apparel, Film etc
    * http://docs.aws.amazon.com/AWSECommerceService/2011-08-01/DG/ItemSearch.html
    *
    *
    * <Items>
        <Request>...</Request>
        <TotalResults>185</TotalResults>
        <TotalPages>19</TotalPages>
        <MoreSearchResultsUrl>...</MoreSearchResultsUrl>
        <Item>...</Item>
        <Item>...</Item>
        <Item>...</Item>
      </Items>
    *
    */
    public static List<BooksModel> getBooksByTitle(String searchString, String searchCategory){
        /*
         * Set up the signed requests helper
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String requestUrl = null;
        String title = null;

        /* The helper can sign requests in two forms - map form and string form */

        /*
        * Here is an example in map form, where the request parameters are stored in a map.
        */
        System.out.println("Map form example:");
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", API_VERSION);
        params.put("Operation", "ItemSearch");
        params.put("Keywords", searchString);
        params.put("SearchIndex",searchCategory)
        params.put("ResponseGroup", "Small,Images"); //To get Title and Images
        //params.put("ResponseGroup", "Small"); //TO GET TITLE
        //params.put("ResponseGroup", "Images");  //TO  GET IMAGES
        params.put("AssociateTag", AFFILIATE_CODE);    //TODO: Change to your Affiliate Tag

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        List<BooksModel> booksModel = parseBookInfoForItemLookup(requestUrl);

        return booksModel;



    }



    /*
     * Look up a book by ISBN/ASIN/UPC code etc
     * http://docs.aws.amazon.com/AWSECommerceService/2011-08-01/DG/ItemLookup.html
     */
    public static List<BooksModel> getBooksByUniqueIdentifier(String uniqueIdentifier){
        /*
         * Set up the signed requests helper
         */
        SignedRequestsHelper helper;
        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String requestUrl = null;
        String title = null;

        /* The helper can sign requests in two forms - map form and string form */

        /*
        * Here is an example in map form, where the request parameters are stored in a map.
        */
        System.out.println("Map form example:");
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", API_VERSION);
        params.put("Operation", "ItemLookup");
        params.put("ItemId", uniqueIdentifier);
        params.put("ResponseGroup", "Small,Images"); //To get Title and Images
        //params.put("ResponseGroup", "Small"); //TO GET TITLE
        //params.put("ResponseGroup", "Images");  //TO  GET IMAGES
        params.put("AssociateTag", AFFILIATE_CODE);    //TODO: Change to your Affiliate Tag

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        List<BooksModel> booksModel = parseBookInfoForItemLookup(requestUrl);

        return booksModel;



    }


            /*

            <Items>
                <Request>...</Request>
                <Item>
                    <ASIN>0545010225</ASIN>
                    <DetailPageURL>...</DetailPageURL>
                    <ItemLinks>...</ItemLinks>
                    <SmallImage>
                    </SmallImage>
                    <MediumImage>...</MediumImage>
                    <LargeImage>...</LargeImage>
                    <ImageSets>
                        <ImageSet Category="primary">
                            <SmallImage>
                                <URL>
                                    http://ecx.images-amazon.com/images/I/41qTZcMasSL._SL75_.jpg
                                </URL>
                                <Height Units="pixels">75</Height>
                                <Width Units="pixels">50</Width>
                            </SmallImage>
                    </ImageSets>
                    <ItemAttributes>
                        <Author>J. K. Rowling</Author>
                        <Creator Role="Illustrator">Mary GrandPré</Creator>
                        <Manufacturer>Arthur A. Levine Books</Manufacturer>
                        <ProductGroup>Book</ProductGroup>
                        <Title>Harry Potter and the Deathly Hallows (Book 7)</Title>
                    </ItemAttributes>
                </Item>
            </Items>
             */

    private static List<BooksModel> parseBookInfoForItemLookup(String requestUrl) {
        ArrayList<BooksModel>  listOfBooksFound = new ArrayList<BooksModel>()
        try {

            def xml = new XmlParser().parse(requestUrl)
            // def xml = new XmlSlurper().parse("/Users/naren/Downloads/dummy.xml")


            xml."Items"."Item".each{



               /* println "**************************"

                println it."ASIN".text()
                println it."ItemAttributes"."Author".text()
                println it."ItemAttributes"."Title".text()
                println it."DetailPageURL".text()
                //println it."SmallImage"."URL".text()
                //println it."MediumImage"."URL".text()
                //println it."LargeImage"."URL".text()
                println it."ImageSets"."ImageSet"."SwatchImage"."URL".text()
                println it."ImageSets"."ImageSet"."SmallImage"."URL".text()
                println it."ImageSets"."ImageSet"."ThumbnailImage"."URL".text()
                println it."ImageSets"."ImageSet"."TinyImage"."URL".text()
                println it."ImageSets"."ImageSet"."MediumImage"."URL".text()
                println it."ImageSets"."ImageSet"."LargeImage"."URL".text()


                println "**************************"             */
                
                
                BooksModel bookModel = new BooksModel()
                bookModel.setTitle(it."ItemAttributes"."Title".text())
                bookModel.setAuthors(it."ItemAttributes"."Author".text())
                bookModel.setLinkToBuyBook(it."DetailPageURL".text())
                bookModel.getImageLinks().setSwatchImage(it."ImageSets"."ImageSet"."SwatchImage"."URL".text())
                bookModel.getImageLinks().setSmall(it."ImageSets"."ImageSet"."SmallImage"."URL".text())
                bookModel.getImageLinks().setThumbnail(it."ImageSets"."ImageSet"."ThumbnailImage"."URL".text())
                bookModel.getImageLinks().setTinyImage( it."ImageSets"."ImageSet"."TinyImage"."URL".text())
                bookModel.getImageLinks().setMedium(it."ImageSets"."ImageSet"."MediumImage"."URL".text())
                bookModel.getImageLinks().setLarge(it."ImageSets"."ImageSet"."LargeImage"."URL".text())

                listOfBooksFound.add(bookModel)
                
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listOfBooksFound;
    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
   /* private static String fetchTitle(String requestUrl) {
        String title = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(requestUrl);
            // Node titleNode = doc.getElementsByTagName("Title").item(0);   //TO GET TITLE
            Node titleNode = doc.getElementsByTagName("SmallImage").item(0); //TO GET IMAGES
            title = titleNode.getTextContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return title;
    }   */

}


