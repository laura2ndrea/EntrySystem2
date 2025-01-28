package campus.u2.entrysystem.Utilities.exceptions;

public class TypeMismatchException extends RuntimeException {
    
    private final String fieldName;
    private final String expectedType;
    private final String receivedValue;

     public TypeMismatchException(String fieldName, String expectedType, String receivedValue) {
        super(String.format("Field '%s' expected a value of type '%s' but received '%s'", fieldName, expectedType, receivedValue));
        this.fieldName = fieldName; 
        this.expectedType = expectedType;
        this.receivedValue = receivedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getExpectedType() {
        return expectedType;
    }

    public String getReceivedValue() {
        return receivedValue;
    }
     
    
    
}
