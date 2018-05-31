package tech.ryanqyang;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Collections;

public class StockParser {

    private ArrayList<Double> priceList;
    private ArrayList<String> intervalList;
    private JsonArray metaData;

    private double stockHigh;
    private double stockLow;

    public ArrayList<Double> getPriceList() {
        return priceList;
    }
//    public ArrayList<String> getIntervalList() {
//        return intervalList;
//    }
//    public JsonObject getMetaData() {
//        return metaData;
//    }
    public double getStockHigh() {
        return stockHigh;
    }
    public double getStockLow() {
        return stockLow;
    }

    public StockParser( String timeSeries, String symbol, String interval ) throws Exception{
        this.priceList = new ArrayList<>();
        this.intervalList = new ArrayList<>();
        displayStockInfo(timeSeries, symbol, interval);
    }

    /**
     * Retrieves stock information by making an API call using the APICalls.java method and gets the response in JSON
     * It then parses the JSON file and puts all the information in a Java readable format
     *
     * @param timeSeries
     * @param symbol
     * @param interval
     */
    public void displayStockInfo( String timeSeries, String symbol, String interval ) throws Exception{
        JsonObject value = Json.parse(APICalls.getStockInformation(timeSeries, symbol, interval)).asObject();
        extractPrices(value);
    }

    /**
     * Parses JSON data and stores into java accessible data structures
     * @param value
     */
    public void extractPrices(JsonObject value){
        priceList.clear();
        intervalList.clear();
//        for (JsonObject.Member member : value) {
//            String name = member.getName();
//            if(name.equals("Meta Data")){
//                metaData = member.getValue().asObject();
//            }else{
//                value = member.getValue().asObject();
//                break;
//            }
//        }
//        for(JsonObject.Member key : value.asObject()){
//            Double stockValueAt =
//                    Double.parseDouble(
//                            key
//                                    .getValue()
//                                    .asObject()
//                                    .getString("4. close", "0")
//                    );
//            String intervalAt = key.getName();
//            intervalList.add(intervalAt);
//            priceList.add(stockValueAt);
//        }


        for (JsonObject.Member member : value) {
            String name = member.getName();
            if(name.equals("chart")){
                metaData = member.getValue().asArray();
            }
        }
        for(JsonValue key : metaData){
            priceList.add(key.asObject().getDouble("high", 0.0));
        }
//        stockHigh = Collections.max(priceList);
//        stockLow = Collections.min(priceList);
    }
}
