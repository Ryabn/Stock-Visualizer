package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StockGraph extends JPanel{

    private static ArrayList<Double> priceList;
    private static ArrayList<Integer> xPosList;
    private static ArrayList<String> intervalList;

    public StockGraph(){

    }

    public void displayStockGraph(){
        this.priceList = StockParser.getPriceList();
        this.intervalList = StockParser.getIntervalList();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        //graphStock(g);
    }

    public void calculateXPos(){
        int panelHeight = this.getHeight() - 30;
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
