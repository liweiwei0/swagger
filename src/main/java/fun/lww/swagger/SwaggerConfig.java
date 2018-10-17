package fun.lww.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	//设置完成后进入SwaggerUI，右上角出现“Authorization”按钮，点击即可输入我们配置的参数。
	//对于不需要输入参数的接口（上文所述的包含auth的接口），在未输入Authorization参数就可以访问。
	//其他接口则将返回401错误。点击右上角“Authorization”按钮，输入配置的参数后即可访问。参数输入后全局有效，无需每个接口单独输入。
	//至此，完成Swagger2 非全局、无需重复输入的Head参数配置。
	//Swagger2的相关完整代码如下（工程基于Springboot）
	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfoBuilder()
						.title("demo")
						.description("demo")
						.contact(new Contact("swagger demo", null, null))
						.version("1.0")
						.build())
				.useDefaultResponseMessages(false)
				.select()
				.apis(RequestHandlerSelectors.basePackage("fun.lww.swagger"))
				.paths(PathSelectors.any())
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts())
				;
	}

	//通过Swagger2的securitySchemes配置全局参数：如下列代码所示，securitySchemes的ApiKey中增加一个名为“Authorization”，
	// type为“header”的参数。
	private List<ApiKey> securitySchemes() {
		List<ApiKey> list = new ArrayList<>();
		list.add(new ApiKey("Authorization", "Authorization", "header"));
		return list;
	}

	//在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），
	// 如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。
	// 即不需要使用上文中设置的名为“Authorization”，type为“header”的参数。
	private List<SecurityContext> securityContexts() {
		List<SecurityContext> list = new ArrayList<>();
		list.add(SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$"))
				.build());
		return list;
	}

	List<SecurityReference> defaultAuth() {
		List<SecurityReference> list = new ArrayList<>();
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		list.add(new SecurityReference("Authorization", authorizationScopes));
		return list;
	}
}
