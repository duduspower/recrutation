package it.run.recrutation.domain;

import java.io.File;
import java.util.Optional;
import java.util.Set;

public interface XMLDataLoader {
  void load();
  Set<Person> getPersons();
  Optional<File> findPersonFile(String uniqueId);
  Optional<Person> findPersonByUniqueId(String uniqueId);
  Optional<Person> findPersonByPesel(String pesel);
}
