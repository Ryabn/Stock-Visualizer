package tech.ryanqyang;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import java.util.ArrayList;
import java.util.Collections;

public class StockParser {

    private static ArrayList<Double> priceList;
    private static ArrayList<String> intervalList;
    private static JsonObject metaData;

    private static double stockHigh;
    private static double stockLow;

    public static ArrayList<Double> getPriceList() {
        return priceList;
    }
    public static ArrayList<String> getIntervalList() {
        return intervalList;
    }
    public static double getStockHigh() {
        return stockHigh;
    }
    public static double getStockLow() {
        return stockLow;
    }

    public static void initStockParser(){
        priceList = new ArrayList<>();
        intervalList = new ArrayList<>();
    }

    /**
     * Retrieves stock information by making an API call using the APICalls.java method and gets the response in JSON
     * It then parses the JSON file and puts all the information in a Java readable format
     *
     * @param timeSeries
     * @param symbol
     * @param interval
     */
    public static void displayStockInfo( String timeSeries, String symbol, String interval ){
        JsonObject value = Json.parse(APICalls.getStockInformation(timeSeries, symbol, interval)).asObject();
        extractPrices(value);
    }

    /**
     * Parses JSON data and stores into java accessible data structures
     * @param value
     */
    public static void extractPrices(JsonObject value){
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
            String intervalAt = key.getName();
            intervalList.add(intervalAt);
            priceList.add(stockValueAt);
        }
        stockHigh = Collections.max(priceList);
        stockLow = Collections.min(priceList);
    }
}
