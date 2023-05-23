package br.eti.erley.csv;

import com.opencsv.CSVReader;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Erley
 */
public class MainFrame extends JFrame {
    private JTable jTable;
    private JButton openCSV;
    private JFileChooser fileChooser;
    private JToolBar toolBar;
    
    MyDataTableModel dataTableModel;
    
    public MainFrame() throws HeadlessException {
        //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);
        this.setBounds(100, 100, 900, 600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        // chava a inicializacao do programa de importacao
        init();
        System.out.println("MainFrame.<init>()");
    }
    
    private void init() {
        openCSV = new JButton("Choose a file to read");
        setAction();
        
        fileChooser = new JFileChooser();
        toolBar = new JToolBar();
        toolBar.add(openCSV);
        
        this.add(toolBar, BorderLayout.NORTH);
        System.out.println("MainFrame.init()");
        jTable = new JTable();
        this.add(new JScrollPane(jTable), BorderLayout.CENTER);
        //this.add(jTable, BorderLayout.CENTER);
        
        dataTableModel = new MyDataTableModel();
        jTable.setModel(dataTableModel);
        
        dataTableModel.fireTableDataChanged();
    }
    
    private void setAction() {
        openCSV.addActionListener(new ActionListener () {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = fileChooser.showOpenDialog(MainFrame.this);
                if(i == JFileChooser.APPROVE_OPTION) {
                    System.out.println("file choose");
                    File file = fileChooser.getSelectedFile();
                    System.err.println(file);
                    ArrayList<String[]> all = readCSV(file);
                    //MyDataTableModel dataTableModel = (MyDataTableModel) jTable.getModel();
                    
                    String[] cols = all.get(0);
                    all.remove(0);
                    MyDataTableModel dataTableModel = new MyDataTableModel(all, cols);
                    jTable.setModel(dataTableModel);
                    
                    
//                    dataTableModel.setValues(all);
//                    dataTableModel.setColumns(cols);
                    
                    System.err.println("col " + cols.length);
                    dataTableModel.fireTableDataChanged();
                }
            }
        } );
    }
    
    private ArrayList<String[]> readCSV(File file) {
        ArrayList<String[]> data = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            CSVReader cSVReader = new CSVReader(fileReader);
            
            Iterator<String[]> rows = cSVReader.iterator();
            
            int count = 0;
            boolean flag = false;
            
            while(rows.hasNext()) {
                count ++;
                
                String[] cols = rows.next();
                if(count == 1) {
                    for(String s : cols) {
                        if(s.equalsIgnoreCase("Transaction_date")) {
                            flag = true;
                        }
                    }
                }
                if(flag == false) {
                    JOptionPane.showMessageDialog(this, "Invalid File");
                    break;
                }
                //System.err.println(Arrays.toString(cols));
                data.add(cols);
            }
            System.out.println("MainFrame.readCSV() " + data.size());
        } catch(Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
        return data;
    }
    
}
