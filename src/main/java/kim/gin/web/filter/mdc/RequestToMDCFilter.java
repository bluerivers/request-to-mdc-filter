package kim.gin.web.filter.mdc;

import kim.gin.web.filter.mdc.extractor.*;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @since 2017-02-08
 */
public class RequestToMDCFilter implements Filter {

    private List<Extractor> extractors;

    public RequestToMDCFilter() {
        this(new RemoteIpExtractor(),
                new RequestSessionIdExtractor(),
                new RequestUrlExtractor(),
                new HttpHeaderExtractor(),
                new ParameterExtractor());
    }

    public RequestToMDCFilter(Extractor... extractors) {
        this.extractors = Arrays.asList(extractors);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            extractValuesFromRequestToMDC(request);
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private void extractValuesFromRequestToMDC(ServletRequest request) {

        for (Extractor extractor : extractors) {
            String result = extractor.execute(request);
            if (result.equals(Extractor.EMPTY)) {
                continue;
            }

            MDC.put(extractor.fieldName(), result);
        }
    }
}
