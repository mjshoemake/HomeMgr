package mjs.common.tags;

import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

/**
 * Tag used to write out a URL that comes from 
 * a request attribute, session attribute, etc.  
 * This tag uses the expression handler to 
 * find the URL.  It then encodes the URL 
 * leveraging JSTL's url tag, and writes the URL
 * to the JSP output.
 */
public class UrlTag extends AbstractNavTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    private String url = null;

    public UrlTag() {
        super();
    }

    public String getValue() {
        return url;
    }

    public void setValue(String value) {
        this.url = value;
    }

    public int output(AbstractTag tag) {
        Logger log = null;
        JspWriter out = tag.getWriter();

        try {
            log = Logger.getLogger("JSP");

            if (url != null) {
                String context = req.getContextPath();
                String encoded = context + url;
                log.info("URL: " + encoded);
                out.print(encoded);
            }
        } catch (java.lang.Exception e) {
            log.error(e.getMessage(), e);
        }
        return SKIP_BODY;
    }

}
