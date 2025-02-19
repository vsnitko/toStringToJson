package org.vsnitko;

import java.util.Map;
import lombok.Builder;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2025.02.14
 */
@Data
@Builder
public class Address {
    int homeId;
    String street;
    byte[] arr;
    Map<String, Object> map;
}
