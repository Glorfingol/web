package cmpl.web.robots;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.h2.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller pour le robot d'indexation google et autres
 * 
 * @author Louis
 *
 */
@Controller
public class RobotsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RobotsController.class);

  /**
   * Mapping pour le robot d'indexation google et autres
   * 
   * @param response
   */
  @GetMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt"})
  @ResponseBody
  public void printRobot(HttpServletResponse response) {

    LOGGER.info("Accès à la page des robots");
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream resourceAsStream = classLoader.getResourceAsStream("robot.txt");
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
