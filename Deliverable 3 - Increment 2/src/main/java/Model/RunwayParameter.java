//package Model;
//
//import javafx.beans.property.SimpleDoubleProperty;
//import javafx.beans.property.SimpleStringProperty;
//
//public class RunwayParameter {
//    private SimpleStringProperty parameterName;
//    private SimpleDoubleProperty originalValue;
//    private SimpleDoubleProperty revisedValue;
//
//    public RunwayParameter(String parameterName, double originalValue, double revisedValue) {
//        this.parameterName = new SimpleStringProperty(parameterName);
//        this.originalValue = new SimpleDoubleProperty(originalValue);
//        this.revisedValue = new SimpleDoubleProperty(revisedValue);
//    }
//
//    public String getParameterName() {
//        return parameterName.get();
//    }
//
//    public void setParameterName(String value) {
//        parameterName.set(value);
//    }
//
//    public SimpleStringProperty parameterNameProperty() {
//        return parameterName;
//    }
//
//    public double getOriginalValue() {
//        return originalValue.get();
//    }
//
//    public void setOriginalValue(double value) {
//        originalValue.set(value);
//    }
//
//    public SimpleDoubleProperty originalValueProperty() {
//        return originalValue;
//    }
//
//    public double getRevisedValue() {
//        return revisedValue.get();
//    }
//
//    public void setRevisedValue(double value) {
//        revisedValue.set(value);
//    }
//
//    public SimpleDoubleProperty revisedValueProperty() {
//        return revisedValue;
//    }
//}
package Model;

public class RunwayParameter {
    private String name;
    private String originalValue;
    private String newValue;

    public RunwayParameter(String name, String originalValue, String newValue) {
        this.name = name;
        this.originalValue = originalValue;
        this.newValue = newValue;
    }

    public String getName() {
        return name;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public String getNewValue() {
        return newValue;
    }
}
