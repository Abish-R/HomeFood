package abish.veettusorudemo.constants;

/**
 * Created by Abish on 8/12/2017.
 * </p>
 */

public class UrlConstants {

    private static String BASE_URL = "http://webservice.budeis.com/hotel_api/";
    public static String GET_LOCATION_URL = BASE_URL + "getlocations.php";
    public static String GET_CATEGORY_URL = BASE_URL + "getcategoryandtimings.php";
    public static String GET_MAIN_FOOD_URL = BASE_URL + "getmaincourseandoffers.php";
    public static String GET_SUB_FOOD_URL = BASE_URL + "getsubcourseandoffers.php";
    public static String GET_SIGN_UP_URL = BASE_URL + "usersignup.php";
    public static String GET_LOGIN_URL = BASE_URL + "userlogin.php";
    public static String GET_FAV_FOOD_URL = BASE_URL + "addtofavourite.php";
    public static String GET_ADDRESS_URL = BASE_URL + "getuseraddresses.php";
    public static String GET_ADDRESS_UPDATE_URL = BASE_URL + "addorupdateaddress.php";
    public static String GET_ADDRESS_DELETE_URL = BASE_URL + "deletetemproryaddress.php";
    public static String SEND_ORDERS_URL = BASE_URL + "addOrder.php?";
    public static String GET_USER_ORDERS_URL = BASE_URL + "get_user_orders.php?";
    public static String GET_MY_FAV_FOOD_URL = BASE_URL + "all_favorite.php";

    //Url Params
    public static String MAIN_FOOD_PARAM_FOOD_CATEGORY = "?food_category=";
    public static String MAIN_FOOD_PARAM_USER_ID = "&user_id=";

    public static String SUB_FOOD_PARAM_MAIN_ID = "?main_course_id=";
    public static String SUB_FOOD_PARAM_USER_ID = "&user_id=";

    public static String SIGN_UP_PARAM_NAME = "?name=";
    public static String SIGN_UP_PARAM_PASSWORD = "&password=";
    public static String SIGN_UP_PARAM_EMAIL = "&email=";
    public static String SIGN_UP_PARAM_MOBILE = "&mobile=";
    public static String SIGN_UP_PARAM_STREET = "&street=";
    public static String SIGN_UP_PARAM_CITY = "&city=";
    public static String SIGN_UP_PARAM_STATE = "&state=";
    public static String SIGN_UP_PARAM_Country = "&country=";
    public static String SIGN_UP_PARAM_PINCODE = "&pincode=";
    public static String SIGN_UP_PARAM_ADDRESS_FLAG = "&address_flag=";
    public static String SIGN_UP_PARAM_DEVICE_TOKEN = "&deviceToken=";

    public static String LOGIN_PARAM_USERNAME = "?user_name=";
    public static String LOGIN_PARAM_PASSWORD = "&password=";
    public static String LOGIN_PARAM_DEVICE_TOKEN = "&deviceToken=";

    public static String FAV_FOOD_PARAM_USER_ID = "?user_id=";
    public static String FAV_FOOD_PARAM_COURSE_ID = "&course_id=";
    public static String FAV_FOOD_PARAM_COURSE_TYPE = "&course_type=";

    public static String ADDRESS_USER_ID = "?user_id=";
    public static String ADDRESS_CATEGORY_ID = "&category_id=";

    public static String ADDRESS_UPDATE_USER_ID = "?user_id=";
    public static String ADDRESS_UPDATE_STREET = "&street=";
    public static String ADDRESS_UPDATE_CITY = "&city=";
    public static String ADDRESS_UPDATE_STATE = "&state=";
    public static String ADDRESS_UPDATE_COUNTRY = "&country=";
    public static String ADDRESS_UPDATE_PIN_CODE = "&pincode=";
    public static String ADDRESS_UPDATE_FLAG = "&address_flag=";
    public static String ADDRESS_UPDATE_ID = "&address_id=";

    public static String ADDRESS_DELETE_USER_ID = "?user_id=";
    public static String ADDRESS_DELETE_TEMP_ID = "&temp_add_id=";

    public static String ORDERS_DATA = "orderData=";
    public static String ORDERS_CUSTOMER_ID = "customer_id=";
}
