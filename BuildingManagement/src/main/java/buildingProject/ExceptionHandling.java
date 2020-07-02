package buildingProject;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public void handleDatabaseException(Exception e) {
        System.out.println("Database");
    }

}
