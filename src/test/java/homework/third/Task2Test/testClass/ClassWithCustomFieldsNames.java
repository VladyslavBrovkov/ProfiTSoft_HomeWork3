package homework.third.Task2Test.testClass;

import homework.third.Task2.Property;

public class ClassWithCustomFieldsNames {
    @Property(value = "numberProperty")
    private int property;

    @Property(value = "numberProp")
    private String string;

    public int getProperty() {
        return property;
    }

    public void setProperty(int property) {
        this.property = property;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
