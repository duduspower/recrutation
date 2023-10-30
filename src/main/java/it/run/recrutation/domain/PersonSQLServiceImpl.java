package it.run.recrutation.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class PersonSQLServiceImpl implements PersonSQLService {
  PersonRepository repository;
  PersonValidator validator;

  @Override
  public Person getByUniqueId(String uniqueId) {
    return repository.findByUniqueId(uniqueId).orElseThrow(() -> new EntityNotFoundException(String.format("Person with given uniqueId = %s not exist", uniqueId)));
  }

  @Override
  public Person getByPesel(String pesel) {
    return repository.findByPesel(pesel).orElseThrow(() -> new EntityNotFoundException(String.format("Person with given pesel = %s not exist", pesel)));
  }

  @Override
  public Set<Person> get(PersonFilter person) {
    return repository.find(person);
  }

  @Override
  public void add(Person person) {
    validator.validate(person);
    log.info(String.format("Adding file with uniqueId : %s", person.getUniqueId()));
    repository.save(person);
  }

  @Override
  public void update(String uniqueId, Person person) {
    log.info(String.format("Updating file with uniqueId : %s", uniqueId));
    validator.validate(person);
    repository.update(person.toBuilder().uniqueId(uniqueId).build());
  }

  @Override
  public void delete(String uniqueId) {
    log.info(String.format("Deleting file with uniqueId : %s", uniqueId));
    repository.delete(uniqueId);
  }
}
