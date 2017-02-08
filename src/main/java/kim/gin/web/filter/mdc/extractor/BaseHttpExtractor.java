package kim.gin.web.filter.mdc.extractor;

import kim.gin.web.filter.mdc.Extractor;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @since 2017-02-07
 */
public abstract class BaseHttpExtractor implements Extractor {

    @Override
    public String execute(ServletRequest servletRequest) {
        return (servletRequest instanceof HttpServletRequest) ?
                executeHttpServletRequest((HttpServletRequest)servletRequest) :
                executeServletRequest(servletRequest);
    }

    protected abstract String executeServletRequest(ServletRequest servletRequest);
    protected abstract String executeHttpServletRequest(HttpServletRequest httpServletRequest);
}
