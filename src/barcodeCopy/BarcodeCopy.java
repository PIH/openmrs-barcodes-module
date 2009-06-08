package barcodeCopy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.StreamPrintServiceFactory;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterName;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class BarcodeCopy extends JPanel implements ActionListener


{
    
    
  final static long serialVersionUID = 23497343;  
  protected static JButton jbuttonClose;
  protected static JButton jbuttonPrint;
  protected static JTextField fecha;
  protected static JTextField copiesText;
  protected static JCheckBox checkbox;
  protected static JPopupMenu rightClickMenu;
  protected static StreamPrintServiceFactory spsf;
  protected static JLabel fechaLabel;
  protected static JPanel controls;
  protected static JFrame frame;
 
  
  //for sticker offsets
  protected static JLabel advancedLabel;
  protected static JTextField advancedText;
  protected static JLabel advancedHeight;
  protected static JTextField advancedHeightText;
  
  protected static JFrame myWindow;
  GridLayout experimentLayout = new GridLayout(0,2);

  //TODO
  protected static JTextArea idPane;


  
  
  public BarcodeCopy()
  {
      JLabel idTextLabel = new JLabel("IDs");
      JPanel idTextPanel = new JPanel();
      idTextPanel.add(idTextLabel);

      idPane = new JTextArea();
      idPane.setLineWrap(true);
      idPane.setFont(new Font("Courier New", Font.PLAIN, 12));
      JScrollPane scrollPane = new JScrollPane(idPane);
      scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      JViewport viewport = scrollPane.getViewport();
      //TODO:   we may need to do better with sizing after each ID
      viewport.setPreferredSize(new Dimension(90,90));
      idTextPanel.add(scrollPane);
      viewport.revalidate();
      viewport.updateUI();
       
      
      rightClickMenu = new JPopupMenu();
      rightClickMenu.setBorderPainted(false);
      rightClickMenu.setBackground(Color.LIGHT_GRAY);
      rightClickMenu.setBorder(null);
      JMenuItem pasteClipboard = new JMenuItem("Pegar");
      pasteClipboard.setBorderPainted(false);
      pasteClipboard.addActionListener(this);
      rightClickMenu.add(pasteClipboard);
      MouseListener mouseListener = new MouseAdapter() {
          private void showIfPopupTrigger(MouseEvent mouseEvent) {
            if (mouseEvent.isPopupTrigger()) {
                rightClickMenu.show(mouseEvent.getComponent(),
                  mouseEvent.getX(),
                  mouseEvent.getY());
            }
          }
          public void mousePressed(MouseEvent mouseEvent) {
            showIfPopupTrigger(mouseEvent);
          }
          public void mouseReleased(MouseEvent mouseEvent) {
            showIfPopupTrigger(mouseEvent);
          }
        };
        idPane.addMouseListener (mouseListener);
        idPane.setSize(350, 250);
        idPane.setVisible(true);



      JLabel copiesTextLabel = new JLabel("Numero de filas");
      JPanel copiesTextPanel = new JPanel(true);
      copiesText = new JTextField(2);
      copiesText.setDocument(new JTextFieldLimit(2));
      copiesText.setText(" 1");
      copiesTextPanel.add(copiesTextLabel);
      copiesTextPanel.add(copiesText);
  
      //protected static JLabel advancedLabel;
      //protected static JTextField advancedText;
      JPanel advancedPanel = new JPanel(true);
      advancedLabel = new JLabel("offset dpi (opcional, predeterminado = 268)");
      advancedLabel.setForeground(Color.GRAY);
      advancedText = new JTextField(3);
      advancedText.setDocument(new JTextFieldLimit(3));
      advancedText.setForeground(Color.GRAY);
      advancedPanel.add(advancedLabel);
      advancedPanel.add(advancedText);
      
      JPanel advancedHeightPanel = new JPanel(true);
      advancedHeight = new JLabel("altura offset dpi (opcional, predeterminado = 20)");
      advancedHeight.setForeground(Color.GRAY);
      advancedHeightText = new JTextField(2);
      advancedHeightText.setDocument(new JTextFieldLimit(2));
      advancedHeightText.setForeground(Color.GRAY);
      advancedHeightPanel.add(advancedHeight);
      advancedHeightPanel.add(advancedHeightText);
    
      
      JPanel jbuttonColsePanel = new JPanel();
      jbuttonClose = new JButton("Cerrar");
      jbuttonClose.setActionCommand("Close");
      jbuttonClose.setMnemonic(KeyEvent.VK_C);
      jbuttonClose.addActionListener(this);
      jbuttonColsePanel.add(jbuttonClose);
      
      
      JPanel jbuttonPrintPanel = new JPanel();
      jbuttonPrint = new JButton("Imprimir");
      jbuttonPrint.setMnemonic(KeyEvent.VK_P);
      //add(jbuttonPrint);
      jbuttonPrint.addActionListener(this);
      jbuttonPrint.setActionCommand("Print");
      jbuttonPrintPanel.add(jbuttonPrint);
     

    //Here's the laboratory part:
      JPanel labPanel = new JPanel();
      labPanel.setLayout(new GridBagLayout());
      checkbox = new JCheckBox("", false);
      checkbox.addActionListener(this);
      checkbox.setActionCommand("laboratorio");
      GridBagConstraints clab = new GridBagConstraints();
      clab.gridx = 1;
      clab.gridy = 1;
      clab.anchor = GridBagConstraints.LINE_START;
      clab.insets = new Insets(0, 10, 0, 10);
      GridBagConstraints clab2 = new GridBagConstraints();
      clab2.gridx = 2;
      clab2.gridy = 1;
      clab2.anchor = GridBagConstraints.LINE_START;
      clab2.insets = new Insets(0, 10, 0, 10);
      labPanel.add(new JLabel("Formato Laboratorio"), clab);
      labPanel.add(checkbox, clab2);
      GridBagConstraints clab3 = new GridBagConstraints();
      clab3.gridx = 1;
      clab3.gridy = 2;
      clab3.anchor = GridBagConstraints.LINE_START;
      clab3.insets = new Insets(0, 10, 0, 10);
      fechaLabel = new JLabel("Fecha (dd/MM/yy)");
      fechaLabel.setVisible(false);
      labPanel.add(fechaLabel, clab3);
      //add the fecha
      fecha = new JTextField(8);
      fecha.setDocument(new JTextFieldLimit(8));
      GridBagConstraints clab4 = new GridBagConstraints();
      clab4.gridx = 2;
      clab4.gridy = 2;
      clab4.anchor = GridBagConstraints.LINE_START;
      clab4.insets = new Insets(0, 10, 0, 10);
      fecha.setVisible(false);
      labPanel.add(fecha, clab4);
      

      controls = new JPanel();
      controls.setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.gridx = 1;
      c.gridy = 1;
      c.anchor = GridBagConstraints.LINE_START;
      GridBagConstraints c2 = new GridBagConstraints();
      c2.gridx = 2;
      c2.gridy = 1;
      c2.anchor = GridBagConstraints.LINE_START;
      GridBagConstraints c3 = new GridBagConstraints();
      c3.gridx = 1;
      c3.gridy = 5;
      c3.insets = new Insets(20,0,0,0);
      GridBagConstraints c4 = new GridBagConstraints();
      c4.gridx = 2;
      c4.gridy = 5;
      c4.insets = new Insets(20,0,0,0);
      GridBagConstraints c5 = new GridBagConstraints();
      c5.gridx = 1;
      c5.gridy = 2;
      c5.gridwidth = 2;
      c5.anchor = GridBagConstraints.LINE_START;
      c5.insets = new Insets(10, 10, 10, 10);
      GridBagConstraints c6 = new GridBagConstraints();
      c6.gridx = 1;
      c6.gridy = 3;
      c6.gridwidth = 2;
      c6.anchor = GridBagConstraints.LINE_START;
      c6.insets = new Insets(0, 20, 0, 0);
      GridBagConstraints c7 = new GridBagConstraints();
      c7.gridx = 1;
      c7.gridy = 4;
      c7.gridwidth = 2;
      c7.anchor = GridBagConstraints.LINE_START;
      c7.insets = new Insets(0, 20, 0, 0);
      
      //advancedHeightPanel
      
      controls.add(idTextPanel, c);
      controls.add(copiesTextPanel, c2);
      controls.add(jbuttonColsePanel, c3);
      controls.add(jbuttonPrintPanel, c4);
      controls.add(labPanel, c5);
      controls.add(advancedPanel, c6);
      controls.add(advancedHeightPanel, c7);
      
      
      add(controls);
      //set cursor in ID field:
      idTextPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    
  }
  
  
  private static void createAndShowGUI() {

      //Create and set up the window.
      frame = new JFrame("Codigo De Barras");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      WindowDestroyer myListener = new WindowDestroyer();
      frame.addWindowListener(myListener);
      //Create and set up the content pane.
      BarcodeCopy newContentPane = new BarcodeCopy();
      newContentPane.setOpaque(true); //content panes must be opaque
      frame.setContentPane(newContentPane);
      frame.pack();
      //Display the window.
      frame.setLocation(new Point(200,200));
      frame.setVisible(true);
     
  }

  public static void main(String[] args) {
      //Schedule a job for the event-dispatching thread:
      //creating and showing this application's GUI.
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
          public void run() {
              createAndShowGUI(); 
          }
      });
  }

  
  public void actionPerformed(ActionEvent e) {
      if ("Close".equals(e.getActionCommand())) {
          System.exit(0);
      } else if ("Pegar".equals(e.getActionCommand()))  {
              idPane.setText(getClipboardContents());
      } else if ("Print".equals(e.getActionCommand())){
         
          
          String id = this.idPane.getText().trim();
          String[] ids;
          if (id.contains("\n"))
              ids = id.split("\n");  
          else {
              ids = new String[1];
              ids[0] = id;
          }    
          String numCopies = this.copiesText.getText().trim();
          String offset = this.advancedText.getText().trim();
          String heightString = this.advancedHeightText.getText().trim();
          PrintService psZebra = null;
          try{
            //GET PRINTER             
              String sPrinterName = null;
              PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
              for (int i = 0; i < services.length; i++) {
                  PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
                  sPrinterName = ((PrinterName)attr).getValue();
                  if (sPrinterName.toLowerCase().indexOf("zebra") >= 0) {
                      psZebra = services[i];
                      break;
                  }
              }
              if (psZebra == null) {
                  System.out.println("Zebra printer is not found.");
                  return;
              }  
          } catch (Exception eFindPrintService){
              HandleError(eFindPrintService);
          }
          
          
//               //GET ALL DOCTYPES SUPPORTED BY PRINTER
//              DocFlavor[] flavors = psZebra.getSupportedDocFlavors();
//              for (int i = 0; i <flavors.length; i++){
//                  System.out.println(flavors[i].getMimeType() + " " + flavors[i].getRepresentationClassName());
//              }
              

//            //Get all supported attributes  
//            Attribute[] ats = psZebra.getAttributes().toArray();
//            for (int i = 0; i <ats.length; i++){
//                System.out.println(ats[i].getName());
//            }
          
            
            
            
            
//            System.out.println("PRINTER DIMENSIONS height " + height + " WIDTH " + width);
//            System.out.println("PRINTER IMAGEABLE DIMENSIONS height " + pf.getImageableHeight() + " width " + pf.getImageableWidth());
            
              

                              
                  //HERE's how you make a barcode
                  
//                  Code39Bean bean = new Code39Bean();
//                  final int dpi = 150;
//                  bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
//                  bean.setWideFactor(3);
//                  bean.doQuietZone(true);
//                  //bean.setHeight(height - (0.2*height));
//
//                  //File outputFile = new File("out.jpg");
//                  //OutputStream out = new FileOutputStream(outputFile);
//                  //BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//                  //bean.generateBarcode(canvas,id);
//                  //canvas.finish();
//                  
//                  //BarcodeDimension bd = canvas.getDimensions();
//                  //paper.setImageableArea(this.getX(), this.getY(), bd.getWidthPlusQuiet(), bd.getHeightPlusQuiet());
//                  //System.out.println("BARCODE DIMENSIONS height " + bd.getHeightPlusQuiet() + " width " + bd.getWidthPlusQuiet());
//                  
//                  File outputFile = new File("testDT.txt");
//                  OutputStream outStr = new FileOutputStream(outputFile);
//                  String cmd = "N"+ "\015\012" + "B10,10,0,3,3,7,200,B,\"998152-001\""+ "\015\012" + "PA\015\012" ;
//                  outStr.write(cmd.getBytes());
//                  outStr.close();
//                  
//                  DocFlavor flavor = new DocFlavor("image/jpeg","java.io.InputStream");
//                  Doc doc = new SimpleDoc(new FileInputStream(outputFile.getAbsoluteFile()), flavor, null);
                  
          
          //HERE:  validate all IDs
          boolean runPrintJob = true;
          try{
             
              if (psZebra == null)
                  throw new RuntimeException("No puede encontrar al impresora de codigo de barras.");
              if (id == null || id.equals(""))
                  throw new RuntimeException("Falta un ID.");
              if (numCopies == null || numCopies.equals(""))
                      throw new RuntimeException("Falta numero de filas.");
              else {
                  if (!isNumeric(numCopies))
                      throw new RuntimeException("Numero de filas no es un numero.");
              }
              if (offset != null && !offset.trim().equals("") && !isNumeric(offset)){
                      throw new RuntimeException("offset dpi no es un numero.");
              }
              if (heightString != null && !heightString.equals("") && !isNumeric(heightString)){
                      throw new RuntimeException("altura offset dpi no es un numero");
              }
              for (int j=0; j<ids.length; j++){
                  if (!(ids[j].charAt(0) == 'P' || ids[j].charAt(0) == 'M' || ids[j].charAt(0) == 'C' || ids[j].charAt(0) == 'I' || ids[j].charAt(0) == 'A' || ids[j].charAt(0) == 'N'))
                      throw new RuntimeException("El ID " + id + " tiene que empezar con I,C,A,N,P, o M.");
                  if (ids[j].charAt(0) != 'P' && (ids[j].charAt(8) != '-' || ids[j].length() != 10))
                          throw new RuntimeException("El ID " + id + " no tiene un formato correcto.");
                  if (ids[j].charAt(0) == 'P' && (ids[j].charAt(6) != '-' || ids[j].length() != 8))
                      throw new RuntimeException("El ID " + id + " no tiene un formato correcto por PPD.");
              }   
          
          } catch (Exception ex){
              HandleError(ex); 
              runPrintJob = false;
          }
                  
          if (runPrintJob){
                for (int j=0; j<ids.length; j++){
                      id = ids[j];
                      try {
                          
                          Integer numCopiesInt = Integer.valueOf(numCopies.trim());
                          Integer offsetInt = null;
                          Integer heightInt  = null;
                          if (offset != null && !offset.equals(""))
                              offsetInt  = Integer.valueOf(offset.trim());
                          if (heightString != null && !heightString.equals(""))
                              heightInt  = Integer.valueOf(heightString);

                          PrinterJob pj = PrinterJob.getPrinterJob();
                          pj.setPrintService(psZebra);

                          String labStuff = "";
                          //TODO:  create UI for pixelBufferToNextSticker?   268 is the dif for the regular stickers for the epi study
                          int p1Pos = 40;
                          
                          int pixelBufferToNextSticker = 268; 
                          if (offsetInt != null)
                              pixelBufferToNextSticker = offsetInt.intValue(); 
                          
                          int verticalOffset = 20;
                          if (heightInt != null)
                              verticalOffset = heightInt.intValue();
                          

                          if (checkbox.isSelected()){

                              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                              if (fecha.getText() != null && !fecha.getText().equals("")){
                                  try {
                                      Date date = sdf.parse(fecha.getText());
                                      fecha.setText(sdf.format(date));
                                  } catch (Exception ex) {
                                      throw new RuntimeException("El formato de la fecha no es correcta. Por favor use 'dd/MM/yy'");
                                  }
                              }                           
                              
                              
                              int dateRowHeigt = verticalOffset + 68;
                              int cripseRowHeight = verticalOffset + 87;
                              int three65Row = verticalOffset + 106;
                              
                              //dateRow
                              labStuff += "\015\012A" + String.valueOf(p1Pos) + "," + dateRowHeigt + ",0,2,1,1,N,\"" + fecha.getText() + "\"\015\012";
                              labStuff += "\015\012A" + String.valueOf(p1Pos + pixelBufferToNextSticker) + "," + dateRowHeigt + ",0,2,1,1,N,\"" + fecha.getText() + "\"\015\012";
                              labStuff += "\015\012A" + String.valueOf(p1Pos + (pixelBufferToNextSticker*2)) + "," + dateRowHeigt + ",0,2,1,1,N,\"" + fecha.getText() + "\"\015\012";
                              
                              //CRISPE row
                              labStuff += "\015\012A" + String.valueOf(p1Pos) + "," + cripseRowHeight + ",0,2,1,1,N,\"C R I S P E\"\015\012";
                              labStuff += "\015\012A" + String.valueOf(p1Pos + pixelBufferToNextSticker) + "," + cripseRowHeight + ",0,2,1,1,N,\"C R I S P E\"\015\012";
                              labStuff += "\015\012A" + String.valueOf(p1Pos + (pixelBufferToNextSticker*2)) + "," + cripseRowHeight + ",0,2,1,1,N,\"C R I S P E\"\015\012";
                              
                              //356 row
                              labStuff += "\015\012A" + String.valueOf(p1Pos) + "," + three65Row + ",0,2,1,1,N,\"3 5 6\"\015\012";
                              labStuff += "\015\012A" + String.valueOf(p1Pos + pixelBufferToNextSticker) + "," + three65Row + ",0,2,1,1,N,\"3 5 6\"\015\012";
                              labStuff += "\015\012A" + String.valueOf(p1Pos + (pixelBufferToNextSticker*2)) + "," + three65Row + ",0,2,1,1,N,\"3 5 6\"\015\012";

                              
                          }                  
                          
                          //Barcode Row
                          String createBarcodeString = "B" + String.valueOf(p1Pos) + "," + verticalOffset + ",0,1,1,6,40,B,\"" + id + "\"";
                          createBarcodeString += "\015\012B" + String.valueOf(p1Pos + pixelBufferToNextSticker) + "," + verticalOffset + ",0,1,1,6,40,B,\"" + id + "\"";
                          createBarcodeString += "\015\012B" + String.valueOf(p1Pos + (pixelBufferToNextSticker*2)) + "," + verticalOffset + ",0,1,1,6,40,B,\"" + id + "\"";
                          
                          
                          StringBuffer myString = new StringBuffer("N\015\012" + createBarcodeString + labStuff + "\015\012P" + numCopiesInt.intValue() + "\015\012");
                          
                          DocFlavor flavor2 = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                          Doc doc2 = new SimpleDoc(myString.toString().getBytes(), flavor2, null);
                   
                          //log EPL message:
                          //System.out.println(myString.toString());
                          
                          DocPrintJob job = psZebra.createPrintJob();
                          PrintJobWatcher pjw  = new PrintJobWatcher(job);
                          job.print(doc2, null);
                          //System.out.println("PrintJobComplete");
                          pjw.waitForDone();
                          
                          
                     } catch (PrintException ex) {
                        HandleError(ex);
                        break;
                      } catch (Exception ex){
                          HandleError(ex);
                      }
                } 
          }
      } else if ("laboratorio".equals(e.getActionCommand())){
          try {
              if (checkbox.isSelected()){
                  fecha.setVisible(true);
                  fechaLabel.setVisible(true);
                  frame.pack();
              }
                  
              if (!checkbox.isSelected()){
                  fecha.setVisible(false);
                  fechaLabel.setVisible(false);
                  frame.pack();
              }
                  
              
          } catch (Exception ex){
              HandleError(ex);
          }
      }
    
  } 
  
  private void HandleError(Exception ex){
      System.out.println("Error " + ex);
      System.out.println(ex.getStackTrace());
      ex.printStackTrace();
      JLabel  message = new JLabel(ex.getLocalizedMessage());
      JOptionPane.showMessageDialog(this, message, "test", 0);
  }
  
  public String getClipboardContents() {
      String result = "";
      Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      //odd: the Object param of getContents is not currently used
      Transferable contents = clipboard.getContents(null);
      boolean hasTransferableText =
        (contents != null) &&
        contents.isDataFlavorSupported(DataFlavor.stringFlavor)
      ;
      if ( hasTransferableText ) {
        try {
          result = (String)contents.getTransferData(DataFlavor.stringFlavor);
        }
        catch (UnsupportedFlavorException ex){
          //highly unlikely since we are using a standard DataFlavor
          System.out.println(ex);
          ex.printStackTrace();
        }
        catch (IOException ex) {
          System.out.println(ex);
          ex.printStackTrace();
        }
      }
      return result;
    }
  
    private boolean isNumeric(String input){
        try {
            Integer myInteger = Integer.valueOf(input.trim());
            return true;
        } catch (Exception ex){
            return false;
        }
    }

}