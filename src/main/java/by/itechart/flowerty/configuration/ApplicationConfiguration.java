package by.itechart.flowerty.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import by.itechart.flowerty.Application;

/**
 * @author Eugene Putsykovich(slesh) Mar 26, 2015
 *
 *         Configuration for fetch properties from resources
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class, excludeFilters = @ComponentScan.Filter({ Controller.class,
	Configuration.class }))
public class ApplicationConfiguration {
    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
	PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
	ppc.setLocations(new ClassPathResource("/persistence.properties"), new ClassPathResource("/mongo.properties"),
		new ClassPathResource("/local.properties"));

	return ppc;
    }
}
