package org.vsnitko;

import lombok.Builder;
import lombok.Data;

/**
 * @author v.snitko
 * @since 2025.02.14
 */
@Data
@Builder
public class Pojo {
    String name;
    Long age;
    byte[] arr;
    Address address;
}
