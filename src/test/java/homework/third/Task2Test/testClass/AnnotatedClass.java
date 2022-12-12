package homework.third.Task2Test.testClass;

import homework.third.Task2.Property;

import java.time.Instant;
import java.util.Objects;

public class AnnotatedClass {

    private String stringProperty;

    private Integer numberProperty;

    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getNumberProperty() {
        return numberProperty;
    }

    public void setNumberProperty(Integer numberProperty) {
        this.numberProperty = numberProperty;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotatedClass that = (AnnotatedClass) o;
        return Objects.equals(stringProperty, that.stringProperty) && Objects.equals(numberProperty, that.numberProperty) && Objects.equals(timeProperty, that.timeProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringProperty, numberProperty, timeProperty);
    }

    @Override
    public String toString() {
        return "NotAnnotatedClass{" +
                "stringProperty='" + stringProperty + '\'' +
                ", numberProperty=" + numberProperty +
                ", timeProperty=" + timeProperty +
                '}';
    }
}