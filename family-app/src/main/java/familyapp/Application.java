package familyapp;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
@EnableAutoConfiguration
@ComponentScan

@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class Application extends SpringBootServletInitializer{

public static void main(String[] args) {
	 ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }


@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	return application.sources(Application.class);
}


@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurerAdapter() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("GET");
        }
    };
}


//@ExceptionHandler(BusinessException.class)
//void handleBusinessException(BusinessException e, HttpServletResponse response) throws IOException {
//    response.sendError(HttpStatus.BAD_REQUEST_400 , e.getMessages().get(0).getMessage());
//}

// @Bean
//    public WebMvcConfigurerAdapter adapter() {
//        return new WebMvcConfigurerAdapter() {
//        	@Override
//        	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        		if (!registry.hasMappingForPattern("/**")) {
//        			registry.addResourceHandler("/**").addResourceLocations(
//        				CLASSPATH_RESOURCE_LOCATIONS);
//        		}
//        	}
//        };
//    }

}
