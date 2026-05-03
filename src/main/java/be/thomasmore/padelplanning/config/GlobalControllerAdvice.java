package be.thomasmore.padelplanning.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
     @ModelAttribute("currentUrl")
     public String getCurrentUrl(HttpServletRequest request) {
         return request.getRequestURI();
     }
}
