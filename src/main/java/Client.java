import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import fr.nro.spring.demo.beans.Person;
import reactor.core.publisher.Flux;

public class Client {

	public static void main(String[] args) throws Exception{
		WebClient client = WebClient.create("http://localhost:8080");
		Flux<Person> flux = client.get()
		  .uri("/person")
		  .accept(MediaType.TEXT_EVENT_STREAM)
		  .exchange()
		  .flatMapMany(r -> r.bodyToFlux(Person.class))
		  .log();
		 
		flux.subscribe(System.out::println);
		
		while(true){
			Thread.sleep(1000);
		}

	}

}
