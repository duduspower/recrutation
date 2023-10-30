package it.run.recrutation.domain;

import java.util.Optional;
import java.util.Set;

public interface PersonRepository {
  Optional<Person> findByUniqueId(String uniqueId);
  Optional<Person> findByPesel(String pesel);
  Set<Person> find(PersonFilter person);
  void save(Person person);
  void update(Person person);
  void saveWithUUID(Person person);
  void delete(String uniqueId);
}
