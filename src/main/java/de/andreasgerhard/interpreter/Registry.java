package de.andreasgerhard.interpreter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Registry {

  private static final Map<JsonNode, String> REGISTRY = new HashMap<>();

  private static JsonNode actualPath;

  protected static void setPath(JsonNode node) {
    actualPath = node;
  }

  public static void setValue(String val) {
    REGISTRY.put(actualPath, val);
  }

  public static Optional<String> value(String path) {

    AtomicReference<Optional> result = new AtomicReference<>(Optional.empty());

    REGISTRY.entrySet().forEach(jsonNodeStringEntry -> {

      var ref = new Object() {
        JsonNode traversingNode = jsonNodeStringEntry.getKey();
      };

      Arrays.stream(path.split("/")).forEach(token -> {
        ref.traversingNode = ref.traversingNode.findPath(token);
      });

      if (!(ref.traversingNode instanceof MissingNode)) {
        result.set(Optional.of(jsonNodeStringEntry.getValue()));
      }
    });
    return result.get();
  }

}
