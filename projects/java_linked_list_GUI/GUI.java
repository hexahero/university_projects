import java.io.*;
import java.security.SecureRandom;
import java.math.BigInteger;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

class InputBlock {

    int labelX = 310;
    int labelY = 10;
    int textFieldX = 100;
    int textFieldY = 30;
    int buttonX = 200;
    int buttonY = 30;
    
    private JLabel label;
    private JTextField textField;
    private JButton button;

    public InputBlock(String labelText, String textFieldText, String buttonText) {

        label = new JLabel(labelText);
        textField = new JTextField(textFieldText);
        button = new JButton(buttonText);

    }

    public InputBlock(String labelText, String buttonText) {

        label = new JLabel(labelText);
        textField = new JTextField();
        button = new JButton(buttonText);

    }

    public void configure(int posX, int posY, boolean focusable, JPanel panel) {

        label.setBounds(posX, posY, labelX, labelY);
        textField.setBounds(posX, posY + 20, textFieldX, textFieldY);
        button.setBounds(posX + textFieldX + 10, posY + 20, buttonX, buttonY);
        button.setFocusable(focusable);
        attach_to(panel);

    }
    
    public void attach_to(JPanel panel) {

        panel.add(label);
        panel.add(textField);
        panel.add(button);

    }

    public int get_text_field_value() {
        
        int value;

        try { 
            
            value = Integer.parseInt(textField.getText()); 
        }
        catch (NumberFormatException nfe) {

            JOptionPane.showMessageDialog(null, "Incorrect numeric value", "Error", JOptionPane.ERROR_MESSAGE);
            throw new Error("Incorrect numeric value");
        }
        
        return value;
    }

    public String get_text_field_text() {

        return textField.getText();
    }

    public JLabel get_label() {

        return label;
    }

    public JTextField get_textField() {

        return textField;
    }

    public JButton get_button() {

        return button;
    }

}

public class GUI {
    
    //Declare components
    private JFrame frame;

    private JPanel listOpsPanel;
    private JPanel outputPanel;
    private JPanel toolPanel;
    
    private InputBlock genDataBlock;
    private InputBlock pushBackBlock;
    private InputBlock pushOrderedBlock;
    private InputBlock getElementBlock;
    private InputBlock setElementBlock;
    private InputBlock insertBlock;
    private InputBlock removeBlock;

    private JButton clearOutputButton;
    private JButton printDataButton;
    private JButton popBackButton;
    private JButton getListSizeButton;
    private JButton sortListButton;

    private JButton importDataButton;
    private JButton exportDataButton;
    
    private JTextArea outputTextArea;
    private JScrollPane outputTextAreaScrollPane;

    //Declare panel properties
    int panelX = 350;
    int panelY = 500;

    //Declare data list
    List<String> Data = new List<>();

