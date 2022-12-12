package homework.third.task2TestClasses;

import homework.third.Task2.Property;

public class ClassWithWrongFieldsType {
    @Property(value = "numberProperty")
    private Float number;

    public Float getNumber() {
        return number;
    }

    public void setNumber(Float number) {
        this.number = number;
    }
}

