package it.run.recrutation.infrastructure;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class PersonCriteriaApiData {
  static String NAME = "name";
  static String SURNAME = "surname";
  static String NUMBER = "number";
  static String EMAIL = "email";
}
