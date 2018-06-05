package tech.ryanqyang;

import javax.swing.*;
import java.awt.*;

public class StockPortfolio extends JPanel{
    private JPanel jpControls;

    private JTextField jtfShareCount;
    private JTextField jtfShareValue;

    private JLabel jlPortfolioStats;
    private JButton jbAddToPortfolio;

    public StockPortfolio(){
        super();
        createComponents();
        wireComponents();
    }
    private void createComponents(){
        setBackground(new Color(25, 31, 43));
        BorderLayout borderLayout = new BorderLayout();
        GridLayout gridLayout = new GridLayout(2,2);

        setLayout(borderLayout);


        jpControls = new JPanel();
        jpControls.setLayout(gridLayout);


    }
    private void wireComponents(){

    }

}
