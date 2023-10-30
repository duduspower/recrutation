package it.run.recrutation.domain;

import java.util.Set;

public interface PersonService {
  Person getByUniqueId(String uniqueId);

  Person getByPesel(String pesel);

  Set<Person> get(PersonFilter person);

  void add(Person person);

  void update(String uniqueId, Person person);

  void delete(String uniqueId);
}
