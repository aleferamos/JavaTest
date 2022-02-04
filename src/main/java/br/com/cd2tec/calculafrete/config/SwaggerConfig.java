package br.com.cd2tec.calculafrete.config;

import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FretePersistDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket getBean(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.cd2tec.calculafrete.controllers"))
                .paths(PathSelectors.any())
                .build()
                .ignoredParameterTypes(Pageable.class, Sort.class)
                .apiInfo(getInfo());
    }

    private ApiInfo getInfo(){
        return new ApiInfoBuilder().title("SigaBem").description("Api para calcular frete").license("cd2tec").build();
    }
}
