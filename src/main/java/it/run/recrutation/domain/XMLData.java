package it.run.recrutation.domain;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public final class XMLData {
static String UNIQUE_ID = "uniqueId";
static String NAME = "name";
static String SURNAME = "surname";
static String NUMBER = "number";
static String EMAIL = "email";
static String PESEL = "pesel";
static String INTERNAL = "Internal";
static String EXTERNAL = "External";
static String xmlFilesDirectory = "src/main/resources/xmlDirectory";
static String XML = ".xml";
}
