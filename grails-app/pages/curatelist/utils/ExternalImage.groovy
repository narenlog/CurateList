package curatelist.utils

import org.apache.wicket.markup.html.WebComponent
import org.apache.wicket.model.IModel
import org.apache.wicket.markup.ComponentTag

/**
 * Created by IntelliJ IDEA.
 * User: naren
 * Date: 3/3/13
 * Time: 8:13 PM
 * To change this template use File | Settings | File Templates.
 *
 * Courtesy:http://developme.wordpress.com/2010/05/25/wicket-image-tag-linking-to-an-external-resource/
 */
public class ExternalImage extends WebComponent
{

    /**
     * @param id wicket id on the page
     * @param model reference the external URL from which the image is gotten
     *          for ex.: "http://images.google.com/img/10293.gif"
     */
    public ExternalImage(String id, IModel urlModel)
    {
        super( id, urlModel );
    }

    protected void onComponentTag(ComponentTag tag)
    {
        super.onComponentTag( tag );
        checkComponentTag( tag, "img" );
        tag.put( "src", getDefaultModelObjectAsString() );
    }
}
