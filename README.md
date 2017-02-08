# Request to MDC Filter

This code provides the filter that injects some information of servlet request into MDC(slf4j). 
Input information will be printed when logger is used. It is important where this filter is located.
We recommends that the location is before a filter that processes user defined logic.   


## Usage

### Spring

```java
    @Bean
    public FilterRegistrationBean requestToMDCFilter() {
        RequestToMDCFilter filter = new RequestToMDCFilter();
        FilterRegistrationBean bean = new FilterRegistrationBean(filter);
        bean.setOrder(2);
        return bean;
    }
```

## Customization

```java

    HttpHeaderExtractor headerExtractor = new HttpHeaderExtractor();
    headerExtractor.setFieldName("headers");
    
    SimpleWordsExcluder swe = new SimpleWordsExcluder("password", "credential");
    
    headerExtractor.setExcluder(swe);
     
    RequestToMDCFilter filter = new RequestToMDCFilter(
            new RemoteIpExtractor(),
                            new RequestSessionIdExtractor(),
                            new RequestUrlExtractor(),
                            headerExtractor,
                            new ParameterExtractor());
```


## License

_Request to MDC Filter_ is Open Source software released under the Apache 2.0 license.
