package homework.third.task2TestClasses;

import java.time.Instant;

public class ClassWithoutSetter {
    private String stringProperty;

    private int numberProperty;

    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public int getNumberProperty() {
        return numberProperty;
    }

    public void setNumberProperty(int numberProperty) {
        this.numberProperty = numberProperty;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
