package edu.vt.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.vt.globals.Constants;
import edu.vt.models.Dish;

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

@WebServlet("/nutritionServlet")
public class NutritionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        String dishName = request.getParameter("dishName");
        String ingredients = request.getParameter("ingredients");

        Dish dish = new Dish(dishName, ingredients);
        ObjectMapper Obj = new ObjectMapper();
        String finalResponse = "No response";

        try {
            String jsonStr = Obj.writeValueAsString(dish);
            System.out.println(jsonStr);
            finalResponse = getResponseFromUrl(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter writer = response.getWriter();
        writer.println(getHtmlResponse(finalResponse));

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

    private String getHtmlResponse(String finalResponse) {
        String htmlRespone = "<html>";
        htmlRespone += "<h1>The returned Json is:<br/>";
        htmlRespone += "<p>" + finalResponse + "</p><br/>";
        htmlRespone += "</html>";

        return htmlRespone;
    }
}
