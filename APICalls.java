package tech.ryanqyang;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APICalls {
    /**
     * Makes an api call to Alpha Vantage to get detailed stock information about a certain stock
     * Reqiures API key hidden inside keys.java class
     *
     * @param timeSeries
     * @param symbol
     * @param interval
     * @return
     */
    public static String getStockInformation( String timeSeries, String symbol, String interval ) throws Exception{
        String url = "https://www.alphavantage.co/query" +
                "?function=" + timeSeries +
                "&symbol=" + symbol +
                "&interval=" + interval +
                "&apikey=" + keys.getAlphaVantageApiKey();
        System.out.println(url);
//        https://api.iextrading.com/1.0/stock/aapl/batch?types=quote,news,chart&range=1m&last=10
        String url2 = "https://api.iextrading.com/1.0/stock/" + symbol +
                "/batch?types=quote,news,chart&range=1m&last=10";
        return getReq(url2);
    }

    /**
     * Makes a get request at given url and returns server response in its JSON format
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static String getReq(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        System.setProperty("http.agent", "");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
} 
