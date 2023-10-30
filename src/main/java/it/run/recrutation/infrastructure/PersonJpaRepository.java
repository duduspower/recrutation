package it.run.recrutation.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonJpaRepository extends JpaRepository<PersonEntity, Long> {
  Optional<PersonEntity> findByUniqueId(String uniqueId);

  Optional<PersonEntity> findByPesel(String pesel);

  void deleteByUniqueId(String uniqueId);
}
