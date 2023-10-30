package it.run.recrutation.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true,access = AccessLevel.PUBLIC)
public class Person {
  String uniqueId;
  @NotEmpty
  String name;
  @NotEmpty
  String surname;
  @NotNull
  PersonType type;
  @Pattern(regexp = "(^$|[0-9]{11})", message = "Invalid phone number.You must give country phone code too (for example 12123123123)")
  String number;
  @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email")
  String email;
  @Pattern(regexp = "(^$|[0-9]{11})", message = "Invalid pesel")
  String pesel;
}
