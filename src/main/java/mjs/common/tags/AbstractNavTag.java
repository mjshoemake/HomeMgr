package mjs.common.tags;

import org.apache.taglibs.standard.tag.el.core.UrlTag;

public abstract class AbstractNavTag extends AbstractTag {
    
    static final long serialVersionUID = -4174504602386548113L;

    public AbstractNavTag() {
        super();
    }

    protected String encodeUrl(String url, String context) {
        StringBuffer result = new StringBuffer();
        try {
            if (url != null && ! url.equals(""))
                result.append(UrlTag.resolveUrl(url, context, this.pageContext));
            else
                result.append(url);
            String sessionId = req.getSession().getId();
            if (sessionId != null && ! url.contains("jsessionid"))
                result.append(";jsessionid=" + sessionId); 
        } catch (Exception e) {
            log.error("Error encoding URL " + url + " using JSTL UrlTag.  Returning initial URL.", e);
            return url;
        }
        
        return result.toString();
    }
    
}
