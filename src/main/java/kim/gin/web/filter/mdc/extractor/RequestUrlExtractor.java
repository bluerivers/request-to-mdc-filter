package kim.gin.web.filter.mdc.extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @since 2017-02-07
 */
public class RequestUrlExtractor extends BaseHttpExtractor {

    private static final String DEFAULT_KEY_REQUEST_URL = "requestUrl";

    private String fieldName = DEFAULT_KEY_REQUEST_URL;

    @Override
    public String fieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    protected String executeServletRequest(ServletRequest servletRequest) {
        return EMPTY;
    }

    @Override
    protected String executeHttpServletRequest(HttpServletRequest httpServletRequest) {
        String ret = httpServletRequest.getMethod() + "  " + httpServletRequest.getRequestURL().toString();
        String query = httpServletRequest.getQueryString();
        if (query != null) {
            ret += "?" + query;
        }

        return ret;
    }
}
