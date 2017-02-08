package kim.gin.web.filter.mdc

import kim.gin.web.filter.mdc.extractor.HttpHeaderExtractor
import kim.gin.web.filter.mdc.extractor.ParameterExtractor
import kim.gin.web.filter.mdc.extractor.RemoteIpExtractor
import kim.gin.web.filter.mdc.extractor.RequestSessionIdExtractor
import kim.gin.web.filter.mdc.extractor.RequestUrlExtractor
import kim.gin.web.filter.mdc.extractor.UserPrincipalExtractor
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest
import java.security.Principal


/**
 *
 * @since 2017-02-08
 */
class ExtractorTest extends Specification {

    @Shared
    ServletRequest servletRequest

    @Shared
    HttpServletRequest httpRequest

    def setupSpec() {
        servletRequest = Mock(ServletRequest.class)

        httpRequest = Mock(HttpServletRequest) {
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

    }

    def "RemoteIpExtractorTest - setFieldName" () {

        given:
        RemoteIpExtractor remoteIpExtractor = new RemoteIpExtractor()

        when:

        remoteIpExtractor.setFieldName("remoteIp")

        then:

        remoteIpExtractor.fieldName() == "remoteIp"
    }


    def "RemoteIpExtractorTest - extracting" () {
        given:
        RemoteIpExtractor remoteIpExtractor = new RemoteIpExtractor()

        when:
        def ret = remoteIpExtractor.execute(httpRequest)

        then:
        remoteIpExtractor.fieldName() == "remoteAddress"
        ret == "127.0.0.1"

    }

    def "RequestSessionIdExtractorTest - setFieldName" () {
        given:
        RequestSessionIdExtractor requestSessionIdExtractor = new RequestSessionIdExtractor()

        when:
        requestSessionIdExtractor.setFieldName("session")

        then:
        requestSessionIdExtractor.fieldName() == "session"
    }

    def "RequestSessionIdExtractorTest - extracting" () {
        given:
        RequestSessionIdExtractor requestSessionIdExtractor = new RequestSessionIdExtractor()

        when:
        def ret = requestSessionIdExtractor.execute(httpRequest)

        then:
        requestSessionIdExtractor.fieldName() == "requestedSessionId"
        requestSessionIdExtractor.execute(servletRequest) == Extractor.EMPTY
        ret == "abcdef"
    }

    def "RequestUrlExtractorTest - setFieldName" () {
        given:
        RequestUrlExtractor requestUrlExtractor = new RequestUrlExtractor()

        when:
        requestUrlExtractor.setFieldName("request")

        then:
        requestUrlExtractor.fieldName() == "request"
    }

    def "RequestUrlExtractorTest - extracting" () {
        given:
        RequestUrlExtractor requestUrlExtractor = new RequestUrlExtractor()

        when:

        def ret = requestUrlExtractor.execute(httpRequest)

        then:
        requestUrlExtractor.fieldName() == "requestUrl"
        requestUrlExtractor.execute(servletRequest) == Extractor.EMPTY
        ret == "POST  http://localhost:8080?a=b"
    }

    def "UserPrincipalExtractorTest - setFieldName" () {
        given:
        UserPrincipalExtractor userPrincipalExtractor = new UserPrincipalExtractor()

        when:
        userPrincipalExtractor.setFieldName("auth info")

        then:
        userPrincipalExtractor.fieldName() == "auth info"
    }


    def "UserPrincipalExtractorTest - extracting" () {
        given:
        UserPrincipalExtractor userPrincipalExtractor = new UserPrincipalExtractor()

        when:

        def ret = userPrincipalExtractor.execute(httpRequest)

        then:
        userPrincipalExtractor.fieldName() == "userPrincipal"
        userPrincipalExtractor.execute(servletRequest) == Extractor.EMPTY
        ret == "gin, USER"
    }


    def "ParameterExtractorTest - setFieldName" () {
        given:
        ParameterExtractor parameterExtractor = new ParameterExtractor()

        when:
        parameterExtractor.setFieldName("params")

        then:
        parameterExtractor.fieldName() == "params"
    }

    def "ParameterExtractorTest - extracting" () {
        given:
        ParameterExtractor parameterExtractor = new ParameterExtractor()

        when:
        def ret = parameterExtractor.execute(httpRequest)

        then:
        parameterExtractor.fieldName() == "parameters"
        ret == "a=alpha\nb=beta\npassword=justLikeTT\n"
    }


    def "headerExtractorTest - setFieldName" () {
        given:
        HttpHeaderExtractor httpHeaderExtractor = new HttpHeaderExtractor()

        when:
        httpHeaderExtractor.setFieldName("headers")

        then:
        httpHeaderExtractor.fieldName() == "headers"
    }


    def "headerExtractorTest - extracting " () {
        given:
        HttpHeaderExtractor httpHeaderExtractor = new HttpHeaderExtractor()

        when:
        def ret = httpHeaderExtractor.execute(httpRequest)

        then:
        httpHeaderExtractor.fieldName() == "httpHeaders"
        httpHeaderExtractor.execute(servletRequest) == Extractor.EMPTY
        ret == "a=alpha\nb=beta\ncredential=[justLikeTT]"
    }
}
