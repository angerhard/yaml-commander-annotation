package de.andreasgerhard.interpreter;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;

class RegistryTest {


  @Test
  void shouldAddVariable() {
    JsonNode jsonNode = getJsonNode();
    Registry.setPath(jsonNode);
    Registry.setValue("expected-value");
  }

  private JsonNode getJsonNode() {
    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    ObjectNode objectNode = mapper.createObjectNode();
    objectNode.set("root", mapper.createObjectNode().put("val", "expected-value"));
    return objectNode;
  }

  @Test
  void shouldReadValue() {
    JsonNode jsonNode = getJsonNode();
    Registry.setPath(jsonNode);
    Registry.setValue("expected-value");
    assertThat(Registry.value("root/val")).contains("expected-value");
  }

}