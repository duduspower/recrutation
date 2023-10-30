package it.run.recrutation.domain;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class XMLDataSaver {
  XMLDataLoader loader;
  static String PERSON = "person";

  void saveToFile(Person person) {
    DocumentBuilder builder = null;
    try {
      builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (ParserConfigurationException e) {
      throw new XMLParserException(e.getMessage());
    }
    var doc = createDocument(builder,person);
    DOMSource dom = new DOMSource(doc);
    Transformer transformer = null;
    try {
      transformer = TransformerFactory.newInstance()
          .newTransformer();
    } catch (TransformerConfigurationException e) {
      throw new RuntimeException(e);
    }
    try {
      transformer.transform(dom, generateStream(person));
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  void update(Person person){
    var file = loader.findPersonFile(person.getUniqueId());
    if(file.isEmpty()) throw new EntityNotFoundException(String.format("XML file with given person not exist, uniqueId : %s", person.getUniqueId()));
    file.get().delete();
    saveToFile(person);
  }

  Document createDocument(DocumentBuilder builder, Person person){
    Document doc = builder.newDocument();
    var root = doc.createElement(PERSON);
    doc.appendChild(root);
    var uniqueId = doc.createElement(XMLData.UNIQUE_ID);
    uniqueId.setTextContent(person.getUniqueId());
    root.appendChild(uniqueId);
    var name = doc.createElement(XMLData.NAME);
    name.setTextContent(person.getName());
    root.appendChild(name);
    var surname = doc.createElement(XMLData.SURNAME);
    surname.setTextContent(person.getSurname());
    root.appendChild(surname);
    var number = doc.createElement(XMLData.NUMBER);
    number.setTextContent(person.getNumber());
    root.appendChild(number);
    var email = doc.createElement(XMLData.EMAIL);
    email.setTextContent(person.getEmail());
    root.appendChild(email);
    var pesel = doc.createElement(XMLData.PESEL);
    pesel.setTextContent(person.getPesel());
    root.appendChild(pesel);
    return doc;
  }

  private StreamResult generateStream(Person person) {
    if (person.getType() == PersonType.INTERNAL)
      return new StreamResult(Path.of(XMLData.xmlFilesDirectory, XMLData.INTERNAL, person.getUniqueId() + XMLData.XML).toFile());
    return new StreamResult(Path.of(XMLData.xmlFilesDirectory, XMLData.EXTERNAL, person.getUniqueId()).toFile() + XMLData.XML);
  }
}
