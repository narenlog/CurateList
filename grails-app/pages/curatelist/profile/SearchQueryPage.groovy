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
import org.apache.wicket.markup.html.WebMarkupContainer
import curatelist.books.BooksModel
import org.apache.wicket.markup.repeater.RepeatingView
import org.apache.wicket.markup.html.pages.RedirectPage
import org.apache.wicket.markup.html.basic.Label
import curatelist.utils.ExternalImage
import org.apache.wicket.model.Model

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

        RepeatingView searchResults = new RepeatingView("searchResults")
        searchResults.setOutputMarkupId(true)
        searchResults.setOutputMarkupPlaceholderTag(true)
        add(searchResults)

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
            
            List<BooksModel> books = bookService.findBooksByTitle(searchText)


            RepeatingView searchResults = new RepeatingView("searchResults")

            books.each { BooksModel book ->

                WebMarkupContainer item = new WebMarkupContainer(searchResults.newChildId())
                searchResults.add(item)


                Link<Void> linkToBuyBook = new Link<Void>("linkToBuyBook"){

                    @Override
                    public void onClick(){
                        setResponsePage(new RedirectPage(book.getLinkToBuyBook()))
                    }

                }
                linkToBuyBook.add(new ExternalImage("mediumImage",new Model(book.getImageLinks().getMedium())))
                item.add(linkToBuyBook)
                
                item.add(new Label("bookTitle",book.getTitle()))


            }
            getPage().replace(searchResults)

        }


    }

}
