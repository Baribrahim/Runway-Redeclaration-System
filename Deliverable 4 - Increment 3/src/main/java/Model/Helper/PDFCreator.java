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

  public PDFCreator(Airport a, Obstacle o, PhysicalRunway r, Node topView, Node sideView, Node simulView) throws IOException, DocumentException {
    takeSnapShots(topView, sideView, simulView);
    createPdf(a, o, r);
  }

  private void createVisual(Node contentNode, File file) throws IOException {
    SnapshotParameters params = new SnapshotParameters();
    params.setTransform(Transform.scale(2, 2)); // Set the device pixel ratio to 2x
    params.setFill(Color.TRANSPARENT);

    double width = contentNode.getBoundsInLocal().getWidth();
    double height = contentNode.getBoundsInLocal().getHeight();

    WritableImage snapshot = new WritableImage((int) (width * 2), (int) (height * 2));
    snapshot = contentNode.snapshot(params, snapshot);

    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
    ImageIO.write(bufferedImage, "png", file);
  }

  private void takeSnapShots(Node topNode, Node sideNode, Node simulNode) throws IOException {
    File file1 = new File(String.valueOf(Objects.requireNonNull(getClass().getResource("/Printer/SideView.png")).getPath()));
    File file2 = new File(String.valueOf(Objects.requireNonNull(getClass().getResource("/Printer/TopView.png")).getPath()));
    File file3 = new File(String.valueOf(Objects.requireNonNull(getClass().getResource("/Printer/SimulView.png")).getPath()));
    createFile(file1);
    createFile(file2);
    createFile(file3);
    createVisual(sideNode, file1);
    createVisual(topNode, file2);
    createVisual(simulNode, file3);
  }

  private void createFile(File file) throws IOException {
    if(!file.getParentFile().exists()){
      file.getParentFile().mkdirs();
    }
    file.createNewFile();
  }


  public void createPdf(Airport a, Obstacle o, PhysicalRunway r) throws DocumentException, IOException {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Generate Regeneration Report");

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
//            header.setWidths(new int[] { 140, 25 });
      header.setSpacingAfter(10);
      header.getDefaultCell().setBorder(0);
      header.addCell(new Paragraph("Calculation report", headerFont));
      document.add(header);

      addNewlines(document, 1);

//      document.add(new Paragraph("Generated by "+ Main.getName()+" on "+Utility.getDateTimeNow(), bodyFont));


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
      PdfPTable table = createDeclarationTable(r);
      table.setHorizontalAlignment(Element.ALIGN_LEFT);
      document.add(table);

      document.newPage();
      String breakdownL = ParameterCalculator.convertCalcBreakdown(o, r.getLogicalRunways().get(0));
      String flightMethodL = ParameterCalculator.getFlightMethod(o, r.getLogicalRunways().get(0));
      String breakdownR = ParameterCalculator.convertCalcBreakdown(o, r.getLogicalRunways().get(1));
      String flightMethodR = ParameterCalculator.getFlightMethod(o, r.getLogicalRunways().get(1));


      document.add(new Paragraph("Re-declaration breakdown", subTitleFont));
      addNewlines(document, 1);
      document.add(new Paragraph(new Phrase(r.getLogicalRunways().get(0).getDesignator()+" "+flightMethodL, subTitleItalic)));
      document.add(new Paragraph(new Phrase(breakdownL, bodyFont)));
      document.add(new Paragraph(new Phrase(r.getLogicalRunways().get(1).getDesignator()+" "+flightMethodR, subTitleItalic)));
      document.add(new Paragraph(new Phrase(breakdownR, bodyFont)));


      document.newPage();
      document.add(new Paragraph("Visualisations", subTitleFont));

      addNewlines(document, 1);

      PdfPTable t = new PdfPTable(1);
      t.getDefaultCell().setBorder(0);
      t.addCell(createImageCell("/Printer/SideView.png", 0.3f));

      PdfPTable t2 = new PdfPTable(1);
      t2.getDefaultCell().setBorder(0);
      t2.addCell(createImageCell("/Printer/TopView.png", 0.3f));

      PdfPTable t3 = new PdfPTable(1);
      t3.addCell(createImageCell("/Printer/SimulView.png", 0.3f));

      document.add(new Paragraph(new Phrase("Side-On Visualisation", subTitleFontSmall)));
      addNewlines(document, 1);
      document.add(t);

      document.newPage();
      document.add(new Paragraph(new Phrase("Top-Down Visualisation", subTitleFontSmall)));
      addNewlines(document, 1);
      document.add(t2);

      document.newPage();
      document.add(new Paragraph("Simultaneous Visualisation", subTitleFontSmall));
      addNewlines(document, 1);
      document.add(t3);

      document.close();

//      new Notification(Main.getStage()).sucessNotification("Successful generation", "Report downloaded to "+selectedDirectory);
    }
  }


  public void addNewlines(Document document, int lines)
      throws DocumentException {
    for (int i = 0; i < lines; i++) {
      document.add(Chunk.NEWLINE);
    }
  }



  private PdfPTable breakdownTable(LogicalRunway runway, PhysicalRunway r, Map direction) throws DocumentException {
    PdfPTable breakdown1 = new PdfPTable(3);
    breakdown1.setHorizontalAlignment(0);
    breakdown1.setSpacingBefore(10);
    breakdown1.setSpacingAfter(10);
    breakdown1.getDefaultCell().setBorder(0);
    breakdown1.setWidths(new float[] { 1.2f,0.4f, 12});

    breakdown1.addCell(new Phrase("TORA", subTitleFontSmall));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("TORA").toString(), bodyFont));
    breakdown1.addCell((""));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("TORA1").toString(), bodyFont));
    breakdown1.addCell((""));
    breakdown1.addCell("=");
    breakdown1.addCell(new Phrase(direction.get("TORA2").toString(), resultFont));

    PdfPCell line = new PdfPCell(new Phrase(" "));
    line.getPhrase().getFont().setSize(4);
    line.setBorderColor(BaseColor.WHITE);
    line.setColspan(3);
    breakdown1.addCell(line);

    breakdown1.addCell(new Phrase("ASDA", subTitleFontSmall));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("ASDA").toString(), bodyFont));
    breakdown1.addCell(new Phrase("", bodyFont));
    breakdown1.addCell(new Phrase("="));
    breakdown1.addCell(new Phrase(direction.get("ASDA1").toString(), resultFont));

    breakdown1.addCell(line);


    breakdown1.addCell(new Phrase("TODA", subTitleFontSmall));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("TODA").toString(), bodyFont));
    breakdown1.addCell("");
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("TODA1").toString(), resultFont));

    breakdown1.addCell(line);

    breakdown1.addCell(new Phrase("LDA", subTitleFontSmall));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("LDA").toString(),bodyFont));
    breakdown1.addCell((""));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("LDA1").toString(), bodyFont));
    breakdown1.addCell((""));
    breakdown1.addCell(new Phrase("=", bodyFont));
    breakdown1.addCell(new Phrase(direction.get("LDA2").toString(), resultFont));

    return breakdown1;
  }

  public static PdfPTable createDeclarationTable(PhysicalRunway runway) throws DocumentException {
    PdfPTable table = new PdfPTable(5);
//        table.setWidths(new float[] { 0.7f, 0.7f, 0.7f, 0.7f, 0.7f });

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

    PdfPCell recalculated = new PdfPCell(new Phrase("Recalculated Values"));
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

  public PdfPCell createImageCell(String path, float scale) throws DocumentException, IOException {
    Image img = createImage(path);
    img.scaleAbsolute(img.getPlainWidth()*scale, img.getPlainHeight()*scale);
    PdfPCell cell = new PdfPCell(img, true);
    cell.setBorder(0);
    return cell;
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

  private Image createImage(String path) throws IOException, BadElementException {
    InputStream inputStream = Objects.requireNonNull(getClass().getResourceAsStream(path));
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int length;
    while ((length = inputStream.read(buffer)) != -1) {
      baos.write(buffer, 0, length);
    }
    byte[] imageData = baos.toByteArray();

// Use ImageIO to read the input stream and determine the format of the image
    ImageInputStream imageInputStream = ImageIO.createImageInputStream(new ByteArrayInputStream(imageData));
    Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);
    if (!readers.hasNext()) {
      throw new IllegalArgumentException("Unsupported image format");
    }
    ImageReader reader = readers.next();
    reader.setInput(imageInputStream);

// Create an image object from the byte array
    return Image.getInstance(imageData);
  }

}
