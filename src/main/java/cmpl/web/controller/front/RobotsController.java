package cmpl.web.controller.front;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RobotsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RobotsController.class);

  @RequestMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt"})
  @ResponseBody
  public void printRobot(HttpServletResponse response) {

    LOGGER.info("Accès à la page des robots");
    InputStream resourceAsStream = null;
    ClassLoader classLoader = getClass().getClassLoader();
    resourceAsStream = classLoader.getResourceAsStream("robot.txt");
    modifyResponse(response, resourceAsStream);

  }

  private void modifyResponse(HttpServletResponse response, InputStream resourceAsStream) {

    response.addHeader("Content-disposition", "filename=robot.txt");
    response.setContentType("text/plain");
    try {
      IOUtils.copy(resourceAsStream, response.getOutputStream());
      response.flushBuffer();
      resourceAsStream.close();
    } catch (Exception e) {
      LOGGER.error("Impossible de lire le fichier des robots", e);
    }

  }

}
