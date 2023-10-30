package it.run.recrutation.api;

import it.run.recrutation.domain.PersonSQLService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sql/person")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class PersonSQLController implements PersonController {
  PersonSQLService service;
  PersonApiMapper mapper;
  PersonFilterMapper filterMapper;

  public PersonResponse getByUniqueId(String uniqueId) {
    return mapper.toResponse(service.getByUniqueId(uniqueId));
  }

  public PersonResponse getByPesel(String pesel) {
    return mapper.toResponse(service.getByPesel(pesel));
  }

  public Set<PersonResponse> getPersons(PersonRequest personRequest) {
    return service.get(filterMapper.toFilter(personRequest)).stream().map(mapper::toResponse).collect(Collectors.toSet());
  }

  public ResponseEntity<?> add(PersonRequest request) {
    service.add(mapper.toDomain(request));
    return ResponseEntity.ok().build();
  }

  public ResponseEntity<?> update(String uniqueId, PersonRequest request) {
    service.update(uniqueId, mapper.toDomain(request));
    return ResponseEntity.ok().build();
  }

  public ResponseEntity<?> delete(String uniqueId) {
    service.delete(uniqueId);
    return ResponseEntity.ok().build();
  }
}
