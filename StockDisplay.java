package tech.ryanqyang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StockDisplay extends JPanel {
    private static ArrayList<String> stockList;
    private JList<String> jlStocks;

    private JPanel jpControlButtons;
    private JPanel jpSideBar;

    private JButton jbStart;

    private StockData jpStockVisualizer;
    private AutocompleteSearch jtfSearch;


    public StockDisplay(){
        createComponents();
        wireComponents();
    }
    public void createComponents(){
        BorderLayout mainLayout = new BorderLayout();
        BorderLayout sideBarLayout = new BorderLayout(10, 30);
        GridLayout visualizerControls = new GridLayout(3, 3, 5, 10);

        setLayout(mainLayout);

        jpStockVisualizer = new StockData();
        jpStockVisualizer.setBackground(new Color(25, 31, 43));
        jpStockVisualizer.setForeground(Color.white);
        add(jpStockVisualizer, mainLayout.CENTER);

        jpSideBar = new JPanel();
        jpSideBar.setBorder(new EmptyBorder(20,0, 40, 0));
        jpSideBar.setBackground(new Color(25, 31, 43));
        jpSideBar.setLayout(sideBarLayout);

        jpControlButtons = new JPanel();
        jpControlButtons.setBackground(new Color(25, 31, 43));
        jpControlButtons.setLayout(visualizerControls);

        jtfSearch = new AutocompleteSearch();
        jtfSearch.setBackground(Color.white);

        jbStart = new JButton("Start");

        jbStart.setOpaque(true);
        jbStart.setBackground(new Color(84, 216, 99));

        jpControlButtons.add(jbStart);

        jpSideBar.add(jpControlButtons, sideBarLayout.CENTER);
        jpSideBar.add(jtfSearch, sideBarLayout.SOUTH);

        jlStocks = new JList<>();
        JScrollPane jspStocks = new JScrollPane(jlStocks);

        jlStocks.setBorder(new EmptyBorder(5,10, 10, 10));
        jlStocks.setBackground(new Color(77, 92, 122));
        jlStocks.setForeground(new Color(255, 255, 255));
        jlStocks.setLayoutOrientation(JList.VERTICAL);

        jlStocks.setFixedCellHeight(30);
        jlStocks.setPreferredSize(new Dimension(400, 700));
        jpSideBar.add(jspStocks, sideBarLayout.NORTH);

        add(jpSideBar, mainLayout.EAST);
        setBackground(new Color(25, 31, 43));
        setBorder(new EmptyBorder(0,20, 0, 20));
    }

    public void wireComponents(){
        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                newSearch();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                newSearch();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                newSearch();
            }
        });
        jlStocks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    String name = jlStocks.getSelectedValue();
                    System.out.print(name);
                    for(int i = 0; i < name.length(); i++){
                        if(name.charAt(i) == ' '){
                            selectedStock(name.substring(0, i));
                            break;
                        }
                    }
                    selectedStock(jlStocks.getSelectedValue());
                }
            }
        });
        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                selectedStock();
            }
        });
    }

    public void newSearch(){
        String[] newList = jtfSearch.updateSuggestions(jtfSearch.getText());
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < newList.length; i++) {
            model.addElement(newList[i]);
        }
        jlStocks.setModel(model);
    }
    public void selectedStock(String symbol){
        jpStockVisualizer.displayStockGraph(symbol);
    }
}
