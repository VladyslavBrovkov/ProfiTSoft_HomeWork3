package homework.third.task2TestClasses;

import homework.third.Task2.Property;

import java.time.Instant;

public class ClassWithWrongDateFormat {
    @Property(format = "hh:mm yyyy")
    private Instant timeProperty;

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }
}
