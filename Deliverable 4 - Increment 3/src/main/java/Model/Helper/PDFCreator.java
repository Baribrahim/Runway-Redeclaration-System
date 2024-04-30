package Model.Helper;

import Model.*;
import View.AirportManager;
import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class PDFCreator {
  Font headerFont = FontFactory.getFont("Times New Roman", 18, Font.BOLD);
  Font subTitleFont = FontFactory.getFont("Times New Roman", 14, Font.BOLD);
  Font subTitleItalic = FontFactory.getFont("Times New Roman", 12, Font.BOLDITALIC);
  Font subTitleFontSmall = FontFactory.getFont("Times New Roman", 12, Font.BOLD);
  Font bodyFont = FontFactory.getFont("Times New Roman", 12, Font.NORMAL);
  Font resultFont = FontFactory.getFont("Times New Roman", 12, Font.NORMAL, BaseColor.BLUE );

  Document document;

  public PDFCreator(Airport a, Obstacle o, PhysicalRunway r) throws IOException, DocumentException {
    createPdf(a, o, r);
  }

  public void createPdf(Airport a, Obstacle o, PhysicalRunway r) throws DocumentException, IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Generate PDF Report");

    // Set initial directory for the file chooser
    File userDirectory = new File(System.getProperty("user.home"));
    fileChooser.setInitialDirectory(userDirectory);

    // Set extension filters based on the type of file to be saved
    FileChooser.ExtensionFilter pdfFilter = new FileChooser.ExtensionFilter("PDF Files (*.pdf)", "*.pdf");
    fileChooser.getExtensionFilters().addAll(pdfFilter);

    // Show the file chooser and get the selected file directory
    File selectedDirectory = fileChooser.showSaveDialog(AirportManager.getStage());

    if(selectedDirectory != null){
      this.document = new Document();
      PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(selectedDirectory)));
      document.open();

      PdfPTable header = new PdfPTable(1);
      header.setHorizontalAlignment(Element.ALIGN_LEFT);
      header.setSpacingBefore(10);
      header.completeRow();
      header.setSpacingAfter(10);
      header.getDefaultCell().setBorder(0);
      header.addCell(new Paragraph("Calculation report", headerFont));
      document.add(header);

      addNewlines(document, 1);

      PdfPTable details = new PdfPTable(2);
      details.setHorizontalAlignment( 0);
      details.setSpacingBefore(10);
      details.setSpacingAfter(10);
      details.setWidths(new int[] { 8, 5 });
      details.getDefaultCell().setBorder(0);
      PdfPCell title = new PdfPCell(new Phrase("Airport details", subTitleFont));
      title.setColspan(2);
      title.setPaddingBottom(10);
      title.setBorder(0);
      details.addCell(title);
      details.addCell(new Phrase("Name:", subTitleFontSmall));
      details.addCell(a.getName());
      details.addCell(new Phrase("Runway:", subTitleFontSmall));
      details.addCell(r.getPhysicalRunwayName());
      details.addCell(new Phrase("Runway length:", subTitleFontSmall));
      details.addCell(r.getLogicalRunways().get(0).getTora() + "m");
      details.addCell(new Phrase("Blast protection:", subTitleFontSmall));
      details.addCell(PhysicalRunway.getBlastProtection() + "m");
      details.addCell(new Phrase("Strip end:", subTitleFontSmall));
      details.addCell(PhysicalRunway.getStripEnd() + "m");
      PdfPTable obstacle = new PdfPTable(2);
      obstacle.setHorizontalAlignment( 0);
      obstacle.setSpacingBefore(10);
      obstacle.setSpacingAfter(10);
      obstacle.getDefaultCell().setBorder(0);
      obstacle.setWidths(new int[] { 8, 5 });
      PdfPCell titleObstacle = new PdfPCell(new Phrase("Obstacle details", subTitleFont));
      titleObstacle.setColspan(2);
      titleObstacle.setPaddingBottom(10);
      titleObstacle.setBorder(0);
      obstacle.addCell(titleObstacle);
      obstacle.addCell(new Phrase("Name:", subTitleFontSmall));
      obstacle.addCell(o.getName());
      obstacle.addCell(new Phrase("Width:", subTitleFontSmall));
      obstacle.addCell(o.getWidth()+ "m");
      obstacle.addCell(new Phrase("Height:", subTitleFontSmall));
      obstacle.addCell(o.getHeight()+ "m");
      obstacle.addCell(new Phrase("Distance from threshold:", subTitleFontSmall));
      obstacle.addCell(o.getDistanceFromThreshold()+ "m");
      obstacle.addCell(new Phrase("Distance from center", subTitleFontSmall));
      obstacle.addCell(o.getDistanceFromCentre() + "m from "+(o.getDirFromCentre().equals("L")? "left": "right"));

      PdfPTable outer = new PdfPTable(1);
      outer.setHorizontalAlignment( Element.ALIGN_LEFT);
      outer.setSpacingBefore(10);
      outer.setSpacingAfter(10);
      outer.getDefaultCell().setBorder(0);
      outer.addCell(details);
      outer.addCell(obstacle);
      document.add(outer);
      addNewlines(document, 3);
      document.add(new Paragraph("Table of original and revised values", subTitleFont));
      document.add(new Paragraph(" ", bodyFont));
      PdfPTable table = createBreakdownTable(r);
      table.setHorizontalAlignment(Element.ALIGN_LEFT);
      document.add(table);

      document.newPage();
      String breakdownL = ParameterCalculator.convertCalcBreakdown(o, r.getLogicalRunways().get(0));
      String flightMethodL = ParameterCalculator.getFlightMethod(o, r.getLogicalRunways().get(0));
      String breakdownR = ParameterCalculator.convertCalcBreakdown(o, r.getLogicalRunways().get(1));
      String flightMethodR = ParameterCalculator.getFlightMethod(o, r.getLogicalRunways().get(1));

      document.add(new Paragraph("Calculation breakdown", subTitleFont));
      addNewlines(document, 1);
      document.add(new Paragraph(new Phrase(r.getLogicalRunways().get(0).getDesignator()+" "+flightMethodL, subTitleItalic)));
      document.add(new Paragraph(new Phrase(breakdownL, bodyFont)));
      document.add(new Paragraph(new Phrase(r.getLogicalRunways().get(1).getDesignator()+" "+flightMethodR, subTitleItalic)));
      document.add(new Paragraph(new Phrase(breakdownR, bodyFont)));

      document.close();
    }
  }

  public void addNewlines(Document document, int lines)
      throws DocumentException {
    for (int i = 0; i < lines; i++) {
      document.add(Chunk.NEWLINE);
    }
  }

  public static PdfPTable createBreakdownTable(PhysicalRunway runway) throws DocumentException {
    PdfPTable table = new PdfPTable(5);

    PdfPCell runwayDes = cellWithPadding("Runway designator");
    runwayDes.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(runwayDes);

    PdfPCell tora = cellWithPadding("TORA");
    tora.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(tora);

    PdfPCell toda = cellWithPadding("TODA");
    toda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(toda);

    PdfPCell asda = cellWithPadding("ASDA");
    asda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(asda);

    PdfPCell lda = cellWithPadding("LDA");
    lda.setBackgroundColor(BaseColor.LIGHT_GRAY);
    table.addCell(lda);

    PdfPCell original = new PdfPCell(new Phrase("Original Values"));
    original.setColspan(5);
    original.setPadding(3);
    original.setBackgroundColor(BaseColor.ORANGE);
    original.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(original);


    LogicalRunway llogRunway = runway.getLogicalRunways().get(0);
    LogicalRunway rlogRunway = runway.getLogicalRunways().get(1);

    PdfPCell desiL = cellWithPadding(llogRunway.getDesignator());
    desiL.setHorizontalAlignment(Element.ALIGN_LEFT);
    table.addCell(desiL);
    table.addCell(cellWithPadding(llogRunway.getTora()+ "m"));
    table.addCell(cellWithPadding(llogRunway.getToda()+ "m"));
    table.addCell(cellWithPadding(llogRunway.getAsda()+ "m"));
    table.addCell(cellWithPadding(llogRunway.getLda()+ "m"));

    PdfPCell desiR = cellWithPadding(rlogRunway.getDesignator());
    desiL.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(desiR);
    table.addCell(cellWithPadding(rlogRunway.getTora()+ "m"));
    table.addCell(cellWithPadding(rlogRunway.getToda()+ "m"));
    table.addCell(cellWithPadding(rlogRunway.getAsda()+ "m"));
    table.addCell(cellWithPadding(rlogRunway.getLda()+ "m"));

    PdfPCell recalculated = new PdfPCell(new Phrase("Revised Values"));
    recalculated.setPaddingBottom(recalculated.getPaddingTop());
    recalculated.setColspan(5);
    recalculated.setPadding(3);
    recalculated.setBackgroundColor(BaseColor.ORANGE);
    recalculated.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(recalculated);

    PdfPCell desiL2 = cellWithPadding(llogRunway.getDesignator());
    desiL.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(desiL2);
    table.addCell(cellWithPadding(llogRunway.getNewTora()+ "m"));
    table.addCell(cellWithPadding(llogRunway.getNewToda()+ "m"));
    table.addCell(cellWithPadding(llogRunway.getNewAsda()+ "m"));
    table.addCell(cellWithPadding(llogRunway.getNewLda()+ "m"));

    PdfPCell desiR2 = cellWithPadding(rlogRunway.getDesignator() );
    desiL.setHorizontalAlignment(Element.ALIGN_CENTER);
    table.addCell(desiR2);
    table.addCell(cellWithPadding(rlogRunway.getNewTora()+ "m"));
    table.addCell(cellWithPadding(rlogRunway.getNewToda()+ "m"));
    table.addCell(cellWithPadding(rlogRunway.getNewAsda()+ "m"));
    table.addCell(cellWithPadding(rlogRunway.getNewLda()+ "m"));

    return table;
  }

  public static PdfPCell cellWithPadding(String text){
    Phrase p = new Phrase(text);
    Font bold  = new Font(FontFamily.UNDEFINED,12, Font.BOLD);
    p.setFont(bold);
    PdfPCell cell = new PdfPCell(p);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setPadding(10);
    return cell;
  }

}
