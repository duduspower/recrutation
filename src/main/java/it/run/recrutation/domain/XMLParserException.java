package it.run.recrutation.domain;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
class XMLParserException extends RuntimeException{
  XMLParserException(String message) {
    super(message);
  }
}
