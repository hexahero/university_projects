import list._
import util.Random
import java.util.Scanner
import java.io.{File, FileOutputStream, FileInputStream, IOException}

import java.awt.Dimension
import java.awt.BorderLayout
import java.awt.GridLayout
import java.awt.Dimension
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import java.awt.Color

import javax.swing.WindowConstants
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JTextArea
import javax.swing.JScrollPane
import javax.swing.JOptionPane
import javax.swing.ScrollPaneConstants

class InputBlock(

    private val labelX: Int = 310,
    private val labelY: Int = 10,
    private val textFieldX: Int = 100,
    private val textFieldY: Int = 30,
    private val buttonX: Int = 200,
    private val buttonY: Int = 30,
    
    private var label: JLabel = null,    
    private var textField: JTextField = null,
    private var button: JButton = null

){

    def init(labelText: String, textFieldText: String, buttonText: String): Unit = {

        label = new JLabel(labelText)
        textField = new JTextField(textFieldText)
        button = new JButton(buttonText)

    }

    def init(labelText: String, buttonText: String): Unit = {

        label = new JLabel(labelText)
        textField = new JTextField()
        button = new JButton(buttonText)

    }

    def configure(posX: Int, posY: Int, focusable: Boolean, panel: JPanel): Unit =  {

        label.setBounds(posX, posY, labelX, labelY)
        textField.setBounds(posX, posY + 20, textFieldX, textFieldY)
        button.setBounds(posX + textFieldX + 10, posY + 20, buttonX, buttonY)
        button.setFocusable(focusable)
        attach_to(panel)

    }
    
    def attach_to(panel: JPanel): Unit = {

        panel.add(label);
        panel.add(textField);
        panel.add(button);

    }

    def get_text_field_value(): Int = {
        
        var value = 0

        try { 
            
            value = Integer.parseInt(textField.getText())
        }
        catch {

            case e: NumberFormatException => {

                JOptionPane.showMessageDialog(null, "Incorrect numeric value", "Error", JOptionPane.ERROR_MESSAGE)
                throw new Error("Incorrect numeric value")

            } 

        }
        
        return value
    }

    def get_text_field_text(): String = {

        return textField.getText()
    }

    def get_label(): JLabel = {

        return label
    }

    def get_textField(): JTextField = {

        return textField
    }

    def get_button(): JButton = {

        return button
    }

}

object Main {

    //Declare components
    private var frame: JFrame = null

    private var listOpsPanel: JPanel = null
    private var outputPanel: JPanel = null
    private var toolPanel: JPanel = null
    
    private var genDataBlock: InputBlock = null
    private var pushBackBlock: InputBlock = null
    private var pushOrderedBlock: InputBlock = null
    private var getElementBlock: InputBlock = null
    private var setElementBlock: InputBlock = null
    private var insertBlock: InputBlock = null
    private var removeBlock: InputBlock = null

    private var clearOutputButton: JButton = null
    private var printDataButton: JButton = null
    private var popBackButton: JButton = null
    private var getListSizeButton: JButton = null
    private var sortListButton: JButton = null

    private var importDataButton: JButton = null
    private var exportDataButton: JButton = null
    
    private var outputTextArea: JTextArea = null
    private var outputTextAreaScrollPane: JScrollPane = null

    //Declare panel properties
    private var panelX = 350
    private var panelY = 500

    //Declare data list
    var Data = new List[String]()

    private def gui_init(): Unit = {

        //Construct components
        frame = new JFrame()

        outputTextArea = new JTextArea()
        outputTextAreaScrollPane = new JScrollPane(outputTextArea)
        
        //Setup panels
        setup_panels()

        //Setup input blocks
        setup_input_blocks();

        //Configure outputTextArea
        outputTextArea.setEditable(false)
        outputTextArea.setFont(outputTextArea.getFont().deriveFont(14f))

        //Configure outputTextAreaScrollPane
        outputTextAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS)

        //Add components to outputPanel
        outputPanel.add(outputTextAreaScrollPane, BorderLayout.CENTER)

        //Configure frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
        frame.setLayout(null)
        frame.setResizable(false)
        frame.setTitle("List Window App")
        frame.setBounds(300, 300, 715, 670)
        frame.setVisible(true)

