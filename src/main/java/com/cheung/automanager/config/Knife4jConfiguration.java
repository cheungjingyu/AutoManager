package com.cheung.automanager.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Slf4j
@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    //通过注解引入配置中的端口参数
    @Value("${server.port}")
    private String port;

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        String url = "http://localhost:" + port + "/doc.html";
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        //.title("swagger-bootstrap-ui-demo RESTful APIs")
                        .description("# ArrayTool RESTful APIs")
                        .termsOfServiceUrl(url)
                        .contact(new Contact("cheung", null, "zhangjingyu@thinkingdata.cn"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("ArrayTool")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.cheung.automanager"))
                .paths(PathSelectors.any())
                .build();
        //打印地址
        log.info("swagger-bootstrap-ui-demo RESTful APIs: {}",  url);
        return docket;

    }
}
