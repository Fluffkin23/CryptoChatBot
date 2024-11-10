package com.application.chatbot.Service;

import com.application.chatbot.dto.CoinDTO;
import com.application.chatbot.response.APIResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ChatbotServiceImpl implements ChatbotService {

    String GEMINI_API_KEY ="AIzaSyCVIMKSe0MAVizuEA2uyNtXbJ1gvl37F2c";
    private double convertToDouble(Object value){
        if(value instanceof Integer){
            return ((Integer)value).doubleValue();
        }
        else if(value instanceof Long){
            return ((Long)value).doubleValue();
        }
        else if(value instanceof Double){
            return (Double)value;
        }
        else throw new IllegalArgumentException("Unsupported type" + value.getClass().getName());
    }


    public CoinDTO makeAPIRequest(String currencyName) throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/bitcoin";

        // for making http api request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // no body because we are doing get request
        HttpEntity<String> entity = new HttpEntity<>( headers);
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
        Map<String, Object> responseBody = responseEntity.getBody();

        if(responseBody !=null){
            Map<String, Object> image = (Map<String, Object>) responseBody.get("image");
            Map<String, Object> marketData = (Map<String, Object>) responseBody.get("market_data");

            CoinDTO coinDTO = new CoinDTO();
            coinDTO.setId((String)responseBody.get("id"));
            coinDTO.setName((String)responseBody.get("name"));
            coinDTO.setSymbol((String)responseBody.get("symbol"));
            coinDTO.setImage((String)image.get("large"));

//            // market data
           coinDTO.setCurrentPrice(convertToDouble(((Map<String,Object>)marketData.get("current_price")).get("usd")));
            coinDTO.setMarketCap(convertToDouble(((Map<String,Object>)marketData.get("market_cap")).get("usd")));
            coinDTO.setMarketCapRank(convertToDouble((marketData.get("market_cap_rank"))));
            coinDTO.setTotalVolume(convertToDouble(((Map<String,Object>)marketData.get("total_volume")).get("usd")));
            coinDTO.setHigh24h(convertToDouble(((Map<String,Object>)marketData.get("high_24h")).get("usd")));
            coinDTO.setLow24h(convertToDouble(((Map<String,Object>)marketData.get("low_24h")).get("usd")));
            coinDTO.setPriceChange24h(convertToDouble((marketData.get("price_change_24h"))));
            coinDTO.setPriceChangePercentage24h(convertToDouble((marketData.get("price_change_percentage_24h"))));
            coinDTO.setMarketCapChange24h(convertToDouble((marketData.get("market_cap_change_24h"))));
            coinDTO.setMarketCapChangePercentage24h(convertToDouble((marketData.get("market_cap_change_percentage_24h"))));
            coinDTO.setCirculatingSupply(convertToDouble((marketData.get("circulating_supply"))));
            coinDTO.setTotalSupply(convertToDouble((marketData.get("total_supply"))));

            return coinDTO;
        }
        throw new Exception("Coin not found");
    }

    @Override
    public APIResponse getCoinDetails(String prompt) throws Exception {

        CoinDTO coinDTO = makeAPIRequest(prompt);
        System.out.println("Coin dto ------------------- " + coinDTO);
        return null;
    }

    // ask whatever questions
    @Override
    public String simpleChat(String prompt) {
        String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + GEMINI_API_KEY;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = new JSONObject()
                .put("contents", new JSONArray()
                    .put(new JSONObject()
                        .put("parts", new JSONArray()
                                .put(new JSONObject()
                                        .put("text",prompt))))).toString();

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(GEMINI_API_URL,requestEntity,String.class);

        return response.getBody();
    }
}
