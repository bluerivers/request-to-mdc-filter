package kim.gin.web.filter.mdc.extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 *
 *
 * @since 2017-02-07
 */
public class ParameterExtractor extends BaseHttpMapExtractor {

    private static final String DEFAULT_KEY_PARAMETERS = "parameters";

    private static final String DEFAULT_ELEMENT_DELIMITER = "\n";
    private static final String DEFAULT_KEY_VALUE_DELIMITER = "=";
    private static final String DEFAULT_VALUE_ARRAY_DELIMITER = ",";

    private String fieldName = DEFAULT_KEY_PARAMETERS;

    private String elementDelimiter = DEFAULT_ELEMENT_DELIMITER;
    private String keyValueDelimiter = DEFAULT_KEY_VALUE_DELIMITER;
    private String valueArrayDelimiter = DEFAULT_VALUE_ARRAY_DELIMITER;

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

    public void setValueArrayDelimiter(String valueArrayDelimiter) {
        this.valueArrayDelimiter = valueArrayDelimiter;
    }

    @Override
    protected String executeServletRequest(ServletRequest servletRequest) {
        StringBuilder builder = new StringBuilder();

        for (Enumeration names = servletRequest.getParameterNames(); names.hasMoreElements(); ) {

            String name = (String) names.nextElement();
            if (excludes(name)) {
                continue;
            }

            String[] values = servletRequest.getParameterValues(name);
            builder.append(name).append(keyValueDelimiter);

            for (int index = 0; index < values.length ; index++) {
                builder.append(String.valueOf(values[index]));
                if (index < values.length - 1) {
                    builder.append(valueArrayDelimiter);
                }
            }

            builder.append(elementDelimiter);
        }

        return builder.toString();
    }

    @Override
    protected String executeHttpServletRequest(HttpServletRequest httpServletRequest) {
        return executeServletRequest(httpServletRequest);
    }


}