    public GUI() {

        //Construct components
        frame = new JFrame();

        outputTextArea = new JTextArea();
        outputTextAreaScrollPane = new JScrollPane(outputTextArea);
        
        //Setup panels
        setup_panels();

        //Setup input blocks
        setup_input_blocks();

        //Configure outputTextArea
        outputTextArea.setEditable(false);
        outputTextArea.setFont(outputTextArea.getFont().deriveFont(14f));

        //Configure outputTextAreaScrollPane
        outputTextAreaScrollPane.setVerticalScrollBarPolicy(outputTextAreaScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        //Add components to outputPanel
        outputPanel.add(outputTextAreaScrollPane, BorderLayout.CENTER);

        //Configure frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setTitle("List Window App");
        frame.setBounds(300, 300, 715, 670);
        frame.setVisible(true);

        //Add panels to the frame
        frame.add(listOpsPanel);
        frame.add(outputPanel);
        frame.add(toolPanel);

    }

    private void setup_panels() {
        
        listOpsPanel = new JPanel();
        outputPanel = new JPanel();
        toolPanel = new JPanel();

        //Configure listOpsPanel
        listOpsPanel.setLayout(null);
        listOpsPanel.setBackground(Color.white);
        listOpsPanel.setBounds(0, 0, 350, 570);
        
        //Configure outputPanel
        outputPanel.setLayout(new BorderLayout(0, 0));
        outputPanel.setBackground(Color.white);
        outputPanel.setBounds(350, 0, 350, 570);

        //Configure toolPanel
        toolPanel.setLayout(null);
        toolPanel.setBounds(0, 570, 715, 100);

    }

    private void setup_input_blocks() {

        //Generate random data
        genDataBlock = new InputBlock("Generate List Filled With Random Data", "elements to gen.", "Generate");
        genDataBlock.configure(20, 10, false, listOpsPanel);
        genDataBlock.get_button().addActionListener(e -> { 
            
            if (gen_random_data(genDataBlock.get_text_field_value()) == 1) return;
            outputTextArea.append(">Generated\n"); 
        });
        
        //Push back
        pushBackBlock = new InputBlock("Push Back Method", "data", "Push Back");
        pushBackBlock.configure(20, 80, false, listOpsPanel);
        pushBackBlock.get_button().addActionListener(e -> { 
            
            Data.push_back(pushBackBlock.get_text_field_text()); 
            outputTextArea.append(">Pushed back\n"); 
        });

        //Push ordered
        pushOrderedBlock = new InputBlock("Push Ordered Method", "data", "Push Ordered");
        pushOrderedBlock.configure(20, 150, false, listOpsPanel);
        pushOrderedBlock.get_button().addActionListener(e -> { 
            
            Data.push_ordered(pushOrderedBlock.get_text_field_text()); 
            outputTextArea.append(">Pushed ordered\n");
        });

        //Get element
        getElementBlock = new InputBlock("Get Element Method", "index", "Get Element");
        getElementBlock.configure(20, 220, false, listOpsPanel);
        getElementBlock.get_button().addActionListener(e -> { 
            
            int index = getElementBlock.get_text_field_value();
            int listSize = Data.get_size();

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Index range violated or list is empty\n");
                return; 
            }
            
            outputTextArea.append('>' + Data.get_element_data(index) + '\n'); 
        });

        //Set element
        setElementBlock = new InputBlock("Set Element Method", "index", "Set Element");
        setElementBlock.configure(20, 290, false, listOpsPanel);
        setElementBlock.get_button().addActionListener(e -> { 
            
            int index = setElementBlock.get_text_field_value();
            int listSize = Data.get_size();

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Range violated or list is empty\n");
                return; 
            }

            String data = (String)JOptionPane.showInputDialog(

                null, null, "Enter Data", JOptionPane.PLAIN_MESSAGE, null, null, null
            );

            if (data == null) return;

            Data.set_element_data(index, data);
            outputTextArea.append(">Element set\n"); 
        });

