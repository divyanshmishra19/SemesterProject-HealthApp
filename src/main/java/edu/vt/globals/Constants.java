package edu.vt.globals;

public class Constants {

    public static final String API_URL = "https://api.edamam.com/api/nutrition-details";
    public static final String API_ID = "772949eb";
    public static final String API_KEY = "f46ba1f1fed8cbf37a4c450f21b9dc1d";
    public static final String PHOTOS_ABSOLUTE_PATH = "C:/Users/hp/DocRoot/PhotoStorage/";
    public static final String API_APP_NAME = "API signup";
    public static final String CHART_API_URL = "https://image-charts.com/chart?cht=";
    public static final String BAR_CHART = "bvg";
    public static final String PIE_CHART = "p3";
    public static final String DATA = "&chd=t:";
    public static final String LABEL = "&chl=";
    public static final String[] QUESTIONS = {
            "In what city or town did your mother and father meet?",
            "In what city or town were you born?",
            "What did you want to be when you grew up?",
            "What do you remember most from your childhood?",
            "What is the name of the boy or girl that you first kissed?",
            "What is the name of the first school you attended?",
            "What is the name of your favorite childhood friend?",
            "What is the name of your first pet?",
            "What is your mother's maiden name?",
            "What was your favorite place to visit as a child?"
    };

    /*
     United States postal state abbreviations (codes)
     */
    public static final String[] STATES = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT",
            "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA",
            "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
            "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT",
            "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    public static final String PHOTOS_URI = "http://localhost:8080/profilephotos/";

    public static final Integer THUMBNAIL_SIZE = 200;

