package barcodeCopy;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
  protected static StreamPrintServiceFactory spsf;
  protected static JLabel fechaLabel;
  protected static JPanel controls;
  protected static JFrame frame;
  
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
      
      
      
      
      JLabel copiesTextLabel = new JLabel("Numero de copias");
      JPanel copiesTextPanel = new JPanel(true);
      copiesText = new JTextField(2);
      copiesText.setDocument(new JTextFieldLimit(2));
      copiesText.setText(" 1");
      copiesTextPanel.add(copiesTextLabel);
      copiesTextPanel.add(copiesText);
  
              
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
      c3.gridy = 3;
      c3.insets = new Insets(20,0,0,0);
      GridBagConstraints c4 = new GridBagConstraints();
      c4.gridx = 2;
      c4.gridy = 3;
      c4.insets = new Insets(20,0,0,0);
      GridBagConstraints c5 = new GridBagConstraints();
      c5.gridx = 1;
      c5.gridy = 2;
      c5.gridwidth = 2;
      c5.anchor = GridBagConstraints.LINE_START;
      c5.insets = new Insets(10, 10, 10, 10);
      controls.add(idTextPanel, c);
      controls.add(copiesTextPanel, c2);
      controls.add(jbuttonColsePanel, c3);
      controls.add(jbuttonPrintPanel, c4);
      controls.add(labPanel, c5);
      
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
      } else if ("Print".equals(e.getActionCommand())){
         
          
          String id = this.idPane.getText().trim();
          String[] ids;
          if (id.contains("\n"))
              ids = id.split("\n");  
          else {
              ids = new String[1];
              ids[0] = id;
          }    
          String numCopies = this.copiesText.getText();
        
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
                      throw new RuntimeException("Falta numero de copias.");
              for (int j=0; j<ids.length; j++){
                  if (!(ids[j].charAt(0) == 'M' || ids[j].charAt(0) == 'C' || ids[j].charAt(0) == 'I'))
                      throw new RuntimeException("El ID " + id + " tiene que empezar con I,C, o M.");
                  if (ids[j].charAt(8) != '-' || ids[j].length() != 10)
                      throw new RuntimeException("El ID " + id + " no tiene un formato correcto.");
                  if (!ids[j].toUpperCase().equals(ids[j]))
                      throw new RuntimeException("El ID " + id + " no puede contener letras minusculas.");
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
                          
                          PrinterJob pj = PrinterJob.getPrinterJob();
                          pj.setPrintService(psZebra);
                          PageFormat pf = pj.defaultPage();
                          
                          //we can use these if we need these to set heights according to paper height
                          Paper paper = pf.getPaper(); 
                          double height = pf.getHeight();
                          double width = pf.getWidth(); 
                          

                          String labStuff = "";
                          if (checkbox.isSelected()){
                              
                              //TODO: validate fecha    
                              
                              SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                              try {
                                  Date date = sdf.parse(fecha.getText());
                                  fecha.setText(sdf.format(date));
                              } catch (Exception ex) {
                                  throw new RuntimeException("El formato de la fecha no es correcta. Por favor use 'dd/MM/yy'");
                              }
                              
//                            Fecha
//                            C R I S P E
//                            3 5 6                              
                              
                              labStuff += "\015\012A30,90,0,3,1,1,N,\"" + fecha.getText() + "\"\015\012";
                              labStuff += "\015\012A30,113,0,2,1,1,N,\"C R I S P E\"\015\012";
                              labStuff += "\015\012A30,133,0,2,1,1,N,\"3 5 6\"\015\012";
//                              labStuff += "\015\012A30,153,0,2,1,1,N,\"CC RR II S E P\"\015\012";
//                              labStuff += "\015\012A30,173,0,2,1,1,N,\"55 55 55 5 5\"\015\012";
//                              labStuff += "\015\012A30,193,0,2,1,1,N,\"CC\"\015\012";
//                              labStuff += "\015\012A30,213,0,2,1,1,N,\"66\"\015\012";
                              
                          }
                          
                          StringBuffer myString = new StringBuffer("N\015\012" + "B30,20,0,1,1,6,40,B,\"" + id + "\"" + labStuff + "\015\012P" + numCopiesInt.intValue() + "\015\012");
                          //StringBuffer myString = new StringBuffer("N\015\012" + labStuff + "\015\012P" + numCopiesInt.intValue() + "\015\012"); 
                          //System.out.println("myString" + myString);
                          DocFlavor flavor2 = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                          
                          Doc doc2 = new SimpleDoc(myString.toString().getBytes(), flavor2, null);
                   
                          
                          DocPrintJob job = psZebra.createPrintJob();
                          PrintJobWatcher pjw  = new PrintJobWatcher(job);
                         job.print(doc2, null);
                          //System.out.println("PrintJobComplete");
                         pjw.waitForDone();
                          //I was using this as a proxy for sending the print job to the printer:
                          //throw new RuntimeException(String.valueOf(id));
                          
                          
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

}