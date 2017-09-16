package fr.nro.spring.demo.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.nro.spring.demo.beans.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rx.subjects.PublishSubject;

@RestController
@RequestMapping("/person")
public class PersonController {

	private PublishSubject<Person> subject = PublishSubject.create();

	@PostConstruct
	public void before() {
	}

	@RequestMapping(method = RequestMethod.POST)
	public Mono<Person> create(@RequestBody Person p) {
		this.subject.onNext(p);
		return Mono.just(p);
	}

	@RequestMapping(produces = "text/event-stream")
	public Flux<Person> list() {
		//return source; 
		
		return Flux.create(c -> {
			subject.subscribe(onNext ->{
				c.next(onNext);
				//c.complete();	
			});
			
		});
		
	}

}
