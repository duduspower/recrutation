package it.run.recrutation.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PersonXMLServiceImpl implements PersonXMLService {
  XMLDataLoader loader;
  XMLDataSaver saver;
  PersonValidator validator;

  @Override
  public Person getByUniqueId(String uniqueId) {
    var result = loader.findPersonByUniqueId(uniqueId);
    return result.orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Person getByPesel(String pesel) {
    var result = loader.findPersonByPesel(pesel);
    return result.orElseThrow(EntityNotFoundException::new);
  }

  @Override
  public Set<Person> get(PersonFilter person) {
    var stream = loader.getPersons().stream();
    if (person.getName() != null) stream = stream.filter(entity -> entity.getName().equals(person.getName()));
    if (person.getSurname() != null) stream = stream.filter(entity -> entity.getSurname().equals(person.getSurname()));
    if (person.getNumber() != null) stream = stream.filter(entity -> entity.getNumber().equals(person.getNumber()));
    if (person.getEmail() != null) stream = stream.filter(entity -> entity.getEmail().equals(person.getEmail()));
    return stream.collect(Collectors.toSet());
  }

  @Override
  public void add(Person person) {
    validator.validate(person);
    person = person.toBuilder().uniqueId(UUID.randomUUID().toString()).build();
    log.info(String.format("Adding file with uniqueId : %s", person.getUniqueId()));
    saver.saveToFile(person);
  }

  @Override
  public void update(String uniqueId, Person person) {
    log.info(String.format("Updating file with uniqueId : %s", uniqueId));
    validator.validate(person);
    saver.update(person.toBuilder().uniqueId(uniqueId).build());
  }

  @Override
  public void delete(String uniqueId) {
    log.info(String.format("Deleting file with uniqueId : %s", uniqueId));
    var toDelete = loader.findPersonFile(uniqueId);
    toDelete.ifPresent(File::delete);
  }
}
