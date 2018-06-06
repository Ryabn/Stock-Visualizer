package tech.ryanqyang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StockData extends JPanel {

    private JLabel jlStockDescription;
    private StockGraph graph;

    public StockData(){
        createComponents();
        wireComponents();
    }

    public void createComponents(){
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        setBorder(new EmptyBorder(20, 0,0 ,0));
        jlStockDescription = new JLabel("Stock Portfolio Organizer", SwingConstants.CENTER);
        jlStockDescription.setFont(new Font(jlStockDescription.getName(), Font.BOLD, 30));
        jlStockDescription.setForeground(Color.WHITE);
        add(jlStockDescription, BorderLayout.NORTH);

        graph = new StockGraph();
        graph.setBackground(new Color(25, 31, 43));
        add(graph, BorderLayout.CENTER);

    }
    public void wireComponents(){

    }

    public void displayStockGraph(String symbol){
        graph.displayStockGraph(symbol);
    }
}
