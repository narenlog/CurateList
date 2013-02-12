package curatelist.common

import curatelist.home.HomePage

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.markup.html.form.Form
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.wicket.util.value.ValueMap
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.validation.validator.EmailAddressValidator
import curatelist.signup.SignupPage

/**
 * Created by IntelliJ IDEA.
 * User: naren
 * Date: 1/2/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
class BasePage  extends WebPage{

    public BasePage(){

     // Add a FeedbackPanel for displaying our messages
    FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel");
    add(feedbackPanel);

        add(new EmailForm("emailForm"))

    add(new Link<Void>("logoLink"){
        @Override
        void onClick() {
            setResponsePage(HomePage.class)
        }
    })


    add(new Link<Void>("signupLink"){
        @Override
        void onClick() {
            setResponsePage(SignupPage.class)
        }
    })


        add(new Link<Void>("aboutLink"){
            @Override
            void onClick() {
                setResponsePage(AboutPage.class)
            }
        })

        add(new Link<Void>("contactLink"){
            @Override
            void onClick() {
                setResponsePage(ContactPage.class)
            }
        })

        /*
    Link registerLink = new Link("registerLink"){
        @Override
        void onClick() {



            setResponsePage(RegisterPage1.class)
        }

        @Override
        protected void onBeforeRender() {
            super.onBeforeRender()    //To change body of overridden methods use File | Settings | File Templates.


            setVisible( !(getPage() instanceof RegisterBasePage) )
        }


    }

    add(registerLink)    */
        

    }


    public class EmailForm extends Form{

        transient Logger log =   LoggerFactory.getLogger(EmailForm.class)

        private ValueMap properties = new ValueMap()

        public EmailForm(String id){

            super(id)

            TextField email = new TextField("emailField", new PropertyModel(properties,"emailAddress") )
            email.add(new EmailAddressValidator())
            add(email)
        }

        @Override
        protected void onSubmit() {
            super.onSubmit()    //To change body of overridden methods use File | Settings | File Templates.


            log.error("******** EMAIL : " + properties.get("emailAddress"))
            System.out.println("******** EMAIL : " + properties.get("emailAddress"))

            getSession().info("Thanks! You will be notified when the site is ready to admit you. Please be patient")
        }


    }

}
