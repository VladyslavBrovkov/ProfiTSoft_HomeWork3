package homework.third.Task2Test;

import homework.third.Task2.ClassMaker;
import homework.third.Task2Test.testClass.NotAnnotatedClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class ClassMakerTest {
    private final Path testProperties = Path.of("src/test/resources/propertiesForTest.properties");

    @Test
    public void classCreatingWithoutAnnotationTest() throws IOException {
        NotAnnotatedClass notAnnotatedClass = ClassMaker.loadClassFromProperties(NotAnnotatedClass.class, testProperties);
        System.out.println(notAnnotatedClass);
        assertEquals("value1", notAnnotatedClass.getStringProperty());
        assertEquals(10, notAnnotatedClass.getNumberProperty());
        assertEquals(Instant.parse("2022-11-29T18:30:00Z"), notAnnotatedClass.getTimeProperty());
    }

}
