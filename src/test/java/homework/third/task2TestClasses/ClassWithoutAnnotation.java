package homework.third.task2TestClasses;

import java.time.Instant;

public class ClassWithoutAnnotation {
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

}
