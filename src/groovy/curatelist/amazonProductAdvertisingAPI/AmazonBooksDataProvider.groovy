package curatelist.amazonProductAdvertisingAPI

import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.DocumentBuilder
import org.w3c.dom.Document
import org.w3c.dom.Node

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
    private static final String ITEM_ID = "0545010225";



    public static void getBooksByTitle(String searchString){
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
        params.put("Version", "2009-03-31");
        params.put("Operation", "ItemLookup");
        params.put("ItemId", ITEM_ID);
        params.put("ResponseGroup", "Small,Images"); //To get Title and Images
        //params.put("ResponseGroup", "Small"); //TO GET TITLE
        //params.put("ResponseGroup", "Images");  //TO  GET IMAGES
        //params.put("AssociateTag", "progressprobl-22");    //TODO: Change to your Affiliate Tag
        params.put("AssociateTag", "DUMMY");    //TODO: Change to your Affiliate Tag

        requestUrl = helper.sign(params);
        System.out.println("Signed Request is \"" + requestUrl + "\"");

        title = fetchTitle(requestUrl);
        System.out.println("Signed Title is \"" + title + "\"");
        System.out.println();

        /* Here is an example with string form, where the requests parameters have already been concatenated
         * into a query string. */
        System.out.println("String form example:");
        String queryString = "Service=AWSECommerceService&Version=2009-03-31&Operation=ItemLookup&" +
                //"ResponseGroup=Small" +
                "ResponseGroup=Images" +
                "&ItemId="
        + ITEM_ID
        // +"&AssociateTag=progressprobl-22";  //TODO: Change to your Affiliate Tag
        +"&AssociateTag=DUMMY";  //TODO: Change to your Affiliate Tag
        requestUrl = helper.sign(queryString);
        System.out.println("Request is \"" + requestUrl + "\"");

        title = fetchTitle(requestUrl);
        System.out.println("Title is \"" + title + "\"");
        System.out.println();

    }

    /*
     * Utility function to fetch the response from the service and extract the
     * title from the XML.
     */
    private static String fetchTitle(String requestUrl) {
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
    }

}

