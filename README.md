# SemesterProject-HealthApp

index.html -> Landing Page <br>
NutritionServlet -> Hitting the API <br>
Dish -> POJO. Holds dish objects. <br>
Constants -> Constant keywords <br>

# Functionality
The index page has fields for input. When the button is clicked (form is submitted), it raises a post action which is picked by the nutrition servlet. <br>
The nutrition servlet then creates a a request to the API, details of which are stored in Constants.java, some basic data parsing is done and java.net.http <br>
package is used to send request and get response. We won't use this setup for the final project but this is a good setup to test our APIs in a Java EE set-up. <br>

# Set-UP
1. Clone this repo. <br>
2. Open project in IntelliJ. <br>
3. Build and Run.
