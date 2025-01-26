package campus.u2.entrysystem.Utilities.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.http.HttpStatus;

public class ErrorResponses {

    private String error;
    private String message;
    private LocalDateTime date;
    private HttpStatus status;
    private Integer statusId;

    public ErrorResponses() {
    }

    public ErrorResponses(String error, String message,  HttpStatus status, Integer statusId) {
        this.error = error;
        this.message = message;
        this.date = LocalDateTime.now();
        this.status = status;
        this.statusId = statusId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

}
