package it.run.recrutation.api;

import it.run.recrutation.domain.PersonFilter;
import org.mapstruct.Mapper;

@Mapper
interface PersonFilterMapper {
  PersonFilter toFilter(PersonRequest request);
}
