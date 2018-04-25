package com.amohnacs.faircarrental.search;

import android.content.Context;

import com.amohnacs.model.amadeus.AmadeusResult;
import com.amohnacs.model.amadeus.AmadeusResults;
import com.amohnacs.model.amadeus.Car;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AmadeusMockData {

    private static ArrayList<AmadeusResult> amadeusList;
    private static ArrayList<Car> carList;

    private static String json = "{\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"provider\": {\n" +
            "        \"company_code\": \"ZR\",\n" +
            "        \"company_name\": \"DOLLAR\"\n" +
            "      },\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 37.80676,\n" +
            "        \"longitude\": -122.41365\n" +
            "      },\n" +
            "      \"address\": {\n" +
            "        \"line1\": \"2500 MASON STREET\",\n" +
            "        \"city\": \"SAN FRANCISCO\",\n" +
            "        \"region\": \"CA\",\n" +
            "        \"country\": \"US\"\n" +
            "      },\n" +
            "      \"cars\": [\n" +
            "        {\n" +
            "          \"vehicle_info\": {\n" +
            "            \"acriss_code\": \"XXAR\",\n" +
            "            \"transmission\": \"Automatic\",\n" +
            "            \"fuel\": \"Unspecified\",\n" +
            "            \"air_conditioning\": true,\n" +
            "            \"category\": \"Special\",\n" +
            "            \"type\": \"Special\"\n" +
            "          },\n" +
            "          \"rates\": [\n" +
            "            {\n" +
            "              \"type\": \"DAILY\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"51.11\",\n" +
            "                \"currency\": \"EUR\"\n" +
            "              }\n" +
            "            }, {\n" +
            "              \"type\": \"DAILY\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"55.00\",\n" +
            "                \"currency\": \"USD\"\n" +
            "              }\n" +
            "            }\n" +
            "          ],\n" +
            "          \"estimated_total\": {\n" +
            "            \"amount\": \"113.81\",\n" +
            "            \"currency\": \"EUR\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }, {\n" +
            "      \"provider\": {\n" +
            "        \"company_code\": \"ZE\",\n" +
            "        \"company_name\": \"HERTZ\"\n" +
            "      },\n" +
            "      \"location\": {\n" +
            "        \"latitude\": 37.71667,\n" +
            "        \"longitude\": -122.2167\n" +
            "      },\n" +
            "      \"airport\": \"OAK\",\n" +
            "      \"address\": {\n" +
            "        \"line1\": \" 7600 EARHART ROAD, SUITE 1\",\n" +
            "        \"city\": \"OAKLAND AP\",\n" +
            "        \"region\": \"CA\",\n" +
            "        \"postal_code\": \"94621\",\n" +
            "        \"country\": \"US\"\n" +
            "      },\n" +
            "      \"cars\": [\n" +
            "        {\n" +
            "          \"vehicle_info\": {\n" +
            "            \"acriss_code\": \"ECAR\",\n" +
            "            \"transmission\": \"Automatic\",\n" +
            "            \"fuel\": \"Unspecified\",\n" +
            "            \"air_conditioning\": true,\n" +
            "            \"category\": \"Economy\",\n" +
            "            \"type\": \"2/4 Door\"\n" +
            "          },\n" +
            "          \"rates\": [\n" +
            "            {\n" +
            "              \"type\": \"WEEKEND\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"41.63\",\n" +
            "                \"currency\": \"EUR\"\n" +
            "              }\n" +
            "            }, {\n" +
            "              \"type\": \"WEEKEND\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"44.80\",\n" +
            "                \"currency\": \"USD\"\n" +
            "              }\n" +
            "            }\n" +
            "          ],\n" +
            "          \"estimated_total\": {\n" +
            "            \"amount\": \"83.26\",\n" +
            "            \"currency\": \"EUR\"\n" +
            "          }\n" +
            "        }, {\n" +
            "          \"vehicle_info\": {\n" +
            "            \"acriss_code\": \"PCAR\",\n" +
            "            \"transmission\": \"Automatic\",\n" +
            "            \"fuel\": \"Unspecified\",\n" +
            "            \"air_conditioning\": true,\n" +
            "            \"category\": \"Premium\",\n" +
            "            \"type\": \"2/4 Door\"\n" +
            "          },\n" +
            "          \"rates\": [\n" +
            "            {\n" +
            "              \"type\": \"WEEKEND\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"57.24\",\n" +
            "                \"currency\": \"EUR\"\n" +
            "              }\n" +
            "            }, {\n" +
            "              \"type\": \"WEEKEND\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"61.60\",\n" +
            "                \"currency\": \"USD\"\n" +
            "              }\n" +
            "            }\n" +
            "          ],\n" +
            "          \"estimated_total\": {\n" +
            "            \"amount\": \"114.48\",\n" +
            "            \"currency\": \"EUR\"\n" +
            "          }\n" +
            "        }, {\n" +
            "          \"vehicle_info\": {\n" +
            "            \"acriss_code\": \"LCAR\",\n" +
            "            \"transmission\": \"Automatic\",\n" +
            "            \"fuel\": \"Unspecified\",\n" +
            "            \"air_conditioning\": true,\n" +
            "            \"category\": \"Luxury\",\n" +
            "            \"type\": \"2/4 Door\"\n" +
            "          },\n" +
            "          \"rates\": [\n" +
            "            {\n" +
            "              \"type\": \"WEEKEND\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"91.06\",\n" +
            "                \"currency\": \"EUR\"\n" +
            "              }\n" +
            "            }, {\n" +
            "              \"type\": \"WEEKEND\",\n" +
            "              \"price\": {\n" +
            "                \"amount\": \"98.00\",\n" +
            "                \"currency\": \"USD\"\n" +
            "              }\n" +
            "            }\n" +
            "          ],\n" +
            "          \"estimated_total\": {\n" +
            "            \"amount\": \"182.12\",\n" +
            "            \"currency\": \"EUR\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    public static ArrayList<AmadeusResult> getMockResults() {
        amadeusList = new ArrayList<>();

        AmadeusResults result = new Gson().fromJson(json, AmadeusResults.class);
        amadeusList.addAll(result.getAmadeusResults());
        return amadeusList;
    }
}
