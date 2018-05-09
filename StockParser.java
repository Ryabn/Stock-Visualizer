package tech.ryanqyang;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;

public class StockParser {
    private static ArrayList<Double> priceList;
    private static ArrayList<String> stockList;
    private static ArrayList<String> intervalList;
    private static JsonObject value;
    private static JsonObject metaData;


    public static ArrayList<String> getStockList() {
        return stockList;
    }

    public static void initStockParser(){
        priceList = new ArrayList<>();
        stockList = new ArrayList<>();
        intervalList = new ArrayList<>();
    }

    public static void displayStockInfo( String timeSeries, String symbol, String interval ){
        String data = keys.getStockInformation(timeSeries, symbol, interval);
        value = Json.parse(data).asObject();

        extractPrices();
    }
    public static void extractPrices(){
        for (JsonObject.Member member : value) {
            String name = member.getName();
            if(name.equals("Meta Data")){
                metaData = member.getValue().asObject();
            }else{
                value = member.getValue().asObject();
                break;
            }


        }

        for(JsonObject.Member key : value.asObject()){
            Double stockValueAt =
                    Double.parseDouble(
                            key
                                    .getValue()
                                    .asObject()
                                    .getString("4. close", "0")
                    );
            priceList.add(stockValueAt);
        }
    }
}
