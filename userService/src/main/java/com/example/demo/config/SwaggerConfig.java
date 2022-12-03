package com.example.demo.config;
//package com.oycbest.springbootswagger.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.HttpRequestHandlerServlet;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SwaggerConfig {
    @Bean
    public Docket creatRestApi() {
//        Docket 摘要对象。通过对象配置描述文件的信息
        Docket docket = new Docket(DocumentationType.OAS_30);
        // 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
        docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))
                .build();
        return docket;
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //大标题
                .title("接口文档")
                //详细描述
                .description("我是描述信息")
                //作者
                .contact(new Contact("shenan", "http://www.itelearning.cn", "2747334005@qq.com"))
                //版本
                .version("版本号：V.1")
                //协议
                .license("The Apache License")
                //协议URL
                .licenseUrl("http://www.baidu.com")
                .build();
    }
}
