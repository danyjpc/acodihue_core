package com.peopleapps.util;


import javax.enterprise.context.RequestScoped;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

@RequestScoped
public class PropertiesService {

    private static final String PROPERTIES_FILE = "/people.properties";

    public List<String> retrieveStringListProperty(String key) {
        Properties prop = new Properties();
        try {
            prop.load(PropertiesService.class.getResourceAsStream(PROPERTIES_FILE));
            String propertiesRawList = prop.getProperty(key);
            return Arrays.asList(propertiesRawList.split(","));
        } catch (Exception e) {
            return Arrays.asList("-99");
        }
    }

    public List<Long> retrieveLongListProperty(String key) {
        Properties prop = new Properties();
        try {
            prop.load(PropertiesService.class.getResourceAsStream(PROPERTIES_FILE));
            String propertiesRawList = prop.getProperty(key);
            List<Long> propertiesIntegerList = Arrays.asList(propertiesRawList.split(","))
                    .stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());
            return propertiesIntegerList;
        } catch (Exception e) {
            return Arrays.asList(-99L);
        }
    }

    public Optional<String> retrieveStringProperty(String key) {
        Properties prop = new Properties();
        try {
            prop.load(PropertiesService.class.getResourceAsStream(PROPERTIES_FILE));
            return Optional.ofNullable(prop.getProperty(key));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Optional<String> retrieveStaticStringProperty(String key) {
        Properties prop = new Properties();
        try {
            prop.load(PropertiesService.class.getResourceAsStream(PROPERTIES_FILE));
            return Optional.ofNullable(prop.getProperty(key));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}