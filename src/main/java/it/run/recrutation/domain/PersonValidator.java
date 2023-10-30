package it.run.recrutation.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

@Component
class PersonValidator {

  void validate(Person person){
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    var validator = factory.getValidator();
    var validations = validator.validate(person);
    if(!validations.isEmpty()) throw new ValidationException("Person Validations : " + validations.stream().map(ConstraintViolation::getMessage).toList());
  }
}
