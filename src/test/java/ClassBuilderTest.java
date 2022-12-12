import homework.third.Task2.ClassBuilder;
import homework.third.Task2.exception.*;
import homework.third.task2TestClasses.*;
import org.junit.Test;

import java.nio.file.Path;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class ClassBuilderTest {
    private final Path testProperties = Path.of("src/test/resources/propertiesForTest.properties");

    @Test
    public void classCreatingWithoutAnnotationTest() {
        ClassWithoutAnnotation classWithoutAnnotation = ClassBuilder.loadClassFromProperties(ClassWithoutAnnotation.class, testProperties);
        assertEquals("value1", classWithoutAnnotation.getStringProperty());
        assertEquals(Instant.parse("2022-11-29T18:30:00Z"), classWithoutAnnotation.getTimeProperty());
        assertEquals(10, classWithoutAnnotation.getNumberProperty());
    }

    @Test
    public void classCreatingWithAnnotationTest() {
        ClassWithAnnotation classWithAnnotation = ClassBuilder.loadClassFromProperties(ClassWithAnnotation.class, testProperties);
        assertEquals("value1", classWithAnnotation.getString());
        assertEquals(Instant.parse("2022-12-12T14:31:30Z"), classWithAnnotation.getTimeProperty());
        assertEquals(10, classWithAnnotation.getNumber());
    }

    @Test
    public void classCreatingWithoutDataInProperties() {
        assertThrows(PropertyDataException.class, () -> ClassBuilder.loadClassFromProperties(ClassWithoutDataInProperties.class, testProperties));
    }

    @Test
    public void classCreatingWithoutSetterTest() {
        assertThrows(SetterException.class, () -> ClassBuilder.loadClassFromProperties(ClassWithoutSetter.class, testProperties));
    }

    @Test
    public void classCreatingWithWrongDateFormat() {
        assertThrows(DateFormatException.class, () -> ClassBuilder.loadClassFromProperties(ClassWithWrongDateFormat.class, testProperties));
    }

    @Test
    public void classCreatingWithWrongFieldType() {
        assertThrows(FieldParseException.class, () -> ClassBuilder.loadClassFromProperties(ClassWithWrongFieldsType.class, testProperties));
    }

    @Test
    public void classCreatingWithoutEmptyConstructor() {
        assertThrows(NoEmptyConstructorException.class, () -> ClassBuilder.loadClassFromProperties(ClassWithoutEmptyConstructor.class, testProperties));
    }

    @Test
    public void classCreatingWithNullData(){
        assertThrows(IllegalArgumentException.class, () -> ClassBuilder.loadClassFromProperties(null, null));
    }

}
