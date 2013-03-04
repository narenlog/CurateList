package curatelist.home

import org.apache.wicket.PageParameters;


import curatelist.common.BasePage
import org.apache.wicket.markup.html.form.Form
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.wicket.util.value.ValueMap
import curatelist.profile.ProfilePage
import curatelist.searchresults.SearchResultsPage
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.validation.validator.EmailAddressValidator
import org.apache.wicket.markup.html.link.Link
import com.google.api.services.books.Books
import com.google.api.client.http.HttpResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import org.apache.wicket.spring.injection.annot.SpringBean
import curatelist.IBookService

/**
 *
 *
 * Carousel :
    http://stackoverflow.com/a/9758836
    http://jsfiddle.net/andresilich/S2rnm/
    http://jsfiddle.net/andresilich/S2rnm/show/
 *
 * Homepage
 */
public class HomePage extends BasePage {

    private static final long serialVersionUID = 1L;




    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters
     *            Page parameters
     */
    public HomePage(final PageParameters parameters) {

        add(new HomeFindForm("homeFindForm"))

        

    }


    public class HomeFindForm extends Form{

        transient Logger log =   LoggerFactory.getLogger(HomeFindForm.class)

        private ValueMap properties = new ValueMap()

        public HomeFindForm(String id){

            super(id)
        }

        @Override
        protected void onSubmit() {
            super.onSubmit()    //To change body of overridden methods use File | Settings | File Templates.

            PageParameters params = new PageParameters()
            params.add("v","naren")
            setResponsePage(SearchResultsPage.class)
        }


    }


}
