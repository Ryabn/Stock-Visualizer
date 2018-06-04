package tech.ryanqyang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class StockDisplay extends JPanel {
    private JList<String> jlStocks;

    private JPanel jpSideBar;
    private JScrollPane jspStocks;
    private StockPortfolio jpPortfolio;

    private StockData jpStockVisualizer;
    private AutocompleteSearch jtfSearch;


    public StockDisplay(){
        createComponents();
        wireComponents();
    }

    public void createComponents(){
        BorderLayout mainLayout = new BorderLayout();
        BorderLayout sideBarLayout = new BorderLayout(10, 0);

        setLayout(mainLayout);

        jpStockVisualizer = new StockData();
        jpStockVisualizer.setBackground(new Color(25, 31, 43));
        jpStockVisualizer.setForeground(Color.white);
        add(jpStockVisualizer, mainLayout.CENTER);

        jpPortfolio = new StockPortfolio();
        add(jpPortfolio, mainLayout.SOUTH);

        jpSideBar = new JPanel();
        jpSideBar.setBorder(new EmptyBorder(20,0, 40, 0));
        jpSideBar.setBackground(new Color(25, 31, 43));
        jpSideBar.setLayout(sideBarLayout);

        jtfSearch = new AutocompleteSearch();
        jtfSearch.setBackground(Color.white);

        jpSideBar.add(jtfSearch, sideBarLayout.NORTH);

        jlStocks = new JList<>();
        jspStocks = new JScrollPane(jlStocks);

        jlStocks.setBorder(new EmptyBorder(5,10, 10, 10));
        jlStocks.setBackground(new Color(77, 92, 122));
        jlStocks.setForeground(new Color(255, 255, 255));
        jlStocks.setLayoutOrientation(JList.VERTICAL);

        jlStocks.setFixedCellHeight(20);
        jlStocks.setFixedCellWidth(180);
        jpSideBar.add(jspStocks, sideBarLayout.CENTER);

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
            }
        });
        jlStocks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()){
                    String name = jlStocks.getSelectedValue();
                    for(int i = 0; i < name.length(); i++){
                        if(name.charAt(i) == ' '){
                            selectedStock(name.substring(0, i));
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * Updates the new list selection based on user input
     * Called by list selection listener whenever user changes input
     */
    public void newSearch(){
        String[] newList = jtfSearch.updateSuggestions(jtfSearch.getText());
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < newList.length; i++) {
            model.addElement(newList[i]);
        }
        jlStocks.setModel(model);
    }

    /**
     * Initializes the stock graph with selected stock
     * @param symbol
     */
    public void selectedStock(String symbol){
        jpStockVisualizer.displayStockGraph(symbol);
    }
}