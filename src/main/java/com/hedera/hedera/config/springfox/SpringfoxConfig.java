package com.hedera.hedera.config.springfox;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Configuration
@EnableSwagger2
public class SpringfoxConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public Docket gatewayApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .pathMapping(getPathMapping("local"))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST, defaultErrorsMessage())
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> defaultErrorsMessage() {
        return Lists.newArrayList(
                new ResponseMessageBuilder().code(NOT_FOUND.value()).message("Data not found").build(),
                new ResponseMessageBuilder()
                        .code(INTERNAL_SERVER_ERROR.value())
                        .message("Internal error on Hedera")
                        .build(),
                new ResponseMessageBuilder()
                        .code(BAD_REQUEST.value())
                        .message("Invalid data / json")
                        .build(),
                new ResponseMessageBuilder()
                        .code(BAD_GATEWAY.value())
                        .message("Hedera")
                        .build());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Hedera")
                .description("API for Hedera")
                .version("1.0")
                .build();
    }

    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(null);
    }

    private String getPathMapping(String profile) {
        String pathMapping = "/";
        if (!profile.equalsIgnoreCase("local")) {
            return pathMapping.concat(applicationName);
        }
        return  pathMapping;
    }
}
