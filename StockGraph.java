package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StockGraph extends JPanel{

    private static ArrayList<Double> priceList;
    private static ArrayList<String> intervalList;
    private static ArrayList<Integer> xPosList;
    private Boolean bHasGraph;

    private double stockHigh;
    private double stockLow;
    private double stockInnerPadding;
    private double stockRange;
    private final int GRAPH_OUTER_PADDING = 25;
    private final int TEN = 10;

    public StockGraph(){
        bHasGraph = false;
        StockParser.initStockParser();
        this.priceList = StockParser.getPriceList();
        this.intervalList = StockParser.getIntervalList();
        this.xPosList = new ArrayList<>();
    }

    /**
     * Called when a stock is selected. It sends the requested user info to StockParser.java and
     */
    public void displayStockGraph(){
        try {
            StockParser.displayStockInfo("TIME_SERIES_INTRADAY", "AAPL", "5min");
            calculateGraphDimensions();
            calculateXPos();
            bHasGraph = true;
            repaint();
        }catch(Exception e){
            System.err.println("The requested API call could not be made");
            e.printStackTrace();
        }
    }

    /**
     * Calculate the intervals/axis of the stock graph by calculating the difference between the stocks
     * highest and the lowest and making the lowerbound 10% of the difference and the upped bound 10% of the different
     */
    public void calculateGraphDimensions(){
        this.stockHigh = StockParser.getStockHigh();
        this.stockLow = StockParser.getStockLow();
        this.stockRange = stockHigh - stockLow;
        this.stockInnerPadding = stockRange / TEN;

        System.out.printf("StockGraph.java/calculateGraphDimensions()\n stockInnerPadding: %f \n stockRange: %f\n",
                stockInnerPadding, stockRange );
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(bHasGraph){
            graphStock(g);
        }
    }

    public void calculateXPos(){
        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 2);
        xPosList.clear();
        for(int i = 0; i < priceList.size(); i++){
            xPosList.add(GRAPH_OUTER_PADDING + (int)(((priceList.get(i) - stockLow) / stockRange) * panelHeight) );
        }
    }

    public void graphStock(Graphics g){
        int panelWidth = this.getWidth() - 40;

        int intervalWidth = (int)((double)panelWidth / priceList.size());

        int y = 20;
        for( int i = 0; i < xPosList.size() - 1; i++ ){
            g.drawLine( y, xPosList.get(i),(y += intervalWidth), xPosList.get(i+1) );
        }

    }
}
