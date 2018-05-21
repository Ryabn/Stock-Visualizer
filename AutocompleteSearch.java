package tech.ryanqyang;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class AutocompleteSearch{
    ArrayList
    public AutocompleteSearch(){
    }

    /**
     * parses a csv list of stocks with the pattern
     * "SYM","STOCK COMPANY NAME"....\n
     * "SYB","STOCK COMPANY NAME2"....\n
     */
    public void parse(){
        File f = new File("/Users/ryanyang/Desktop/Workspace/CS003B/StockVisualizer/src/tech/ryanqyang/stocks.txt");
        try(PrintWriter p = new PrintWriter(new File("/Users/ryanyang/Desktop/Workspace/CS003B/StockVisualizer/src/tech/ryanqyang/stocksList.txt"));){
            try(Scanner sc = new Scanner(f)){
                sc.nextLine();
                while(sc.hasNext()){
                    String line = sc.nextLine();
                    int counter = 0;
                    int secondIndex = 0;
                    for(int i = 2; i < line.length(); i++){
                        if(line.charAt(i) == '"'){
                            if(counter == 0){
                                p.print(line.substring(1, i) + " - ");
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
}
