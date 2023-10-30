package it.run.recrutation.infrastructure;

import it.run.recrutation.domain.PersonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
public class PersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "person_generator")
  @SequenceGenerator(name = "person_generator", sequenceName = "person_seq")
  @Column(updatable = false, nullable = false)
  Long id;
  String uniqueId;
  String name;
  String surname;
  @Enumerated(value = EnumType.STRING)
  PersonType type;
  String number;
  String email;
  String pesel;
}
