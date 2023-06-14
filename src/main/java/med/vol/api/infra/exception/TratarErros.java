package med.vol.api.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratarErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tartarErro404(){
        return ResponseEntity.notFound().build();
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tartarErro400(MethodArgumentNotValidException ex){
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErro::new).toList());
    }

    public record DadosErro(String campo, String mensagem) {
        public DadosErro(FieldError field){
            this(field.getField(), field.getDefaultMessage());
        }
    }
}
