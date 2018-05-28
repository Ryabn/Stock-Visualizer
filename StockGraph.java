package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StockGraph extends JPanel{
    private static ArrayList<Integer> xPosList;
    private Boolean bHasGraph;

    private StockParser stockInfo;
    private double stockHigh;
    private double stockLow;
    private double stockInnerPadding;
    private double stockRange;
    private final int GRAPH_OUTER_PADDING = 25;
    private final int TEN = 10;

    public StockParser getStockInfo() {
        return stockInfo;
    }

    public StockGraph(){
        bHasGraph = false;
    }

    /**
     * Called when a stock is selected. It sends the requested user info to StockParser.java
     * Once graph data is analyzed, will repaint
     */
    public void displayStockGraph(String symbol){
        try {
            stockInfo = new StockParser("TIME_SERIES_INTRADAY", symbol, "5min");
            this.xPosList = new ArrayList<>();
        }catch(Exception e) {
            System.err.println("The requested API call could not be made");
            e.printStackTrace();
        }
        calculateGraphDimensions();
        calculateXPos();
        bHasGraph = true;
        repaint();
    }

    /**
     * Calculate the intervals/axis of the stock graph by calculating the difference between the stocks
     * highest and the lowest and making the lowerbound 10% of the difference and the upped bound 10% of the different
     */
    public void calculateGraphDimensions(){
        this.stockHigh = stockInfo.getStockHigh();
        this.stockLow = stockInfo.getStockLow();
        this.stockRange = stockHigh - stockLow;
        this.stockInnerPadding = stockRange / TEN;

        System.out.printf("StockGraph.java/calculateGraphDimensions()\n stockInnerPadding: %f \n stockRange: %f\n",
                stockInnerPadding, stockRange );
    }

    /**
     * Overrides paint function
     * When graph data is available, will plot graph and display
     * @param g
     */
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(bHasGraph){
            calculateXPos();
            graphStock(g);
        }
    }

    /**
     * Calculates the location of various points of stock prices
     *
     */
    public void calculateXPos(){
        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 4);
        xPosList.clear();
        for(int i = 0; i < stockInfo.getPriceList().size(); i++){
            xPosList.add((int)(((stockInfo.getPriceList().get(i) - stockLow + stockInnerPadding) / stockRange) * panelHeight) );
        }
    }

    /**
     * paints the graph with the data members set by the stock parser function
     * @param g
     */
    public void graphStock(Graphics g){
        int panelWidth = this.getWidth() - 40;
        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 2);
        int intervalWidth = (int)((double)panelWidth / stockInfo.getPriceList().size());
        final int Y_START = 20;
        int y = Y_START;
        for( int i = 0; i < xPosList.size() - 1; i++ ){
            g.setColor( new Color(131, 192, 239));
            g.drawLine( y, xPosList.get(i),(y += intervalWidth), xPosList.get(i+1) );
        }
        g.setColor(new Color(94, 0, 196));

        final int LINE_THICKNESS = 5;

        for(int i = 0; i <= LINE_THICKNESS; i++){
            g.drawLine(Y_START, panelHeight + i, y + LINE_THICKNESS, panelHeight + i);
            g.drawLine(y + i, 10, y + i, panelHeight + LINE_THICKNESS);
        }

    }
}
