package homework.third.task2TestClasses;

import homework.third.Task2.Property;

import java.time.Instant;

public class ClassWithAnnotation {

    @Property(value = "stringProperty")
    private String string;

    @Property(value = "numberProperty")
    private int number;

    @Property(format = "yyyy-MM-dd'T'HH:mm:ssZ", value = "customTimeProperty")
    private Instant time;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Instant getTimeProperty() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }
}
