package homework.third.Task2Test.testClass;

import homework.third.Task2.Property;

public class ClassWithoutEmptyConstructor {
    @Property(value = "numberProperty")
    private int number;

    ClassWithoutEmptyConstructor(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
