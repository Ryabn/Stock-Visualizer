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
            StockParser.displayStockInfo("TIME_SERIES_INTRADAY", "MSFT", "5min");
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

//    public void

    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(bHasGraph){
            calculateXPos();
            graphStock(g);
        }
    }

    public void calculateXPos(){
        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 4);
        xPosList.clear();
        for(int i = 0; i < priceList.size(); i++){
            xPosList.add((int)(((priceList.get(i) - stockLow + stockInnerPadding) / stockRange) * panelHeight) );
        }
    }

    public void graphStock(Graphics g){
        int panelWidth = this.getWidth() - 40;
        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 2);
        int intervalWidth = (int)((double)panelWidth / priceList.size());
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
