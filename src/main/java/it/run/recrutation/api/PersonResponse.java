package it.run.recrutation.api;

import it.run.recrutation.domain.PersonType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
class PersonResponse {
  String uniqueId;
  String name;
  String surname;
  PersonType type;
  String number;
  String email;
  String pesel;
}
