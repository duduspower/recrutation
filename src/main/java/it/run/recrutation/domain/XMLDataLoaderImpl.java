package it.run.recrutation.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class XMLDataLoaderImpl implements XMLDataLoader {
  PersonRepository repository;

  @Override
  public void load() {
    var persons = getPersons();
    persons.forEach(this::save);
  }

  public Set<Person> getPersons() {
    var internal = getFilesInFolder(XMLData.INTERNAL, XMLData.xmlFilesDirectory);
    var external = getFilesInFolder(XMLData.EXTERNAL, XMLData.xmlFilesDirectory);
    DocumentBuilder finalBuilder = getBuilder();
    var internalPersons = internal.stream().map(file -> getPerson(file, finalBuilder, PersonType.INTERNAL));
    var externalPersons = external.stream().map(file -> getPerson(file, finalBuilder, PersonType.EXTERNAL));
    return Stream.concat(internalPersons, externalPersons).collect(Collectors.toSet());
  }

  private void save(Person person) {
    var entity = repository.findByUniqueId(person.getUniqueId());
    if (entity.isPresent()) {
      log.info(String.format("Person with given uniqueId %s exist in sql database", person.getUniqueId()));
    } else {
      log.info(String.format("Adding Person with uniqueId %s", person.getUniqueId()));
      repository.saveWithUUID(person);
    }
  }

  private DocumentBuilder getBuilder(){
    DocumentBuilder builder = null;
    try {
      builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLParserException(e.getMessage());
    }
    return builder;
  }

  private List<File> getFilesInFolder(String folderName, String xmlFilesPath) {
    var folderPath = Path.of(xmlFilesPath, folderName);
    var folder = folderPath.toFile();
    if (!folder.isDirectory())
      throw new XMLParserException(String.format("Given location is not a directory : %s", folderPath));
    return Arrays.stream(folder.listFiles()).toList();
  }

  private Person getPerson(File file, DocumentBuilder builder, PersonType type) {
    return getPersonAttributesFromFile(getDocumentForFile(file, builder)).type(type).build();
  }

  private Document getDocumentForFile(File file, DocumentBuilder builder){
    Document doc = null;
    try {
      doc = builder.parse(file);
    } catch (SAXException | IOException e) {
      throw new XMLParserException(e.getMessage());
    }
    return doc;
  }

  private Person.PersonBuilder getPersonAttributesFromFile(Document doc) {
    var uniqueId = doc.getElementsByTagName(XMLData.UNIQUE_ID).item(0).getFirstChild().getNodeValue();
    var name = doc.getElementsByTagName(XMLData.NAME).item(0).getFirstChild().getNodeValue();
    var surname = doc.getElementsByTagName(XMLData.SURNAME).item(0).getFirstChild().getNodeValue();
    var number = doc.getElementsByTagName(XMLData.NUMBER).item(0).getFirstChild().getNodeValue();
    var email = doc.getElementsByTagName(XMLData.EMAIL).item(0).getFirstChild().getNodeValue();
    var pesel = doc.getElementsByTagName(XMLData.PESEL).item(0).getFirstChild().getNodeValue();
    return Person.builder().uniqueId(uniqueId).name(name).surname(surname).number(number).email(email).pesel(pesel);
  }

  public Optional<File> findPersonFile(String uniqueId) {
    var builder  = getBuilder();
    var internal = getFilesInFolder(XMLData.INTERNAL, XMLData.xmlFilesDirectory).stream().toList().iterator();
    var external = getFilesInFolder(XMLData.EXTERNAL, XMLData.xmlFilesDirectory).stream().toList().iterator();
    while (internal.hasNext()) {
      var next = internal.next();
      if (getPerson(next, builder, PersonType.INTERNAL).getUniqueId().equals(uniqueId))
        return Optional.of(next);
    }
    while (external.hasNext()) {
      var next = external.next();
      if (getPerson(next, builder, PersonType.EXTERNAL).getUniqueId().equals(uniqueId))
        return Optional.of(next);
    }
    return Optional.empty();
  }

  public Optional<Person> findPersonByUniqueId(String uniqueId) {
    var builder  = getBuilder();
    var internal = getFilesInFolder(XMLData.INTERNAL, XMLData.xmlFilesDirectory).stream().toList().iterator();
    var external = getFilesInFolder(XMLData.EXTERNAL, XMLData.xmlFilesDirectory).stream().toList().iterator();
    while (internal.hasNext()) {
      var next = internal.next();
      var person = getPerson(next, builder, PersonType.INTERNAL);
      if (person.getUniqueId().equals(uniqueId))
        return Optional.of(person);
    }
    while (external.hasNext()) {
      var next = external.next();
      var person = getPerson(next, builder, PersonType.EXTERNAL);
      if (person.getUniqueId().equals(uniqueId))
        return Optional.of(person);
    }
    return Optional.empty();
  }

  public Optional<Person> findPersonByPesel(String pesel) {
    var builder  = getBuilder();
    var internal = getFilesInFolder(XMLData.INTERNAL, XMLData.xmlFilesDirectory).stream().toList().iterator();
    var external = getFilesInFolder(XMLData.EXTERNAL, XMLData.xmlFilesDirectory).stream().toList().iterator();
    while (internal.hasNext()) {
      var next = internal.next();
      var person = getPerson(next, builder, PersonType.INTERNAL);
      if (person.getPesel().equals(pesel))
        return Optional.of(person);
    }
    while (external.hasNext()) {
      var next = external.next();
      var person = getPerson(next, builder, PersonType.EXTERNAL);
      if (person.getPesel().equals(pesel))
        return Optional.of(person);
    }
    return Optional.empty();
  }
}
