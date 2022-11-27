package ru.practicum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class ExceptionsHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ModelAndView notFoundHandler(Exception ex) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("status", HttpStatus.NOT_FOUND);
        modelAndView.addObject("reason", "The required object was not found");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public static ModelAndView validationHandler(Exception ex) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("status", HttpStatus.BAD_REQUEST);
        modelAndView.addObject("reason", "For the requested operation the conditions are not met");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {ConflictException.class})
    public static ModelAndView conflictHandler(Exception ex) {
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject("status", HttpStatus.CONFLICT);
        modelAndView.addObject("reason", "Integrity constraint has been violated");
        modelAndView.addObject("message", ex.getMessage());
        modelAndView.addObject("timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        modelAndView.setStatus(HttpStatus.CONFLICT);
        return modelAndView;
    }
}
