package tech.ryanqyang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StockDisplay extends JPanel {
    private static ArrayList<String> stockList;
    private JList<String> jlStocks;

    private JPanel jpControlButtons;
    private JPanel jpSideBar;
    private JSlider jsAnimationSpeed;

    private JButton jbStart;
    private JButton jbStop;
    private JButton jbNext;
    private JButton jbPrevious;

    private StockGraph jpStockVisualizer;

    public StockDisplay(){
        createComponents();
        wireComponents();
        StockParser.initStockParser();
        StockParser.displayStockInfo("TIME_SERIES_INTRADAY", "AAPL","5min");
    }
    public void createComponents(){
        BorderLayout mainLayout = new BorderLayout();
        BorderLayout sideBarLayout = new BorderLayout(10, 30);
        GridLayout visualizerControls = new GridLayout(2, 2, 5, 10);

        setLayout(mainLayout);

        jpStockVisualizer = new StockGraph();
        jpStockVisualizer.setBackground(new Color(25, 31, 43));
        jpStockVisualizer.setForeground(Color.white);
        add(jpStockVisualizer, mainLayout.CENTER);

        jpSideBar = new JPanel();
        jpSideBar.setBorder(new EmptyBorder(20,0, 40, 0));
        jpSideBar.setBackground(new Color(25, 31, 43));
        jpSideBar.setLayout(sideBarLayout);

        jpControlButtons = new JPanel();
        jpControlButtons.setBackground(new Color(25, 31, 43));
        jpControlButtons.setLayout(visualizerControls);

        jbStart = new JButton("Start");
        jbStop = new JButton("Stop");
        jbPrevious = new JButton("Next");
        jbNext = new JButton("Prev");

        jbStart.setOpaque(true);
        jbStart.setBackground(new Color(84, 216, 99));

        jbStop.setOpaque(true);
        jbStop.setBackground(new Color(196, 0, 0));

        jpControlButtons.add(jbStart);
        jpControlButtons.add(jbStop);
        jpControlButtons.add(jbPrevious);
        jpControlButtons.add(jbNext);

        jpSideBar.add(jpControlButtons, sideBarLayout.CENTER);

        jlStocks = new JList<>();

        jlStocks.setBorder(new EmptyBorder(10,30, 10, 30));
        jlStocks.setBackground(new Color(77, 92, 122));
        jlStocks.setForeground(new Color(255, 255, 255));
        jlStocks.setLayoutOrientation(JList.VERTICAL);

        jlStocks.setFixedCellHeight(30);
        jpSideBar.add(jlStocks, sideBarLayout.NORTH);

        jsAnimationSpeed = new JSlider();
        jsAnimationSpeed.setForeground(new Color(255, 255, 255));
        jsAnimationSpeed.setPaintTicks(true);

        jpSideBar.add(jsAnimationSpeed, sideBarLayout.SOUTH);

        add(jpSideBar, mainLayout.EAST);
        setBackground(new Color(25, 31, 43));
        setBorder(new EmptyBorder(0,20, 0, 20));
    }

    public void wireComponents(){
        jlStocks.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                }
            }
        });
        jbStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
    }


    public void displayArrayGeneral(Graphics g){
        int panelWidth = jpStockVisualizer.getWidth();
        int panelHeight = jpStockVisualizer.getHeight() - 20;
//        barWidth = (panelWidth - 40) / randomizedArray.length;
//        int width = barWidth - 4;
//
//        for (int i = 0; i < randomizedArray.length; i++) {
//            int height = (int)((randomizedArray[i] / 100.0) * (panelHeight * 0.8));
//            int xPos = 20 + (i * barWidth) + 2;
//            int yPos = panelHeight - height - 30;
//            g.setColor(new Color(11, 142, 229));
//            g.fillRect(xPos, yPos, width, height);
//        }
    }

    public void drawSelection(Graphics g){
        int panelWidth = jpStockVisualizer.getWidth();
        int panelHeight = jpStockVisualizer.getHeight() - 20;

//        for (int i = 0; i < randomizedArray.length; i++) {
//            int height = (int)((randomizedArray[i] / 100.0) * (panelHeight * 0.8));
//            int xPos = 20 + (i * barWidth) + 2;
//            int yPos = panelHeight - height - 30;
//            if(i > randomizedArray[randomizedArray.length - 3]){
//                g.setColor(new Color(255, 119, 0));
//            }else if(i == randomizedArray[randomizedArray.length - 3]){
//                g.setColor(new Color(11, 142, 229));
//                g.fillOval(xPos, yPos, width, width);
//            }else{
//                g.setColor(new Color(11, 142, 229));
//            }
//            g.fillRect(xPos, yPos, width, height);
//        }
    }
}
