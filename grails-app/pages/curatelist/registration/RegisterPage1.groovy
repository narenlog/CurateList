package curatelist.registration

import curatelist.home.HomePage

import org.apache.wicket.markup.html.link.Link

import org.apache.wicket.model.PropertyModel
import org.apache.wicket.util.value.ValueMap
import org.apache.wicket.validation.validator.EmailAddressValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.apache.wicket.markup.html.form.*

/**
 * Created by IntelliJ IDEA.
 * User: naren
 * Date: 1/16/12
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
class RegisterPage1 extends RegisterBasePage {

    public RegisterPage1(){


        add(new RegisterPage1Form("registerPage1Form"))


    }


    public class RegisterPage1Form extends Form{

        transient Logger log =   LoggerFactory.getLogger(RegisterPage1Form.class)

        private ValueMap properties = new ValueMap()

        public RegisterPage1Form(String id){

            super(id)



            add(new RequiredTextField<String>("fullName", new PropertyModel<String>(properties,"fullName")))
            add(new PasswordTextField<String>("password", new PropertyModel<String>(properties,"password")))

            TextField<String> email = new TextField<String>("email", new PropertyModel<String>(properties,"email"))
            email.add( EmailAddressValidator.getInstance())
            add(email)



            add(new Link<Void>("cancelLink"){
                @Override
                void onClick() {
                    setResponsePage(HomePage.class)
                }
            })

        }

        @Override
        protected void onSubmit() {
            super.onSubmit()    //To change body of overridden methods use File | Settings | File Templates.


            for(String key : properties.keySet()){
                log.debug(key + " : " + properties.getString(key))
            }


        }


    }

}