        //Insert
        insertBlock = new InputBlock("Insert Method", "index", "Insert");
        insertBlock.configure(20, 360, false, listOpsPanel);
        insertBlock.get_button().addActionListener(e -> {

            int index = insertBlock.get_text_field_value();
            int listSize = Data.get_size();

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Range violated or list is empty\n");
                return; 
            }

            String data = (String)JOptionPane.showInputDialog(

                null, null, "Enter Data", JOptionPane.PLAIN_MESSAGE, null, null, null
            );

            if (data == null) return;

            Data.insert(index, data);
            outputTextArea.append(">Inserted\n");
        });

        //Remove
        removeBlock = new InputBlock("Remove Method", "index", "Remove");
        removeBlock.configure(20, 430, false, listOpsPanel);
        removeBlock.get_button().addActionListener(e -> {

            int index = removeBlock.get_text_field_value();
            int listSize = Data.get_size();

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Range violated or list is empty\n");
                return; 
            }

            Data.remove(index);
            outputTextArea.append(">Removed\n");
        });

        //Pop back
        popBackButton = new JButton("Pop Back");
        popBackButton.setBounds(20, 510, 97, 30);
        popBackButton.setFocusable(false);
        popBackButton.addActionListener(e -> {

            if (Data.get_size() == 0) { 
                
                outputTextArea.append("Err:List is empty\n"); 
                return;
            }

            outputTextArea.append('>' + Data.pop_back() + '\n');
        });
        listOpsPanel.add(popBackButton);

        //Get size
        getListSizeButton = new JButton("Get Size");
        getListSizeButton.setBounds(127, 510, 97, 30);
        getListSizeButton.setFocusable(false);
        getListSizeButton.addActionListener(e -> {
            
            outputTextArea.append('>' + ((Integer)Data.get_size()).toString() + '\n');
        });
        listOpsPanel.add(getListSizeButton);

        //Sort
        sortListButton = new JButton("Sort");
        sortListButton.setBounds(234, 510, 97, 30);
        sortListButton.setFocusable(false);
        sortListButton.addActionListener(e -> {
            
            if (Data.get_size() == 0) { 
                
                outputTextArea.append("Err:List is empty\n"); 
                return;
            }
            
            Data.sort();
            outputTextArea.append(">Sorted\n");
        });
        listOpsPanel.add(sortListButton);

        //Import data
        importDataButton = new JButton("Import Data");
        importDataButton.setBounds(20, 15, 150, 30);
        importDataButton.setFocusable(false);
        importDataButton.addActionListener(e -> {

            try {

                import_data();
                outputTextArea.append(">Imported\n");
            }
            catch (IOException inioe) {

                outputTextArea.append(">Err:I/O");
            }
            
        });
        toolPanel.add(importDataButton);

        //Export data
        exportDataButton = new JButton("Export Data");
        exportDataButton.setBounds(180, 15, 150, 30);
        exportDataButton.setFocusable(false);
        exportDataButton.addActionListener(e -> {

            try {

                export_data();
                outputTextArea.append(">Exported\n");
            }
            catch (IOException outioe) {

                outputTextArea.append("Err:I/O\n");
            }
            
        });
        toolPanel.add(exportDataButton);

        //Clear output
        clearOutputButton = new JButton("Clear Output");
        clearOutputButton.setBounds(365, 15, 150, 30);
        clearOutputButton.setFocusable(false);
        clearOutputButton.addActionListener(e -> outputTextArea.setText(null));
        toolPanel.add(clearOutputButton);

        //Print list
        printDataButton = new JButton("Print List");
        printDataButton.setBounds(525, 15, 150, 30);
        printDataButton.setFocusable(false);
        printDataButton.addActionListener(e -> print_data());
        toolPanel.add(printDataButton);

    }

    private void print_data() {
        
        if (Data.get_size() == 0) {

            outputTextArea.append("Err:List is empty\n");
            return;
        }

        for (String element : Data) outputTextArea.append(element + '\n');
        outputTextArea.append("\n");

    }

    private int gen_random_data(int numOfElems) {

        if (numOfElems == 0) { 
            
            outputTextArea.append("Err:Incorrect numeric value\n"); 
            return 1;
        }

        SecureRandom random = new SecureRandom();

        for (int i = 0; i != numOfElems; ++i) Data.push_back(new BigInteger(50, random).toString(32));

        return 0;
    }

    private void import_data() throws IOException {

        Data = new List<String>();

        String fileName = (String)JOptionPane.showInputDialog(

            null, null, "File name", JOptionPane.PLAIN_MESSAGE, null, null, null
        );

        FileInputStream istream = new FileInputStream(fileName);

        int i;
        String data = ""; 

        while((i = istream.read()) != -1){

            if (i != 10) {

                data += Character.toString((char)i);
            }
            else {

                Data.push_back(data);
                data = "";
            }
            
        }

    }

    private void export_data() throws IOException {

        String fileName = (String)JOptionPane.showInputDialog(

            null, null, "File name", JOptionPane.PLAIN_MESSAGE, null, null, null
        );

        FileOutputStream ostream = new FileOutputStream(fileName);

        for (String element : Data) {

            element += "\n";
            ostream.write(element.getBytes());
        } 

        ostream.close();

    }

    public static void main(String[] args) {
        
        new GUI();

    }

}