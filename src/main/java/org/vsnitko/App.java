package org.vsnitko;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class App {

    public static void main(String[] args) throws IOException {
        final String myString;
        if (true) {
            final Pojo pojo = Pojo.builder()
                    .age(1L)
                    .name("BBB")
                    .arr(new byte[]{1, 2, 3})
                    .address(Address.builder()
                            .arr(new byte[]{4, 5, 6})
                            .homeId(888)
                            .street("ASDSADSA")
                            .map(Map.of("key", "bslsdf", "SASSNSNN", true, "ssssss", 2323))
                            .build())
                    .build();
            myString = pojo.toString();
        } else {
            myString = FileUtils.readFileToString(new File("Person.toString"), UTF_8);
        }
        final String pojoString = myString.replaceAll("\\s+", "");
        System.out.println(pojoString);

        final StringBuilder jsonBuilder = new StringBuilder();
        final int startIndex = pojoString.indexOf('(');
        boolean inArray = false;
        for (int i = startIndex; i < pojoString.length(); i++) {
            final char currChar = pojoString.charAt(i);
            if (currChar == ']') {
                inArray = false;
            }
            if (currChar == '(') {
                jsonBuilder.append("{\""); // {"
            } else if (currChar == '=' && (pojoString.charAt(i + 1) != '[' && pojoString.charAt(i + 1) != '{') && !isClassName(pojoString, i)) {
                jsonBuilder.append("\":\""); // ":"

            } else if (currChar == '=' && (pojoString.charAt(i + 1) == '[' || pojoString.charAt(i + 1) == '{')) {
                if (pojoString.charAt(i + 1) == '[') inArray = true;
                jsonBuilder.append("\":"); // ":

            } else if (currChar == ',' && (pojoString.charAt(i - 1) != ']' && pojoString.charAt(i - 1) != '}' && pojoString.charAt(i - 1) != ')') && !inArray) {
                jsonBuilder.append("\",\n\""); // ","

            } else if (currChar == ',' && (pojoString.charAt(i - 1) == ']' || pojoString.charAt(i - 1) == '}' || pojoString.charAt(i - 1) == ')') && !inArray) {
                jsonBuilder.append(",\""); // ,"

            } else if ((currChar == '}' || currChar == ')') && (pojoString.charAt(i - 1) != '}' && pojoString.charAt(i - 1) != ')')) {
                jsonBuilder.append("\"}"); // "}

            } else if ((currChar == '}' || currChar == ')') && (pojoString.charAt(i - 1) == '}' || pojoString.charAt(i - 1) == ')')) {
                jsonBuilder.append("}"); // }

            } else if (currChar == '=' && isClassName(pojoString, i)) {
                i = pojoString.indexOf('(', i);
                jsonBuilder.append("\":{\""); // ":{"

            } else if (currChar == '{') {
                jsonBuilder.append("{\""); // {"

            } else {
                jsonBuilder.append(currChar);
            }
        }

        final String string = jsonBuilder.toString();
        FileUtils.writeStringToFile(new File("Person.json"), string, UTF_8);
    }

    public static boolean isClassName(String fullStr, int currIndex) {
        try {
            while (true) {
                if (fullStr.charAt(currIndex) == '['
                        || fullStr.charAt(currIndex) == ','
                        || fullStr.charAt(currIndex) == '}'
                ) {
                    return false;
                } else if (fullStr.charAt(currIndex) == '(') {
                    return true;
                } else {
                    currIndex++;
                }
            }
        } catch (Exception e) {
            return false;
        }

    }
}
