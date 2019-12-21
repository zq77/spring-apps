package com.z.controller;

import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexController {

  private List<String> repository = new ArrayList<>();

  @GetMapping("/")
  public Publisher<String> index() {
    return Mono.just("Hello world");
  }

  @GetMapping("/add")
  public Publisher<String> add() {
    repository.add("z");
    return Mono.just("success");
  }

  @GetMapping("/list")
  public Publisher<String> list() {
    return Mono.just(repository.toString());
  }
}