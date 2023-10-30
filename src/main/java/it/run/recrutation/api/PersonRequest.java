package it.run.recrutation.api;


import it.run.recrutation.domain.PersonType;

record PersonRequest(String name, String surname, PersonType type, String number, String email, String pesel) {
}