    public static final String SAMPLE_JSON = "{\n" +
            "   \"uri\":\"http://www.edamam.com/ontologies/edamam.owl#recipe_43ab2d767b17403c8d015f7788f1a8a0\",\n" +
            "   \"yield\":8.0,\n" +
            "   \"calories\":1960,\n" +
            "   \"totalWeight\":1023.7560441041956,\n" +
            "   \"dietLabels\":[\n" +
            "      \n" +
            "   ],\n" +
            "   \"healthLabels\":[\n" +
            "      \"SUGAR_CONSCIOUS\",\n" +
            "      \"KIDNEY_FRIENDLY\",\n" +
            "      \"VEGETARIAN\",\n" +
            "      \"PESCATARIAN\",\n" +
            "      \"DAIRY_FREE\",\n" +
            "      \"MILK_FREE\",\n" +
            "      \"PEANUT_FREE\",\n" +
            "      \"TREE_NUT_FREE\",\n" +
            "      \"FISH_FREE\",\n" +
            "      \"SHELLFISH_FREE\",\n" +
            "      \"PORK_FREE\",\n" +
            "      \"RED_MEAT_FREE\",\n" +
            "      \"CRUSTACEAN_FREE\",\n" +
            "      \"CELERY_FREE\",\n" +
            "      \"MUSTARD_FREE\",\n" +
            "      \"SESAME_FREE\",\n" +
            "      \"LUPINE_FREE\",\n" +
            "      \"MOLLUSK_FREE\",\n" +
            "      \"KOSHER\"\n" +
            "   ],\n" +
            "   \"cautions\":[\n" +
            "      \"FODMAP\"\n" +
            "   ],\n" +
            "   \"totalNutrients\":{\n" +
            "      \"ENERC_KCAL\":{\n" +
            "         \"label\":\"Energy\",\n" +
            "         \"quantity\":1960.7869999996706,\n" +
            "         \"unit\":\"kcal\"\n" +
            "      },\n" +
            "      \"FAT\":{\n" +
            "         \"label\":\"Fat\",\n" +
            "         \"quantity\":39.77550000000001,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"FASAT\":{\n" +
            "         \"label\":\"Saturated\",\n" +
            "         \"quantity\":5.5517900000000004,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"FATRN\":{\n" +
            "         \"label\":\"Trans\",\n" +
            "         \"quantity\":0.14328000000000002,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"FAMS\":{\n" +
            "         \"label\":\"Monounsaturated\",\n" +
            "         \"quantity\":21.680819999999997,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"FAPU\":{\n" +
            "         \"label\":\"Polyunsaturated\",\n" +
            "         \"quantity\":10.644720000000001,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"CHOCDF\":{\n" +
            "         \"label\":\"Carbs\",\n" +
            "         \"quantity\":345.01569999998765,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"FIBTG\":{\n" +
            "         \"label\":\"Fiber\",\n" +
            "         \"quantity\":8.096,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"SUGAR\":{\n" +
            "         \"label\":\"Sugars\",\n" +
            "         \"quantity\":19.0986,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"PROCNT\":{\n" +
            "         \"label\":\"Protein\",\n" +
            "         \"quantity\":44.85114999999877,\n" +
            "         \"unit\":\"g\"\n" +
            "      },\n" +
            "      \"CHOLE\":{\n" +
            "         \"label\":\"Cholesterol\",\n" +
            "         \"quantity\":319.92,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"NA\":{\n" +
            "         \"label\":\"Sodium\",\n" +
            "         \"quantity\":2377.919573999428,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"CA\":{\n" +
            "         \"label\":\"Calcium\",\n" +
            "         \"quantity\":167.61695058505367,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"MG\":{\n" +
            "         \"label\":\"Magnesium\",\n" +
            "         \"quantity\":235.39506044102964,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"K\":{\n" +
            "         \"label\":\"Potassium\",\n" +
            "         \"quantity\":1653.3139835282939,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"FE\":{\n" +
            "         \"label\":\"Iron\",\n" +
            "         \"quantity\":7.362829945544411,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"ZN\":{\n" +
            "         \"label\":\"Zinc\",\n" +
            "         \"quantity\":6.927516044104393,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"P\":{\n" +
            "         \"label\":\"Phosphorus\",\n" +
            "         \"quantity\":786.4329999999851,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"VITA_RAE\":{\n" +
            "         \"label\":\"Vitamin A\",\n" +
            "         \"quantity\":426.78999999999996,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"VITC\":{\n" +
            "         \"label\":\"Vitamin C\",\n" +
            "         \"quantity\":310.21399999999994,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"THIA\":{\n" +
            "         \"label\":\"Thiamin (B1)\",\n" +
            "         \"quantity\":0.55828,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"RIBF\":{\n" +
            "         \"label\":\"Riboflavin (B2)\",\n" +
            "         \"quantity\":0.88069,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"NIA\":{\n" +
            "         \"label\":\"Niacin (B3)\",\n" +
            "         \"quantity\":10.2462,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"VITB6A\":{\n" +
            "         \"label\":\"Vitamin B6\",\n" +
            "         \"quantity\":1.8183,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"FOLDFE\":{\n" +
            "         \"label\":\"Folate equivalent (total)\",\n" +
            "         \"quantity\":207.23999999999998,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"FOLFD\":{\n" +
            "         \"label\":\"Folate (food)\",\n" +
            "         \"quantity\":207.23999999999998,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"FOLAC\":{\n" +
            "         \"label\":\"Folic acid\",\n" +
            "         \"quantity\":0.0,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"VITB12\":{\n" +
            "         \"label\":\"Vitamin B12\",\n" +
            "         \"quantity\":0.7654,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"VITD\":{\n" +
            "         \"label\":\"Vitamin D\",\n" +
            "         \"quantity\":1.72,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"TOCPHA\":{\n" +
            "         \"label\":\"Vitamin E\",\n" +
            "         \"quantity\":9.0647,\n" +
            "         \"unit\":\"mg\"\n" +
            "      },\n" +
            "      \"VITK1\":{\n" +
            "         \"label\":\"Vitamin K\",\n" +
            "         \"quantity\":79.91999999999999,\n" +
            "         \"unit\":\"µg\"\n" +
            "      },\n" +
            "      \"WATER\":{\n" +
            "         \"label\":\"Water\",\n" +
            "         \"quantity\":580.007112088016,\n" +
            "         \"unit\":\"g\"\n" +
            "      }\n" +
            "   },\n" +
            "   \"totalDaily\":{\n" +
            "      \"ENERC_KCAL\":{\n" +
            "         \"label\":\"Energy\",\n" +
            "         \"quantity\":98.03934999998353,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"FAT\":{\n" +
            "         \"label\":\"Fat\",\n" +
            "         \"quantity\":61.19307692307693,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"FASAT\":{\n" +
            "         \"label\":\"Saturated\",\n" +
            "         \"quantity\":27.758950000000006,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"CHOCDF\":{\n" +
            "         \"label\":\"Carbs\",\n" +
            "         \"quantity\":115.00523333332922,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"FIBTG\":{\n" +
            "         \"label\":\"Fiber\",\n" +
            "         \"quantity\":32.384,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"PROCNT\":{\n" +
            "         \"label\":\"Protein\",\n" +
            "         \"quantity\":89.70229999999754,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"CHOLE\":{\n" +
            "         \"label\":\"Cholesterol\",\n" +
            "         \"quantity\":106.64,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"NA\":{\n" +
            "         \"label\":\"Sodium\",\n" +
            "         \"quantity\":99.07998224997617,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"CA\":{\n" +
            "         \"label\":\"Calcium\",\n" +
            "         \"quantity\":16.761695058505367,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"MG\":{\n" +
            "         \"label\":\"Magnesium\",\n" +
            "         \"quantity\":56.04644296214991,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"K\":{\n" +
            "         \"label\":\"Potassium\",\n" +
            "         \"quantity\":35.176893266559446,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"FE\":{\n" +
            "         \"label\":\"Iron\",\n" +
            "         \"quantity\":40.90461080858006,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"ZN\":{\n" +
            "         \"label\":\"Zinc\",\n" +
            "         \"quantity\":62.97741858276721,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"P\":{\n" +
            "         \"label\":\"Phosphorus\",\n" +
            "         \"quantity\":112.3475714285693,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"VITA_RAE\":{\n" +
            "         \"label\":\"Vitamin A\",\n" +
            "         \"quantity\":47.42111111111111,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"VITC\":{\n" +
            "         \"label\":\"Vitamin C\",\n" +
            "         \"quantity\":344.68222222222215,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"THIA\":{\n" +
            "         \"label\":\"Thiamin (B1)\",\n" +
            "         \"quantity\":46.52333333333334,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"RIBF\":{\n" +
            "         \"label\":\"Riboflavin (B2)\",\n" +
            "         \"quantity\":67.74538461538461,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"NIA\":{\n" +
            "         \"label\":\"Niacin (B3)\",\n" +
            "         \"quantity\":64.03875,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"VITB6A\":{\n" +
            "         \"label\":\"Vitamin B6\",\n" +
            "         \"quantity\":139.86923076923077,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"FOLDFE\":{\n" +
            "         \"label\":\"Folate equivalent (total)\",\n" +
            "         \"quantity\":51.80999999999999,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"VITB12\":{\n" +
            "         \"label\":\"Vitamin B12\",\n" +
            "         \"quantity\":31.891666666666666,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"VITD\":{\n" +
            "         \"label\":\"Vitamin D\",\n" +
            "         \"quantity\":11.466666666666667,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"TOCPHA\":{\n" +
            "         \"label\":\"Vitamin E\",\n" +
            "         \"quantity\":60.431333333333335,\n" +
            "         \"unit\":\"%\"\n" +
            "      },\n" +
            "      \"VITK1\":{\n" +
            "         \"label\":\"Vitamin K\",\n" +
            "         \"quantity\":66.6,\n" +
            "         \"unit\":\"%\"\n" +
            "      }\n" +
            "   },\n" +
            "   \"totalNutrientsKCal\":{\n" +
            "      \"ENERC_KCAL\":{\n" +
            "         \"label\":\"Energy\",\n" +
            "         \"quantity\":1960,\n" +
            "         \"unit\":\"kcal\"\n" +
            "      },\n" +
            "      \"PROCNT_KCAL\":{\n" +
            "         \"label\":\"Calories from protein\",\n" +
            "         \"quantity\":183,\n" +
            "         \"unit\":\"kcal\"\n" +
            "      },\n" +
            "      \"FAT_KCAL\":{\n" +
            "         \"label\":\"Calories from fat\",\n" +
            "         \"quantity\":366,\n" +
            "         \"unit\":\"kcal\"\n" +
            "      },\n" +
            "      \"CHOCDF_KCAL\":{\n" +
            "         \"label\":\"Calories from carbohydrates\",\n" +
            "         \"quantity\":1411,\n" +
            "         \"unit\":\"kcal\"\n" +
            "      }\n" +
            "   }\n" +
            "}";
}
