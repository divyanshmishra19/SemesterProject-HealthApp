package edu.vt.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.vt.globals.Constants;
import edu.vt.payloads.RecipePayload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@WebServlet("/nutritionServlet")
public class NutritionServlet extends HttpServlet {

    private static final String BAR_CHART = "bvg";
    private static final String PIE_CHART = "p3";


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String dishName = request.getParameter("dishName");
        String ingredients = request.getParameter("ingredients");

        RecipePayload dish = new RecipePayload(dishName, ingredients);
        ObjectMapper Obj = new ObjectMapper();
        String finalResponse = "No response";

        try {
            String jsonStr = Obj.writeValueAsString(dish);
            System.out.println(jsonStr);
            finalResponse = getResponseFromUrl(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> imageList = getImageUrl(finalResponse);

        PrintWriter writer = response.getWriter();
        writer.println(getHtmlResponse(imageList));

    }

    private List<String> getImageUrl(String finalResponse) throws IOException {
        if (finalResponse.equals("No response"))
            return Collections.emptyList();

        List<String> urlList = new ArrayList<>();
        try {
            Map<String, Map<String, Map<String, Object>>> result = new ObjectMapper().readValue(finalResponse, HashMap.class);
            Map<String, Map<String, Object>> totalNutrients = result.get("totalNutrients");
            Map<String, Map<String, Object>> totalDaily = result.get("totalDaily");

            //Getting the barchart for first 5 fields
            int i = 0;
            StringBuilder barchartUrl = new StringBuilder();
            barchartUrl.append(Constants.CHART_API_URL);
            barchartUrl.append(BAR_CHART);
            barchartUrl.append("&chs=500x500");
            barchartUrl.append("&chbr=20");
            barchartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE");
            String chd = "&chd=t:";
            String chl = "&chl=";
            for (Map.Entry<String, Map<String, Object>> nutrients : totalNutrients.entrySet()) {
                Map<String, Object> nutrient = nutrients.getValue();
                if(nutrient.get("label").toString().equals("Energy"))
                    continue;
                chd += nutrient.get("quantity").toString() + ",";
                chl += nutrient.get("label").toString() + "|";
                i++;
                if(i==5)
                    break;
            }
            barchartUrl.append(chd.substring(0,chd.length()-1));
            barchartUrl.append(chl.substring(0,chl.length()-1));
            System.out.println(barchartUrl);
            urlList.add(barchartUrl.toString());

            //Getting the pie chart for first 5 fields
            i = 0;
            StringBuilder pieChartUrl = new StringBuilder();
            pieChartUrl.append(Constants.CHART_API_URL);
            pieChartUrl.append(PIE_CHART);
            pieChartUrl.append("&chs=700x500");
            pieChartUrl.append("&chco=FFC6A5|FFFF42|DEF3BD|00A5C6|DEBDDE");
            chd = "&chd=t:";
            chl = "&chl=";
            for (Map.Entry<String, Map<String, Object>> nutrients : totalDaily.entrySet()) {
                Map<String, Object> nutrient = nutrients.getValue();
                if(nutrient.get("label").toString().equals("Energy"))
                    continue;
                chd += nutrient.get("quantity").toString() + ",";
                chl += nutrient.get("label").toString() + "|";
                i++;
                if(i==5)
                    break;
            }
            pieChartUrl.append(chd.substring(0,chd.length()-1));
            pieChartUrl.append(chl.substring(0,chl.length()-1));
            System.out.println(barchartUrl);
            urlList.add(pieChartUrl.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlList;
    }

    private String getResponseFromUrl(String jsonStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String TARGET = "https://api.edamam.com/api/nutrition-details?app_id=" + Constants.API_ID + "&app_key=" + Constants.API_KEY;
            URI uri = new URI(TARGET);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonStr))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No response";
    }

    private String getHtmlResponse(List<String> finalResponse) {
        String htmlRespone = "<html>";
        htmlRespone += "<h1>The visualised nutrition chart is:<br/>";
        htmlRespone += "<img src=" + finalResponse.get(0) +" alt= Nutrition width=\"500\" height=\"500\">";
        htmlRespone += "<h1>The daily nutrition chart is:<br/>";
        htmlRespone += "<img src=" + finalResponse.get(1) +" alt= Nutrition width=\"500\" height=\"500\">";
        htmlRespone += "</html>";

        return htmlRespone;
    }
}
