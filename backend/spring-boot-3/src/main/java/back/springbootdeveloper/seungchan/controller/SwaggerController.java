package back.springbootdeveloper.seungchan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//  http://localhost:8080/v3/api-docs
// http://localhost:8080/swagger-ui/index.html

@Controller
public class SwaggerController {

  @GetMapping("/api/doc/")
  public String redirectSwagger() {
    return "redirect:/swagger-ui/index.html";
  }
}
