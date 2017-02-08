package kim.gin.web.filter.mdc.extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @since 2017-02-08
 */
public class UserPrincipalExtractor extends BaseHttpExtractor {

    private static final String DEFAULT_KEY_USER_PRINCIPAL = "userPrincipal";

    private String fieldName = DEFAULT_KEY_USER_PRINCIPAL;

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
        Principal principal = httpServletRequest.getUserPrincipal();
        if (principal == null) {
            return EMPTY;
        }

        return principal.toString();
    }
}
