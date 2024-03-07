package XML;

import Model.Airport;
import Model.Obstacle;
import javax.xml.transform.dom.DOMSource;
import java.io.File;

public interface XMLExporter {
  /**
   * Export an Aiport to XML
   * @param airport to export
   * @return false if export fails, true otherwise
   */
  public boolean exportAirport(Airport airport,File file);

  /**
   * Export a list of obstacles
   * @param obstacle to export
   * @return false if export fails, true otherwise
   */
  public boolean exportObstacles(Obstacle obstacle, File file);
}
