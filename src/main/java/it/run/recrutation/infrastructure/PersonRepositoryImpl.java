package it.run.recrutation.infrastructure;

import it.run.recrutation.domain.Person;
import it.run.recrutation.domain.PersonFilter;
import it.run.recrutation.domain.PersonRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class PersonRepositoryImpl implements PersonRepository {
  PersonJpaRepository jpaRepository;
  PersonMapper mapper;
  SessionFactory sessionFactory;

  @Override
  public Optional<Person> findByUniqueId(String uniqueId) {
    var entity = jpaRepository.findByUniqueId(uniqueId);
    return entity.stream().map(mapper::toDomain).findFirst().or(Optional::empty);
  }

  @Override
  public Optional<Person> findByPesel(String pesel) {
    var entity =  jpaRepository.findByPesel(pesel);
    return entity.stream().map(mapper::toDomain).findFirst().or(Optional::empty);
  }

  @Override
  public Set<Person> find(PersonFilter person) {
    var session = sessionFactory.openSession();
    var cb = session.getCriteriaBuilder();
    var query = cb.createQuery(PersonEntity.class);
    var root = query.from(PersonEntity.class);
    query.select(root).where(addParametersToCriteria(person, query, cb, root));
    var makeQuery = session.createQuery(query);
    var result = makeQuery.getResultStream().map(mapper::toDomain).collect(Collectors.toSet());
    session.close();
    return result;
  }

  private Predicate [] addParametersToCriteria(PersonFilter personFilter, JpaCriteriaQuery<PersonEntity> query, CriteriaBuilder builder, JpaRoot<PersonEntity> root){
    List<Predicate> list = new ArrayList<>();
    if(personFilter.getName() != null) {
      var name = root.get(PersonCriteriaApiData.NAME);
      list.add(builder.equal(name, personFilter.getName()));
    }
    if(personFilter.getSurname() != null){
      var surname = root.get(PersonCriteriaApiData.SURNAME);
      list.add(builder.equal(surname, personFilter.getSurname()));
    }
    if(personFilter.getNumber() != null){
      var number = root.get(PersonCriteriaApiData.NUMBER);
      list.add(builder.equal(number, personFilter.getNumber()));
    }
    if(personFilter.getEmail() != null){
      var email = root.get(PersonCriteriaApiData.EMAIL);
      list.add(builder.equal(email, personFilter.getEmail()));
    }
    return rewriteListToArray(list);
  }

  private Predicate [] rewriteListToArray(List<Predicate> predicates){
    Predicate [] result = new Predicate[predicates.size()];
    for(int i = 0; i < predicates.size(); i++){
      result[i] = predicates.get(i);
    }
    return result;
  }

  @Override
  public void saveWithUUID(Person person) {
    jpaRepository.save(mapper.toEntity(person));
  }

  @Override
  public void save(Person person) {
    var toSave = person.toBuilder().uniqueId(UUID.randomUUID().toString()).build();
    jpaRepository.save(mapper.toEntity(toSave));
  }

  @Override
  public void update(Person person) {
    var optional = jpaRepository.findByUniqueId(person.getUniqueId());
    if(optional.isPresent()) {
      var entity = optional.get();
      entity.setName(person.getName());
      entity.setSurname(person.getSurname());
      entity.setNumber(person.getNumber());
      entity.setEmail(person.getEmail());
      entity.setPesel(person.getPesel());
    }
  }

  @Override
  public void delete(String uniqueId) {
    jpaRepository.deleteByUniqueId(uniqueId);
  }
}
