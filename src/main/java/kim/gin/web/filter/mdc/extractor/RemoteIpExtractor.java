package kim.gin.web.filter.mdc.extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @since 2017-02-07
 */
public class RemoteIpExtractor extends BaseHttpExtractor {

    private static final String DEFAULT_KEY_REMOTE_ADDRESS = "remoteAddress";

    private List<String> HEADER_NAMES_FOR_REMOTE_IP_ADDRESS = Arrays.asList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    );

    private String fieldName = DEFAULT_KEY_REMOTE_ADDRESS;


    @Override
    public String fieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    protected String executeServletRequest(ServletRequest servletRequest) {
        return servletRequest.getRemoteAddr();
    }

    @Override
    protected String executeHttpServletRequest(HttpServletRequest httpServletRequest) {

        final String UNKNOWN = "unknown";

        for (String headerName : HEADER_NAMES_FOR_REMOTE_IP_ADDRESS) {
            String ip = httpServletRequest.getHeader(headerName);
            if (!(ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip))) {
                return ip;
            }
        }

        return httpServletRequest.getRemoteAddr();
    }


}
