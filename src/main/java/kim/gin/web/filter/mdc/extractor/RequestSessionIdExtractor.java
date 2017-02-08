package kim.gin.web.filter.mdc.extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @since 2017-02-08
 */
public class RequestSessionIdExtractor extends BaseHttpExtractor {

    private static final String DEFAULT_KEY_REQUESTED_SESSION_ID = "requestedSessionId";

    private String fieldName = DEFAULT_KEY_REQUESTED_SESSION_ID;

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
        return httpServletRequest.getRequestedSessionId();
    }
}
