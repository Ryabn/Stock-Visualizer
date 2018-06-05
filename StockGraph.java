package tech.ryanqyang;

import javafx.scene.chart.Chart;
import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.Title;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;
import java.util.ArrayList;

import static org.jfree.chart.ChartColor.DARK_RED;

public class StockGraph extends JPanel{
    private StockParser stockInfo;
    private JFreeChart chart;
    private ChartPanel cp;

    public StockParser getStockInfo() {
        return stockInfo;
    }

    public StockGraph(){
        DefaultXYDataset ds = new DefaultXYDataset();
        double[][] data = { {}, {} };
        ds.addSeries("Stock", data);
        chart = ChartFactory.createXYLineChart("Select a Stock",
                "Value", "Time", ds, PlotOrientation.HORIZONTAL, false, true,
                false);
        setChartProperties();
        cp = new ChartPanel(chart);
        add(cp);
    }

    /**
     * Called when a stock is selected. It sends the requested user info to StockParser.java
     * Once graph data is analyzed, will repaint
     */
    public void displayStockGraph(String symbol){
        try {
            stockInfo = new StockParser("TIME_SERIES_INTRADAY", symbol, "5min");
            refreshChart(stockInfo);
        }catch(Exception e) {
            System.err.println("The requested API call could not be made");
            e.printStackTrace();
        }
    }
    private void setChartProperties(){
        chart.setBackgroundPaint(new Color(25, 31, 43));
        chart.setBorderPaint(new Color(25, 31, 43));
        chart.getTitle().setPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(Color.blue);
        chart.getXYPlot().setRangeGridlinePaint(Color.blue);
        chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.white);
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.white);
        chart.getPlot().setOutlinePaint(new Color(150, 0, 250));
        chart.getXYPlot().getRenderer().setBasePaint(new Color(150, 0, 250));
    }

    /**
     * Removes the old chart and shows the newest chart
     *
     * @param stockParser
     */
    private void refreshChart(StockParser stockParser) {
        XYDataset ds = createDataset(stockInfo);
        removeAll();
        revalidate(); // This removes the old chart
        chart = ChartFactory.createXYLineChart("Stock Price",
                "Value", "Time", ds, PlotOrientation.HORIZONTAL, false, true,
                false);

        setChartProperties();
        cp = new ChartPanel(chart);
        add(cp);
        repaint(); // This method makes the new chart appear
    }

    /**
     * Creates dataset object that jfreechar utilizes to create graph.
     * @param spInfo
     * @return
     */
    private static XYDataset createDataset(StockParser spInfo) {
        DefaultXYDataset ds = new DefaultXYDataset();
        ArrayList<Double> spPrices = spInfo.getPriceList();

        double[] prices = new double[ spPrices.size() ];

        double[] interval = new double[ spPrices.size() ];

        for(int i = 0; i < spPrices.size(); i++){
            prices[i] = spPrices.get(i);
            interval[i] = i;
        }

        double[][] data = { prices, interval };

        ds.addSeries("Stock", data);

        return ds;
    }
//    /**
//     * Calculate the intervals/axis of the stock graph by calculating the difference between the stocks
//     * highest and the lowest and making the lowerbound 10% of the difference and the upped bound 10% of the different
//     */
//    public void calculateGraphDimensions(){
//        this.stockHigh = stockInfo.getStockHigh();
//        this.stockLow = stockInfo.getStockLow();
//        this.stockRange = stockHigh - stockLow;
//        this.stockInnerPadding = stockRange / TEN;
//
//        System.out.printf("StockGraph.java/calculateGraphDimensions()\n stockInnerPadding: %f \n stockRange: %f\n",
//                stockInnerPadding, stockRange );
//    }
//
//    /**
//     * Overrides paint function
//     * When graph data is available, will plot graph and display
//     * @param g
//     */
//    @Override
//    public void paint(Graphics g){
//        super.paint(g);
//        if(bHasGraph){
//            calculateXPos();
//            graphStock(g);
//        }
//    }
//
//    /**
//     * Calculates the location of various points of stock prices
//     *
//     */
//    public void calculateXPos(){
//        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 4);
//        xPosList.clear();
//        for(int i = 0; i < stockInfo.getPriceList().size(); i++){
//            xPosList.add((int)(((stockInfo.getPriceList().get(i) - stockLow + stockInnerPadding) / stockRange) * panelHeight) );
//        }
//    }
//
//    /**
//     * paints the graph with the data members set by the stock parser function
//     * @param g
//     */
//    public void graphStock(Graphics g){
//        int panelWidth = this.getWidth() - 40;
//        int panelHeight = this.getHeight() - (GRAPH_OUTER_PADDING * 2);
//        int intervalWidth = (int)((double)panelWidth / stockInfo.getPriceList().size());
//        final int Y_START = 20;
//        int y = Y_START;
//        for( int i = 0; i < xPosList.size() - 1; i++ ){
//            g.setColor( new Color(131, 192, 239));
//            g.drawLine( y, xPosList.get(i),(y += intervalWidth), xPosList.get(i+1) );
//        }
//        g.setColor(new Color(94, 0, 196));
//
//        final int LINE_THICKNESS = 5;
//
//        for(int i = 0; i <= LINE_THICKNESS; i++){
//            g.drawLine(Y_START, panelHeight + i, y + LINE_THICKNESS, panelHeight + i);
//            g.drawLine(y + i, 10, y + i, panelHeight + LINE_THICKNESS);
//        }
//
//    }
}
