package api.configurarion;

import domaine.annotations.DomainService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import persistence.configuration.PersistanceConfiguration;

@Configuration
@ComponentScan(
        basePackages = {"domaine"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = DomainService.class)})
@Import(PersistanceConfiguration.class)
public class AppConfiguration {

}
