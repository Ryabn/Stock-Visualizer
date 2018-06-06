package tech.ryanqyang;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class AutocompleteSearch extends JTextField{
    private TreeMap<String, String> companyToSymbol;

    public AutocompleteSearch(){
        super("Search Stock...");
        companyToSymbol = new TreeMap<>();
        loadAll();
    }

    /**
     * Sends user data to be analyzed and returns a string array for the list to parse
     * @param userInput
     * @return
     */
    public String[] updateSuggestions(String userInput){
        return getSameStart(userInput).toArray(new String[0]);
    }
    /**
     * Checks the entire map for its values and its keys to see if any of them match the user input
     * It returns a maximum of 30 values so it doesn't choke up the ui
     *
     * Problem: Using tree maps since strings are comparable and should yield a faster search
     * Will use 2 tree maps with the values and the keys mapping each other
     * When searching, will go down left subtree and find the smallest value that is still equal to user input
     * Then include values until it reaches the maximum value that has the user input
     *
     * @param userInput
     * @return
     */
    public ArrayList<String> getSameStart(String userInput){
        int count1 = 0;
        int count2 = 0;
        int len = userInput.length();
        ArrayList<String> suggestions = new ArrayList<>();
        for (Map.Entry<String, String> details : companyToSymbol.entrySet()){
            if( !( details.getKey().length() < len ) && count1 <= 15){
                if( details.getKey().substring(0, len).toLowerCase().equals(userInput.toLowerCase()) ){
                    suggestions.add(details.getValue() + " - " + details.getKey());
                    count1++;
                    continue;
                }
            }
            if( !( details.getValue().length() < len ) && count2 <= 15 ){
                if( details.getValue().substring(0, len).toLowerCase().equals(userInput.toLowerCase()) ){
                    suggestions.add(details.getValue() + " - " + details.getKey());
                    count2++;
                }
            }
        }
        return suggestions;
    }

    /**
     * parses a csv list of stocks with the pattern
     * "SYM","STOCK COMPANY NAME"....\n
     * "SYB","STOCK COMPANY NAME2"....\n
     */
    public void parse(){
        File f = new File("src/parsed/stocks2.txt");
        try(PrintWriter p = new PrintWriter(new File("src/parsed/stocksList2.txt"));){
            try(Scanner sc = new Scanner(f)){
                sc.nextLine();
                while(sc.hasNext()){
                    String line = sc.nextLine();
                    int counter = 0;
                    int secondIndex = 0;
                    for(int i = 2; i < line.length(); i++){
                        if(line.charAt(i) == '"'){
                            if(counter == 0){
                                p.print(line.substring(1, i) + ":");
                                counter = 1;
                            }else if(counter == 1){
                                secondIndex = i + 1;
                                counter = 2;
                            }else{
                                p.println(line.substring(secondIndex, i));
                                break;
                            }
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads both nasdaq and nyse stocks into the program
     *
     */
    public void loadAll(){
        File listFile = new File("src/parsed/stocksList.txt");
        File listFile2 = new File("src/parsed/stocksList2.txt");
        retrieveList(listFile);
        retrieveList(listFile2);
    }

    /**
     * Takes formatted stocks and symbols from generated file from parse() and puts them inside 2 tree maps
     * they map the stocks together in sorted order so autocomplete can work best
     *
     */
    public void retrieveList(File listFile){
        try(Scanner sc = new Scanner(listFile)){
            while(sc.hasNext()) {
                String stockMeta = sc.nextLine();
                findColon:
                for (int i = 0; i < stockMeta.length(); i++) {
                    if (stockMeta.charAt(i) == ':') {
                        //reassigning string creates a new reference but old reference is kept because of the maps
                        String symb = stockMeta.substring(0, i).trim();
                        String company = stockMeta.substring(i + 1);
                        companyToSymbol.put(company, symb);
                        break findColon;
                    }
                }
            }
        }catch(IOException e){
            //shouldn't be triggered unless user removed program generated list from parse
            e.printStackTrace();
        }
    }
}
