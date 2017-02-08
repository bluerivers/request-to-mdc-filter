package kim.gin.web.filter.mdc;

import javax.servlet.ServletRequest;

/**
 *
 *
 * @since 2017-02-07
 */
public interface Extractor {

    String EMPTY = "\n\t\t\n\t\t\n\uE000\uE001\uE002\n\t\t\t\t\n";

    String fieldName();

    /**
     *
     * Execute character string from a servlet request. Extract information in servlet request
     * and combine character string with some rules.
     *
     * @param servletRequest user-requested servlet request to extract some information
     * @return character string which extracted information is combined
     */
    String execute(ServletRequest servletRequest);
}
