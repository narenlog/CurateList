import org.apache.wicket.protocol.http.WebApplication;

import grails.util.*
import org.apache.wicket.Application
import org.apache.wicket.spring.injection.annot.SpringComponentInjector
import org.codehaus.groovy.grails.commons.ApplicationHolder
import curatelist.home.HomePage
import curatelist.profile.ProfilePage
import curatelist.profile.JoyProfilePage
import curatelist.profile.MattWolskyPage
import curatelist.profile.VarunPage
import curatelist.profile.AnnaProfilePage
import curatelist.searchresults.SearchResultsPage
import curatelist.profile.SeanPage

public class WicketApplication extends WebApplication {
    
    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    Class getHomePage() { HomePage.class }

    /**
     * Configures Grails' application context to be used for @SpringBean injection
     */
    protected void init() {
        super.init()
        addComponentInstantiationListener(new SpringComponentInjector(this, ApplicationHolder.getApplication().getMainContext(), false));

        mountBookmarkablePage("/searchresults",SearchResultsPage.class)

        mountBookmarkablePage("/profile",ProfilePage.class)
        mountBookmarkablePage("/profile/joy",JoyProfilePage.class)
        mountBookmarkablePage("/profile/matt",MattWolskyPage.class)
        mountBookmarkablePage("/profile/tommy",VarunPage.class)
        mountBookmarkablePage("/profile/anna",AnnaProfilePage.class)
        mountBookmarkablePage("/profile/sean",SeanPage.class)
    }

    /**
     * If we're running in Grails development environment use Wicket development environment
     */
    public String getConfigurationType() {
        if (GrailsUtil.isDevelopmentEnv()) {
            return Application.DEVELOPMENT
        }
        return Application.DEPLOYMENT
    }


}