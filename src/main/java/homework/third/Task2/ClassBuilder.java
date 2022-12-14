package homework.third.Task2;

import homework.third.Task2.exception.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Properties;

public class ClassBuilder {
    private static final DateTimeFormatter DEFAULT_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static Properties properties = new Properties();

    public static <T> T loadClassFromProperties(Class<T> classType, Path propertiesFilePath) {
        if (propertiesFilePath == null || classType == null) {
            throw new IllegalArgumentException("Class or Path value can't be null");
        }
        properties = loadPropertiesFromFile(propertiesFilePath);
        T classInstance = createClassInstance(classType);
        fillClassInstanceFields(classInstance, classType);
        return classInstance;
    }

    private static Properties loadPropertiesFromFile(Path propertiesFilePath) {
        try (InputStream is = new FileInputStream(String.valueOf(propertiesFilePath))) {
            var props = new Properties();
            props.load(is);
            return props;
        } catch (IOException e) {
            throw new PropertyDataException("Can't read property file");
        }
    }

    private static <T> T createClassInstance(Class<T> classType) {
        T classInstance = null;
        try {
            classInstance = classType.cast(getEmptyConstructor(classType).newInstance());
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException |
                 IllegalArgumentException e) {
            e.printStackTrace();
        }
        return classInstance;
    }

    private static <T> Constructor<?> getEmptyConstructor(Class<T> classType) {
        var emptyConstructor = Arrays.stream(classType.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterTypes().length == 0)
                .findFirst()
                .orElse(null);
        if (emptyConstructor != null) {
            emptyConstructor.setAccessible(true);
            return emptyConstructor;
        } else {
            throw new NoEmptyConstructorException("Class has no empty constructor");
        }
    }

    private static <T> void fillClassInstanceFields(T classInstance, Class<T> classType) {
        Arrays.stream(classType.getDeclaredFields())
                .forEach(field -> {
                    String fieldName = field.getName();
                    String setterMethodName = receiveSetterMethodName(fieldName);
                    Object parsedValue = parseWithAnnotationValueCheck(field, fieldName);
                    invokeClassMethod(classType, classInstance, field, parsedValue, setterMethodName);
                });
    }

    private static Object parseWithAnnotationValueCheck(Field field, String fieldName) {
        if (!field.isAnnotationPresent(Property.class)) {
            return getParsedValue(field.getType(), properties.getProperty(fieldName), DEFAULT_DATE_FORMAT);
        } else {
            Property annotation = field.getAnnotation(Property.class);
            return getParsedValue(field.getType(),
                    properties.getProperty(annotation.value().isEmpty() ? fieldName : annotation.value()),
                    annotation.format().isEmpty() ? DEFAULT_DATE_FORMAT : DateTimeFormatter.ofPattern(annotation.format()));
        }
    }

    private static <T> Object getParsedValue(Class<T> fieldType, String value, DateTimeFormatter dateFormat) {
        if (value == null)
            throw new PropertyDataException(String.format("Property file exception for field %s", fieldType.getName()));
        String classSimpleName = fieldType.getSimpleName();
        Object parsedValue;
        switch (classSimpleName) {
            case "Integer", "int" -> parsedValue = Integer.parseInt(value);
            case "String" -> parsedValue = value;
            case "Instant" -> {
                try {
                    parsedValue = LocalDateTime.parse(value, dateFormat).toInstant(ZoneOffset.UTC);
                } catch (DateTimeParseException e) {
                    throw new DateFormatException(String.format("Incorrect Date Format for %s", value));
                }
            }
            default -> throw new FieldParseException(String.format("%s type can't be parsed", classSimpleName));
        }
        return parsedValue;
    }

    private static <T> void invokeClassMethod(Class<T> classType, T instance, Field field, Object parsedValue, String setterMethodName) {
        try {
            Method fieldSetter = classType.getDeclaredMethod(setterMethodName, field.getType());
            fieldSetter.invoke(instance, parsedValue);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new SetterException(String.format("No setter for field %s", field.getName()));
        }
    }

    private static String receiveSetterMethodName(String fieldName) {
        return ("set").concat(fieldName.substring(0, 1).toUpperCase()
                .concat(fieldName.substring(1)));
    }

}
