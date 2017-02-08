package kim.gin.web.filter.mdc.extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @since 2017-02-07
 */
public class HttpHeaderExtractor extends BaseHttpMapExtractor {

    private static final String DEFAULT_KEY_HTTP_HEADERS = "httpHeaders";
    private static final String DEFAULT_ELEMENT_DELIMITER = "\n";
    private static final String DEFAULT_KEY_VALUE_DELIMITER = "=";

    private String fieldName = DEFAULT_KEY_HTTP_HEADERS;

    private String elementDelimiter = DEFAULT_ELEMENT_DELIMITER;
    private String keyValueDelimiter = DEFAULT_KEY_VALUE_DELIMITER;

    @Override
    public String fieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setElementDelimiter(String elementDelimiter) {
        this.elementDelimiter = elementDelimiter;
    }

    public void setKeyValueDelimiter(String keyValueDelimiter) {
        this.keyValueDelimiter = keyValueDelimiter;
    }

    @Override
    protected String executeServletRequest(ServletRequest servletRequest) {
        return EMPTY;
    }

    @Override
    protected String executeHttpServletRequest(HttpServletRequest httpServletRequest) {
        StringBuilder builder = new StringBuilder();
        for (Enumeration names = httpServletRequest.getHeaderNames(); names.hasMoreElements(); ) {
            String name = (String) names.nextElement();
            if (excludes(name)) {
                continue;
            }

            String value = httpServletRequest.getHeader(name);
            builder.append(name).
                    append(keyValueDelimiter).
                    append(value);
            if (names.hasMoreElements()) {
                builder.append(elementDelimiter);
            }
        }
        return builder.toString();
    }
}
