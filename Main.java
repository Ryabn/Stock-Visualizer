package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        StockDisplay stockDisplay = new StockDisplay();
        frame.setTitle("Sorting Visualizer");
        frame.setPreferredSize(new Dimension(850, 450));
        frame.setMinimumSize(new Dimension(700, 380));
        frame.add(stockDisplay);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        StockParser.initStockParser();
        StockParser.displayStockInfo("TIME_SERIES_INTRADAY", "AAPL","5min");

    }
}
