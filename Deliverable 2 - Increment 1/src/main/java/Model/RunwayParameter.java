package Model;

public class RunwayParameter {
  private String parameterName;
  private String parameterOriginalValue;
  private String parameterNewValue;

  //Constructor
  public RunwayParameter(String parameterName, String parameterOriginalValue, String parameterNewValue) {
    this.parameterName = parameterName;
    this.parameterOriginalValue = parameterOriginalValue;
    this.parameterNewValue = parameterNewValue;
  }

  //Getters
  public String getParameterName() {
    return parameterName;
  }

  public String getParameterOriginalValue() {
    return parameterOriginalValue;
  }

  public String getParameterNewValue() {
    return parameterNewValue;
  }

}
