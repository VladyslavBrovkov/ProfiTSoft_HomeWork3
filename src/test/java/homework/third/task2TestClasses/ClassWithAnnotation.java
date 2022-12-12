package homework.third.task2TestClasses;

import homework.third.Task2.Property;

import java.time.Instant;

public class ClassWithAnnotation {
    private String stringProperty;

    @Property(value = "numberProperty")
    private int number;

    @Property(format = "dd.MM.yyyy HH:mm")
    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
