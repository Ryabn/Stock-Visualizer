package tech.ryanqyang;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class StockPortfolio extends JPanel{
    private JPanel jpControls;
    private static double invested;
    private double worth;
    private static String curSymbol;
    private static int curAmount;
    private static double curSpent;

    private JTextField jtfShareCount;
    private JTextField jtfShareValue;

    private static JLabel jlPortfolioStats;
    private static JLabel jlInvested;
    private static JLabel jlWorth;
    private static JLabel jlSharesOwned;
    private JButton jbAddToPortfolio;

    public StockPortfolio(){
        super();
        createComponents();
        readFile();
        wireComponents();
    }
    private void createComponents(){
        setBackground(new Color(25, 31, 43));
        BorderLayout borderLayout = new BorderLayout();
        GridLayout gridLayout = new GridLayout(2,4, 5, 10);

        setLayout(borderLayout);
        setBorder(new EmptyBorder(5, 150, 20, 150));

        jpControls = new JPanel();
        jpControls.setLayout(gridLayout);
        jpControls.setBackground(new Color(25, 31, 43));
        add(jpControls, borderLayout.CENTER);

        jtfShareCount = new JTextField("Amount");

        jtfShareValue = new JTextField("Price");

        jlSharesOwned = new JLabel("Stocks Owned: 0");
        jlSharesOwned.setForeground(Color.white);
        jlPortfolioStats = new JLabel("");
        jlInvested = new JLabel("Total Invested: $0");
        jlInvested.setForeground(Color.white);
        jlWorth = new JLabel("Total Value: $0");
        jlWorth.setForeground(Color.white);

        jbAddToPortfolio = new JButton("Buy/Sell");


        jpControls.add(jlSharesOwned, gridLayout);
        jpControls.add(jtfShareCount, gridLayout);
        jpControls.add(jtfShareValue, gridLayout);
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
        curAmount += amountBought;
        double priceAt = Double.parseDouble(jtfShareValue.getText());
        curSpent += (priceAt*amountBought);
        writeFile();
        readFile();
    }
    public static void readFile(){
        try {
            String s = StockGraph.getStockInfo().getSymbolName();
            curSymbol = s;
            File f = new File("src/parsed/userPortfolio.txt");
            Boolean done = false;
            try(Scanner sc = new Scanner(f)){
                invested = Double.parseDouble(sc.nextLine());
                curAmount = 0;
                curSpent = 0;
                jlPortfolioStats.setText("0%");
                while (sc.hasNextLine()){
                    String temp = sc.next();
                    if(temp.equals(StockGraph.getStockInfo().getSymbolName())){
                        curSymbol = temp;
                        curAmount = Integer.parseInt(sc.next());
                        curSpent = Double.parseDouble(sc.next());
                        done = true;
                        break;
                    }else{
                        sc.next();
                        sc.next();
                        continue;
                    }
                }
                if(!done){
                    System.out.println(curSymbol);
                    jlSharesOwned.setText("Stocks Owned: 0");
                    jlInvested.setText("Total Invested: $0");
                    jlWorth.setText("Total Value: $0");
                }
                jlSharesOwned.setText("Stocks Owned: " + curAmount);
                jlInvested.setText("Total Invested: $" + curSpent);
                double currentValueWorth = (curAmount * StockGraph.getStockInfo().getPriceList().get(StockGraph.getStockInfo().getPriceList().size()-1));
                jlWorth.setText("Total Value: $" + currentValueWorth);
                double percent = currentValueWorth/curSpent;
                jlPortfolioStats.setText( String.valueOf(percent).substring(0, 5) + "%");
                if(percent > 1){
                    jlPortfolioStats.setForeground(Color.GREEN);
                }else{
                    jlPortfolioStats.setForeground(Color.RED);
                }
            }catch(Exception e){
            }
        }catch(NullPointerException e){
            jlSharesOwned.setText("Stocks Owned: 0");
            jlInvested.setText("Total Invested: $0");
        }


    }
    private void writeFile(){
        File f = new File("src/parsed/userPortfolio.txt");
        String complete = "";
        Boolean added = false;

        try(Scanner sc = new Scanner(f)) {
            complete += sc.nextLine() + "\n";
            while (sc.hasNextLine()) {
                String tempLine = sc.nextLine();
                if (tempLine.substring(0, curSymbol.length()).equals(curSymbol)) {
                    tempLine = curSymbol + " " + curAmount + " " + curSpent;
                    added = true;
                }
                complete += tempLine + "\n";
            }
            if (!added) {
                complete += curSymbol + " " + curAmount + " " + curSpent;
            }
        } catch (Exception e) {
        }
        try (PrintWriter pr = new PrintWriter(f)) {
            pr.print(complete);
        }catch (Exception e) {
        }
    }
}
