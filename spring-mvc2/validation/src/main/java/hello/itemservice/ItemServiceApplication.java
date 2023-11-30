package hello.itemservice;

import hello.itemservice.web.validation.ItemValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class ItemServiceApplication {
//public class ItemServiceApplication implements WebMvcConfigurer {


	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	/*@Override
	public Validator getValidator(){
		return new ItemValidator();//글로벌하게 설정하는것이라 각 컨트롤러의 InitBinder 필요없어짐
	}*/
}
