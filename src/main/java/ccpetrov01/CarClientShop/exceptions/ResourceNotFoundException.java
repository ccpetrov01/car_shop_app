package ccpetrov01.CarClientShop.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String massage) {
        super(massage);
    }
}
