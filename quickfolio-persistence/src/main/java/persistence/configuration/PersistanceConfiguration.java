package persistence.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("persistence.*")
@EnableJpaRepositories(basePackages = "persistence")
@ComponentScan(
        basePackages = {"persistence"},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = PersistanceService.class),
        })
public class PersistanceConfiguration {
}
