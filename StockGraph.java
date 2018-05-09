package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StockGraph extends JPanel{

    private static ArrayList<Double> priceList;
    private static ArrayList<Integer> xPosList;
    private static ArrayList<String> intervalList;

    private double stockHigh;
    private double stockLow;
    private double stockInnerPadding;
    private double stockRange;
    private final int graphOuterPadding = 15;

    public StockGraph(){

    }

    public void displayStockGraph(){
        this.priceList = StockParser.getPriceList();
        this.intervalList = StockParser.getIntervalList();
    }

    /**
     * Calculate the intervals/axis of the stock graph by calculating the difference between the stocks
     * highest and the lowest and making the lowerbound 10% of the difference and the upped bound 10% of the different
     */
    public void calculateGraphDimensions(){
        double dMinMaxDiff = stockHigh - stockLow;
        stockInnerPadding = dMinMaxDiff * 0.1;
        stockRange = dMinMaxDiff + (2 * stockInnerPadding );
        System.out.printf("StockGraph.java/calculateGraphDimensions()\n stockInnerPadding: %f \n stockRange: %f\n",
                stockInnerPadding, stockRange );

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        //graphStock(g);
    }

    public void calculateXPos(){
        int panelHeight = this.getHeight() - (graphOuterPadding * 2);
        for(int i = 0; i < priceList.size(); i++){
            xPosList.add(panelHeight *  + 15);
        }
    }

    public void graphStock(Graphics g){
        int panelWidth = this.getWidth() - 40;

        int intervalWidth = (int)((double)panelWidth / priceList.size());

        int y = 20;
        for( int i = 0; i < xPosList.size() - 1; i++ ){
            g.drawLine(xPosList.get(i), y, xPosList.get(i+1), (y += intervalWidth));

        }

    }
}
