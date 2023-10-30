package it.run.recrutation.api;

import it.run.recrutation.domain.PersonXMLService;
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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/xml/person")
class PersonXMLController implements PersonController {
  PersonXMLService service;
  PersonApiMapper mapper;
  PersonFilterMapper filterMapper;

  @Override
  public PersonResponse getByUniqueId(String uniqueId) {
    return mapper.toResponse(service.getByUniqueId(uniqueId));
  }

  @Override
  public PersonResponse getByPesel(String pesel) {
    return mapper.toResponse(service.getByPesel(pesel));
  }

  @Override
  public Set<PersonResponse> getPersons(PersonRequest personRequest) {
    return service.get(filterMapper.toFilter(personRequest)).stream().map(mapper::toResponse).collect(Collectors.toSet());
  }

  @Override
  public ResponseEntity<?> add(PersonRequest request) {
    service.add(mapper.toDomain(request));
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<?> update(String uniqueId, PersonRequest request) {
    service.update(uniqueId, mapper.toDomain(request));
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<?> delete(String uniqueId) {
    service.delete(uniqueId);
    return ResponseEntity.ok().build();
  }
}
