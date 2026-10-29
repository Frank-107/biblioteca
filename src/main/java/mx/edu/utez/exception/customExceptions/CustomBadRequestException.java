package mx.edu.utez.exception.customExceptions;


public class CustomBadRequestException extends RuntimeException{
    public CustomBadRequestException(String mensaje){
        super(mensaje);

    }
}
