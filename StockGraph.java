package tech.ryanqyang;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.*;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class StockGraph extends JPanel{
    private static StockParser stockInfo;
    private JFreeChart chart;
    private ChartPanel cp;

    public static StockParser getStockInfo() {
        return stockInfo;
    }
    public ChartPanel getCp() {
        return cp;
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
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                cp.setPreferredSize(new Dimension((int)(getWidth()*0.95), (int) (getHeight()* 0.92)));
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
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
        }
    }

    /**
     * Sets chart Properties every time new chart is created
     */
    private void setChartProperties(){
        chart.setBackgroundPaint(new Color(25, 31, 43));
        chart.setBorderPaint(new Color(25, 31, 43));
        chart.getTitle().setPaint(Color.white);
        chart.getXYPlot().setDomainGridlinePaint(new Color(25, 31, 43));
        chart.getXYPlot().setRangeGridlinePaint(Color.white);
        chart.getXYPlot().getDomainAxis().setTickLabelPaint(Color.white);
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(Color.white);
        chart.getPlot().setOutlinePaint(new Color(150, 0, 250));
        chart.getXYPlot().getRenderer().setBasePaint(new Color(150, 0, 250));
        chart.getPlot().setBackgroundPaint(new Color(25, 31, 43));
    }

    /**
     * Removes the old chart and shows the newest chart
     *
     * @param stockParser
     */
    private void refreshChart(StockParser stockParser) {
        TimeSeries ds = createDataset(stockInfo);
        removeAll();
        revalidate(); // This removes the old chart

        chart = ChartFactory.createTimeSeriesChart(
                stockParser.getSymbolName() + " - " + stockParser.getPriceList().get(stockParser.getPriceList().size() - 1),
                "Time (EST)",
                "Value ($USD)",
                 new TimeSeriesCollection(ds),
                false,
                false,
                false
        );
        setChartProperties();
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(150, 0, 250));
        cp = new ChartPanel(chart);
        cp.setPreferredSize(new Dimension((int)(getWidth()*0.95), (int) (getHeight()* 0.92)));
        add(cp);
        repaint(); // This method makes the new chart appear
        StockPortfolio.readFile();
    }

    /**
     * Creates dataset object that jfreechar utilizes to create graph.
     * @param spInfo
     * @return
     */
    private static TimeSeries createDataset(StockParser spInfo) {
        ArrayList<Double> spPrices = spInfo.getPriceList();
        ArrayList<String> spIntervals = spInfo.getIntervalList();
        double[] prices = new double[ spPrices.size() ];

        double[] interval = new double[ spPrices.size() ];
        TimeSeries dataSeries = new TimeSeries("Series Key");

        for(int i = 0; i < spPrices.size(); i++){
            prices[i] = spPrices.get(i);
            interval[i] = i;
            dataSeries.addOrUpdate(
                    new Minute(Integer.parseInt(spIntervals.get(i).substring(3,5)),
                            new Hour(Integer.parseInt(spIntervals.get(i).substring(0,2)),
                                    new Day())
                    ),
                    spPrices.get(i)
            );
        }
        return dataSeries;
    }
}
