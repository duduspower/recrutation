package it.run.recrutation.api;

import it.run.recrutation.domain.Person;
import org.mapstruct.Mapper;

@Mapper
interface PersonApiMapper {
  Person toDomain(PersonRequest request);
  PersonResponse toResponse(Person person);
}
