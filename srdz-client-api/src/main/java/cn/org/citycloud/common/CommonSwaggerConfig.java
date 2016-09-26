package cn.org.citycloud.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(basePackages={"cn.org.citycloud.controller"})
public class CommonSwaggerConfig
{

	@Bean
    public Docket swaggerSpringMvcPlugin(){
    	// ApiInfo
    	ApiInfo apiInfo = new ApiInfo(
    			"善融大宗一期",
    			"用户端接口RESTful APIs",
    			"1.0",
    			"http://www.citycloud.org.cn/",
    			"Lanbo",
    			"CCDC",
    			"http://www.citycloud.org.cn/"
    			);
    	
    	Docket docket = new Docket(DocumentationType.SWAGGER_2)
    			.apiInfo(apiInfo);
    	
    	return docket;
    }
}
