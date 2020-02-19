package com.maiboroda.easyWords.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.maiboroda.easyWords.converter.CollectionDtoToCollectionConverter;
import com.maiboroda.easyWords.converter.CollectionToCollectionDtoConverter;
import com.maiboroda.easyWords.converter.UserDtoToUserConverter;
import com.maiboroda.easyWords.converter.UserToUserDtoConverter;
import com.maiboroda.easyWords.converter.WordToProgressDtoConverter;
import com.maiboroda.easyWords.converter.WordToWordDtoConverter;
import com.maiboroda.easyWords.service.DateService;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private DateService dateService;

    @Autowired
    public WebConfiguration(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserToUserDtoConverter(dateService));
        registry.addConverter(new WordToProgressDtoConverter(dateService));
        registry.addConverter(new UserDtoToUserConverter());
        registry.addConverter(new CollectionDtoToCollectionConverter());
        registry.addConverter(new CollectionToCollectionDtoConverter());
        registry.addConverter(new WordToWordDtoConverter());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
