package abish.veettusorudemo.constants;

/**
 * Created by Abish on 8/12/2017.
 */

public class UrlConstants {

    private static String BASE_URL = "http://httest.in/hotelws/";
    public static String GET_LOCATION_URL = BASE_URL + "getlocations.php";
    public static String GET_CATEGORY_URL = BASE_URL + "getcategoryandtimings.php";
    public static String GET_MAIN_FOOD_URL = BASE_URL + "getmaincourseandoffers.php";
    public static String GET_SUB_FOOD_URL = BASE_URL + "getsubcourseandoffers.php";
    public static String GET_SIGN_UP_URL = BASE_URL + "usersignup.php";
    public static String GET_LOGIN_URL = BASE_URL + "userlogin.php";
    public static String GET_FAV_FOOD_URL = BASE_URL + "addtofavourite.php";

    //Url Params
    public static String MAIN_FOOD_PARAM_FOOD_CATEGORY = "?food_category=";
    public static String MAIN_FOOD_PARAM_USER_ID = "&user_id=";

    public static String SUB_FOOD_PARAM_MAIN_ID = "?main_course_id=";
    public static String SUB_FOOD_PARAM_USER_ID = "&user_id=";

    public static String SIGN_UP_PARAM_FIRST_NAME = "?first_name=";
    public static String SIGN_UP_PARAM_LAST_NAME = "&last_name=";
    public static String SIGN_UP_PARAM_USER_NAME = "&user_name=";
    public static String SIGN_UP_PARAM_PASSWORD = "&password=";
    public static String SIGN_UP_PARAM_EMAIL = "&email=";
    public static String SIGN_UP_PARAM_MOBILE = "&mobile=";
    public static String SIGN_UP_PARAM_STREET = "&street=";
    public static String SIGN_UP_PARAM_CITY = "&city=";
    public static String SIGN_UP_PARAM_STATE = "&state=";
    public static String SIGN_UP_PARAM_Country = "&country=";
    public static String SIGN_UP_PARAM_PINCODE = "&pincode=";
    public static String SIGN_UP_PARAM_DEVICE_TOKEN = "&deviceToken=";

    public static String LOGIN_PARAM_USERNAME = "?user_name=";
    public static String LOGIN_PARAM_PASSWORD = "&password=";
    public static String LOGIN_PARAM_DEVICE_TOKEN = "&deviceToken=";

    //TODO - Change the hardcoded values
    public static String FAV_FOOD_PARAM_USER_ID = "?user_id=2";
    public static String FAV_FOOD_PARAM_COURSE_ID = "&course_id=1";
    public static String FAV_FOOD_PARAM_COURSE_TYPE = "&course_type=1";
}