        //Add panels to the frame
        frame.add(listOpsPanel)
        frame.add(outputPanel)
        frame.add(toolPanel)

    }

    private def setup_panels(): Unit = {
        
        listOpsPanel = new JPanel()
        outputPanel = new JPanel()
        toolPanel = new JPanel()

        //Configure listOpsPanel
        listOpsPanel.setLayout(null)
        listOpsPanel.setBackground(Color.white)
        listOpsPanel.setBounds(0, 0, 350, 570)
        
        //Configure outputPanel
        outputPanel.setLayout(new BorderLayout(0, 0))
        outputPanel.setBackground(Color.white)
        outputPanel.setBounds(350, 0, 350, 570)

        //Configure toolPanel
        toolPanel.setLayout(null)
        toolPanel.setBounds(0, 570, 715, 100)

    }

    private def setup_input_blocks(): Unit = {

        //Generate random data
        genDataBlock = new InputBlock()
        genDataBlock.init("Generate List Filled With Random Data", "elements to gen.", "Generate")
        genDataBlock.configure(20, 10, false, listOpsPanel)
        genDataBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = genButtonAction() }
        )
        def genButtonAction(): Unit = {

            if (gen_random_data(genDataBlock.get_text_field_value()) == 1) return
            outputTextArea.append(">Generated\n")

        }

        //Push back
        pushBackBlock = new InputBlock()
        pushBackBlock.init("Push Back Method", "data", "Push Back")
        pushBackBlock.configure(20, 80, false, listOpsPanel)
        pushBackBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = pushBackAction() }
        )
        def pushBackAction(): Unit = {

            Data.push_back(pushBackBlock.get_text_field_text())
            outputTextArea.append(">Pushed back\n")

        }

        //Push ordered
        pushOrderedBlock = new InputBlock()
        pushOrderedBlock.init("Push Ordered Method", "data", "Push Ordered")
        pushOrderedBlock.configure(20, 150, false, listOpsPanel)
        pushOrderedBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = pushOrderedAction() }
        )
        def pushOrderedAction(): Unit = {

            Data.push_ordered(pushOrderedBlock.get_text_field_text())
            outputTextArea.append(">Pushed ordered\n")

        }

        //Get element
        getElementBlock = new InputBlock()
        getElementBlock.init("Get Element Method", "index", "Get Element")
        getElementBlock.configure(20, 220, false, listOpsPanel)
        getElementBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = getElementAction() }
        )
        def getElementAction(): Unit = {

            var index = getElementBlock.get_text_field_value()
            var listSize = Data.get_size()

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Index range violated or list is empty\n")
                return
            }
            
            outputTextArea.append(">" + Data.get_element_data(index) + "\n")

        }

        //Set element
        setElementBlock = new InputBlock()
        setElementBlock.init("Set Element Method", "index", "Set Element")
        setElementBlock.configure(20, 290, false, listOpsPanel)
        setElementBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = setElementAction() }
        )
        def setElementAction(): Unit = {

            var index = setElementBlock.get_text_field_value()
            var listSize = Data.get_size()

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Range violated or list is empty\n")
                return
            }

            var data: String = JOptionPane.showInputDialog(

                null, null, "Enter Data", JOptionPane.PLAIN_MESSAGE, null, null, null
            ).asInstanceOf[String]

            if (data == null) return

            Data.set_element_data(index, data)
            outputTextArea.append(">Element set\n")

        }

        //Insert
        insertBlock = new InputBlock()
        insertBlock.init("Insert Method", "index", "Insert")
        insertBlock.configure(20, 360, false, listOpsPanel)
        insertBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = insertAction() }
        )
        def insertAction(): Unit = {

            var index = insertBlock.get_text_field_value()
            var listSize = Data.get_size()

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Range violated or list is empty\n")
                return
            }

            var data: String = JOptionPane.showInputDialog(

                null, null, "Enter Data", JOptionPane.PLAIN_MESSAGE, null, null, null
            ).asInstanceOf[String]

            if (data == null) return

            Data.insert(index, data)
            outputTextArea.append(">Inserted\n")

        }

        //Remove
        removeBlock = new InputBlock()
        removeBlock.init("Remove Method", "index", "Remove")
        removeBlock.configure(20, 430, false, listOpsPanel)
        removeBlock.get_button().addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = removeAction() }
        )
        def removeAction(): Unit = {

            var index = removeBlock.get_text_field_value()
            var listSize = Data.get_size()

            if (listSize == 0 || (index < 0 || index > listSize - 1)) {

                outputTextArea.append("Err:Range violated or list is empty\n")
                return
            }

            Data.remove(index)
            outputTextArea.append(">Removed\n")

        }

        //Pop back
        popBackButton = new JButton("Pop Back")
        popBackButton.setBounds(20, 510, 97, 30)
        popBackButton.setFocusable(false)
        popBackButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = popBackAction() }
        )
        listOpsPanel.add(popBackButton)
        def popBackAction(): Unit = {

            if (Data.get_size() == 0) { 
                
                outputTextArea.append("Err:List is empty\n")
                return
            }

            outputTextArea.append(">" + Data.pop_back() + "\n")

        }

        //Get size
        getListSizeButton = new JButton("Get Size")
        getListSizeButton.setBounds(127, 510, 97, 30)
        getListSizeButton.setFocusable(false)
        getListSizeButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = getSizeAction() }
        )
        listOpsPanel.add(getListSizeButton)
        def getSizeAction(): Unit = {

            outputTextArea.append(">" + Data.get_size() + "\n")
        }

        //Sort
        sortListButton = new JButton("Sort")
        sortListButton.setBounds(234, 510, 97, 30)
        sortListButton.setFocusable(false)
        sortListButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = sortAction() }
        )
        listOpsPanel.add(sortListButton)
        def sortAction(): Unit = {

            if (Data.get_size() == 0) { 
                
                outputTextArea.append("Err:List is empty\n")
                return
            }
            
            Data.merge_sort()
            outputTextArea.append(">Sorted\n")

        }

        //Import data
        importDataButton = new JButton("Import Data")
        importDataButton.setBounds(20, 15, 150, 30)
        importDataButton.setFocusable(false)
        importDataButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = importAction() }
        )
        toolPanel.add(importDataButton)
        def importAction(): Unit = {

            try {

                import_data()
                outputTextArea.append(">Imported\n")
            }
            catch {

                case e: IOException => outputTextArea.append(">Err:I/O")
            }

        }

        //Export data
        exportDataButton = new JButton("Export Data")
        exportDataButton.setBounds(180, 15, 150, 30)
        exportDataButton.setFocusable(false)
        exportDataButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = exportAction() }
        )
        toolPanel.add(exportDataButton)
        def exportAction(): Unit = {

            try {

                export_data()
                outputTextArea.append(">Exported\n")
            }
            catch  {

                case e: IOException => outputTextArea.append("Err:I/O\n")
            }

        }

        //Clear output
        clearOutputButton = new JButton("Clear Output")
        clearOutputButton.setBounds(365, 15, 150, 30)
        clearOutputButton.setFocusable(false)
        clearOutputButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = outputClearAction() }
        )
        toolPanel.add(clearOutputButton)
        def outputClearAction(): Unit = {

            outputTextArea.setText(null)
        }

        //Print list
        printDataButton = new JButton("Print List")
        printDataButton.setBounds(525, 15, 150, 30)
        printDataButton.setFocusable(false)
        printDataButton.addActionListener(

            new ActionListener { override def actionPerformed(e: ActionEvent): Unit = print_data() }
        )
        toolPanel.add(printDataButton)
        

    }

    private def print_data(): Unit = {
        
        if (Data.get_size() == 0) {

            outputTextArea.append("Err:List is empty\n")
            return
        }

        for (element <- Data) outputTextArea.append(element + '\n')
        outputTextArea.append("\n")

    }

    private def gen_random_data(numOfElems: Int): Int = {

        if (numOfElems == 0) { 
            
            outputTextArea.append("Err:Incorrect numeric value\n") 
            return 1
        }

        val rand = new Random()
        val alphNum = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes()
  
        def gen_random_str(chars: Array[Byte], length: Int): String = {

            val bytes = new Array[Byte](length)
            for (i <- 0 until length) bytes(i) = chars(rand.nextInt(chars.length))
      
            return new String(bytes)
        }

        for (i <- 0 until numOfElems) Data.push_back(gen_random_str(alphNum, 8))

        return 0
    }

    @throws(classOf[IOException])
    private def import_data(): Unit = {

        Data = new List[String]()

        var fileName: String = JOptionPane.showInputDialog(

            null, null, "File name", JOptionPane.PLAIN_MESSAGE, null, null, null
        ).asInstanceOf[String]

        var file = new File(fileName)
        var scanner = new Scanner(file)

        while (scanner.hasNextLine) {

            Data.push_back(scanner.nextLine)
        }

    }

    @throws(classOf[IOException])
    private def export_data(): Unit = {

        var fileName: String = JOptionPane.showInputDialog(

            null, null, "File name", JOptionPane.PLAIN_MESSAGE, null, null, null
        ).asInstanceOf[String]

        var ostream: FileOutputStream = new FileOutputStream(fileName)

        for (element: String <- Data) {
           
            ostream.write(element.getBytes())
            ostream.write('\n')
        } 

        ostream.close()

    }

    def main(args: Array[String]): Unit = {
        
        gui_init()
    }

}