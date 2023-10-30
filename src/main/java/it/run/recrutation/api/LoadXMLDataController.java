package it.run.recrutation.api;

import it.run.recrutation.domain.XMLDataLoader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/xml")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoadXMLDataController {
  XMLDataLoader xmlDataLoader;

  @PostMapping
  ResponseEntity<?> load() {
    xmlDataLoader.load();
    return ResponseEntity.ok().build();
  }
}
