package it.run.recrutation.infrastructure;

import it.run.recrutation.domain.Person;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {
  Person toDomain(PersonEntity entity);

  PersonEntity toEntity(Person person);
}
