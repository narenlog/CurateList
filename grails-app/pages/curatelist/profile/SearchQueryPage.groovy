package curatelist.profile

import curatelist.common.BasePage
import org.apache.wicket.PageParameters
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.spring.injection.annot.SpringBean
import curatelist.IBookService
import org.apache.wicket.markup.html.form.Form
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.wicket.util.value.ValueMap
import curatelist.searchresults.SearchResultsPage
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.validation.validator.EmailAddressValidator

/**
 * Homepage
 */
public class SearchQueryPage extends BasePage {

	private static final long serialVersionUID = 1L;

	// TODO Add any page properties or variables here

    /**
	 * Constructor that is invoked when page is invoked without a session.
	 *
	 * @param parameters
	 *            Page parameters
	 */
    public SearchQueryPage(final PageParameters parameters) {

        add(new SearchForm("searchForm"))
    }




    public class SearchForm extends Form{

        transient Logger log =   LoggerFactory.getLogger(SearchForm.class)

        private ValueMap properties = new ValueMap()

        @SpringBean(name="bookService")
        IBookService bookService


        public SearchForm(String id){

            super(id)


            TextField searchText = new TextField("searchText", new PropertyModel(properties,"searchText") )
            searchText.setRequired(true)
            add(searchText)
        }

        @Override
        protected void onSubmit() {
            super.onSubmit()    //To change body of overridden methods use File | Settings | File Templates.

            
            String searchText = properties.getString("searchText")
            
            bookService.findBooksByTitle(searchText)
        }


    }

}
