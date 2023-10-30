package it.run.recrutation.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

interface PersonController {
  @GetMapping("/uniqueId/{uniqueId}")
  PersonResponse getByUniqueId(@PathVariable String uniqueId);

  @GetMapping("/pesel/{pesel}")
  PersonResponse getByPesel(@PathVariable String pesel);

  @GetMapping
  Set<PersonResponse> getPersons(@RequestBody PersonRequest personRequest);

  @PostMapping
  ResponseEntity<?> add(@RequestBody PersonRequest request);

  @PutMapping("/{uniqueId}")
  ResponseEntity<?> update(@PathVariable String uniqueId, @RequestBody PersonRequest request);

  @DeleteMapping("/{uniqueId}")
  ResponseEntity<?> delete(@PathVariable String uniqueId);
}
