package kim.gin.web.filter.mdc

import org.slf4j.MDC
import spock.lang.Specification

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.security.Principal

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

/**
 *
 * @since 2017-02-08
 */
class RequestToMDCFilterTest extends Specification {

    def "WebRequestContextFilter - default" () {
        given:
        HttpServletResponse response = Mock(HttpServletResponse.class)

        def data = new HashMap()

        HttpServletRequest request = Mock(HttpServletRequest) {
            getRemoteAddr() >> "127.0.0.1"
            getMethod() >> "POST"
            getRequestURL() >> new StringBuffer("http://localhost:8080")
            getQueryString() >> "a=b"
            getRequestedSessionId() >> "abcdef"
            getUserPrincipal() >> new Principal() {
                String toString() {
                    return "gin, USER"
                }

                String getName() {
                    return "gin"
                }
            }
            getParameterNames() >> new Vector<>(["a", "b", "password"]).elements()
            getParameter("b") >> ["a"]
            getParameterValues("b") >> ["beta"]
            getParameterValues("password") >> ["justLikeTT"]
            getParameterValues("a") >> ["alpha"]

            getHeaderNames() >> new Vector<>(["a", "b", "credential"]).elements()
            getHeader("a") >> "alpha"
            getHeader("b") >> "beta"
            getHeader("credential") >> ["justLikeTT"]
        }

        RequestToMDCFilter webRequestContextFilter = new RequestToMDCFilter()

    when:

        webRequestContextFilter.doFilter(request, response, new FilterChain() {
            @Override
            void doFilter(ServletRequest request1, ServletResponse response1) throws IOException, ServletException {
                data = MDC.getCopyOfContextMap()
            }
        })

    then:
        data.containsKey("remoteAddress") && data.get("remoteAddress") == "127.0.0.1"
        data.containsKey("parameters") && data.get("parameters").contains("a=alpha")
        data.containsKey("parameters") && data.get("parameters").contains("b=beta")
        data.containsKey("parameters") && data.get("parameters").contains("password")
        data.containsKey("httpHeaders") && data.get("httpHeaders").contains("a=alpha")
        data.containsKey("httpHeaders") && data.get("httpHeaders").contains("b=beta")
        data.containsKey("httpHeaders") && data.get("httpHeaders").contains("credential")
    }
}
