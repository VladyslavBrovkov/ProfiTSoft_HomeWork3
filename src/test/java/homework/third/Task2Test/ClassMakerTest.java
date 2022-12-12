package homework.third.Task2Test;

import homework.third.Task2.ClassMaker;
import homework.third.Task2.exception.*;
import homework.third.Task2Test.testClass.*;
import org.junit.Test;

import java.nio.file.Path;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ClassMakerTest {
    private final Path testProperties = Path.of("src/test/resources/propertiesForTest.properties");

    @Test
    public void classCreatingWithoutAnnotationTest() {
        ClassWithoutAnnotation classWithoutAnnotation = ClassMaker.loadClassFromProperties(ClassWithoutAnnotation.class, testProperties);
        System.out.println(String.class.getSimpleName());
        assertEquals("value1", classWithoutAnnotation.getStringProperty());
        assertEquals(Instant.parse("2022-11-29T18:30:00Z"), classWithoutAnnotation.getTimeProperty());
        assertEquals(10, classWithoutAnnotation.getNumberProperty());
    }

    @Test
    public void classCreatingWithAnnotationTest() {
        ClassWithAnnotation classWithAnnotation = ClassMaker.loadClassFromProperties(ClassWithAnnotation.class, testProperties);
        assertEquals("value1", classWithAnnotation.getStringProperty());
        assertEquals(Instant.parse("2022-11-29T18:30:00Z"), classWithAnnotation.getTimeProperty());
        assertEquals(10, classWithAnnotation.getNumber());
    }

    @Test
    public void classCreatingWithCustomFieldsNames() {
        ClassWithCustomFieldsNames classWithCustomFieldsNames = ClassMaker.loadClassFromProperties(ClassWithCustomFieldsNames.class, testProperties);
        assertEquals("StringString", classWithCustomFieldsNames.getString());
        assertEquals(10, classWithCustomFieldsNames.getProperty());
    }

    @Test
    public void classCreatingWithoutDataInProperties() {
        assertThrows(PropertyDataException.class, () -> ClassMaker.loadClassFromProperties(ClassWithoutDataInProperties.class, testProperties));
    }

    @Test
    public void classCreatingWithoutSetterTest() {
        assertThrows(SetterException.class, () -> ClassMaker.loadClassFromProperties(ClassWithoutSetter.class, testProperties));
    }

    @Test
    public void classCreatingWithWrongDateFormat() {
        assertThrows(DateFormatException.class, () -> ClassMaker.loadClassFromProperties(ClassWithWrongDateFormat.class, testProperties));
    }

    @Test
    public void classCreatingWithWrongFieldType() {
        assertThrows(FieldParseException.class, () -> ClassMaker.loadClassFromProperties(ClassWithWrongFieldsType.class, testProperties));
    }

    @Test
    public void classCreatingWithoutEmptyConstructor() {
        assertThrows(NoEmptyConstructorException.class, () -> ClassMaker.loadClassFromProperties(ClassWithoutEmptyConstructor.class, testProperties));
    }

    @Test
    public void classCreatingWithNullData(){
        assertThrows(IllegalArgumentException.class, () -> ClassMaker.loadClassFromProperties(null, null));
    }

}
