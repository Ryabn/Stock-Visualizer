package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class StockPortfolio extends JPanel{
    private JPanel jpControls;
    private double invested;
    private double worth;

    private JTextField jtfShareCount;
    private JTextField jtfShareValue;

    private JLabel jlPortfolioStats;
    private JLabel jlInvested;
    private JLabel jlWorth;
    private JLabel jlSharesOwned;
    private JButton jbAddToPortfolio;

    public StockPortfolio(){
        super();
        createComponents();
        wireComponents();
    }
    private void createComponents(){
        setBackground(new Color(25, 31, 43));6
        BorderLayout borderLayout = new BorderLayout();
        GridLayout gridLayout = new GridLayout(2,3);

        setLayout(borderLayout);

        jpControls = new JPanel();
        jpControls.setLayout(gridLayout);
        jpControls.setBackground(new Color(25, 31, 43));
        add(jpControls, borderLayout.CENTER);

        jtfShareCount = new JTextField();
        jbAddToPortfolio = new JButton("Add");
        jtfShareValue = new JTextField();
        jlSharesOwned = new JLabel("3");
        jlPortfolioStats = new JLabel("");
        jlInvested = new JLabel("Total Invested: ");
        jlWorth = new JLabel("Total Value: ");

        jpControls.add(jlSharesOwned, gridLayout);
        jpControls.add(jtfShareCount, gridLayout);
        jpControls.add(jbAddToPortfolio, gridLayout);
        jpControls.add(jlInvested, gridLayout);
        jpControls.add(jlWorth, gridLayout);
        jpControls.add(jlPortfolioStats, gridLayout);
    }
    private void wireComponents(){
        jbAddToPortfolio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStocks();
            }
        });
    }

    private void addStocks(){
        int amountBought = Integer.parseInt(jtfShareCount.getText());
        invested += (amountBought * StockGraph.getStockInfo().getPriceList().get(StockGraph.getStockInfo().getPriceList().size()-1));
    }

}
