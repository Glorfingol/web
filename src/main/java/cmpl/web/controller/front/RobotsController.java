package cmpl.web.controller.front;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.h2.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RobotsController {

  @RequestMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt"})
  @ResponseBody
  public void printRobot(HttpServletResponse response) {

    InputStream resourceAsStream = null;
    try {

      ClassLoader classLoader = getClass().getClassLoader();
      resourceAsStream = classLoader.getResourceAsStream("robot.txt");

      response.addHeader("Content-disposition", "filename=robot.txt");
      response.setContentType("text/plain");
      IOUtils.copy(resourceAsStream, response.getOutputStream());
      response.flushBuffer();
      resourceAsStream.close();
    } catch (Exception e) {
    }

  }

}
