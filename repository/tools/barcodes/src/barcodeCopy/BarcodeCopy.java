package barcodeCopy;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BarcodeCopy extends JPanel implements ActionListener


{
    
    
  final static long serialVersionUID = 23497343;  
  protected static JButton jbuttonClose;
  protected static JButton jbuttonPrint;
  protected static JTextField idText;
  protected static JTextField copiesText;
  protected static JComboBox type;
  private int[] messageTypes ={JOptionPane.PLAIN_MESSAGE};
  
  protected static JFrame myWindow;
  private String newline = "\n";
  GridLayout experimentLayout = new GridLayout(0,2);



  
  
  public BarcodeCopy()
  {
    
    
      

      JLabel idTextLabel = new JLabel("ID");
      JPanel idTextPanel = new JPanel();
      idText = new JTextField(8);
      idText.setDocument(new JTextFieldLimit(8));
      idTextPanel.add(idTextLabel);
      idTextPanel.add(idText);
      
      JLabel copiesTextLabel = new JLabel("Numero de copias");
      JPanel copiesTextPanel = new JPanel();
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
      jbuttonPrint.setActionCommand("Print");
      jbuttonPrint.setMnemonic(KeyEvent.VK_P);
      //add(jbuttonPrint);
      jbuttonPrint.addActionListener(this);
      jbuttonPrintPanel.add(jbuttonPrint);
      
      
      
      JLabel idTipoLabel = new JLabel("Tipo");
      JPanel tippPanel = new JPanel();
      String[] petStrings = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
      type = new JComboBox(petStrings);
      type.setSelectedIndex(4);
      type.addActionListener(this);
      tippPanel.add(idTipoLabel);
      tippPanel.add(type);

      JPanel controls = new JPanel();
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
      controls.add(tippPanel, c5);
      controls.add(idTextPanel, c);
      controls.add(copiesTextPanel, c2);
      controls.add(jbuttonColsePanel, c3);
      controls.add(jbuttonPrintPanel, c4);
      
      add(controls);
      //set cursor in ID field:
      idTextPanel.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
    
  }
  
  
  private static void createAndShowGUI() {

      //Create and set up the window.
      JFrame frame = new JFrame("Codigo De Barras");
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
          
          //HERE's a basic message box:
          JLabel  message = new JLabel("Message to appear inside of dialog box");
          JOptionPane.showMessageDialog(this, message, "test", 0);
          
      }
  } 

    
}