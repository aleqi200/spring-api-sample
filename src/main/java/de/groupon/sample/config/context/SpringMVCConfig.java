package de.groupon.sample.config.context;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.groupon.sample.config.GSONHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.List;

@Configuration
@EnableWebMvc
@Profile("!test")
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(gsonConverter());
        converters.add(jaxbConverter());
    }

    @Bean
    public GSONHttpMessageConverter gsonConverter() {
        return new GSONHttpMessageConverter();
    }

    @Bean
    public HttpMessageConverter jaxbConverter(){
        return new Jaxb2RootElementHttpMessageConverter();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json",MediaType.APPLICATION_JSON)
                .mediaType("xml",MediaType.APPLICATION_XML)
                .favorPathExtension(true).favorParameter(true);
    }
}
