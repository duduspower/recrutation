package it.run.recrutation.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonFilter {
  String name;
  String surname;
  PersonType type;
  String number;
  String email;
  String pesel;
}
