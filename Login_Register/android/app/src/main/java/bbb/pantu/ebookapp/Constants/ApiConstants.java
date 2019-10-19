package bbb.pantu.ebookapp.Constants;

public class ApiConstants {

    public static String URL = "http://192.168.1.3/";

    private static String API_KEY = "123456";


    public static String LOGIN = URL + "login?&apikey="+API_KEY+"";

    public static String REGISTER = URL + "register?&apikey="+API_KEY+"";

    public static String VERIFY = URL + "verify?&apikey="+API_KEY+"";

    public static String FORGOTPASS = URL + "forgotpassword?&apikey="+API_KEY+"";

    public static String ABOUT_APP = URL + "api/app-about?&apikey="+API_KEY+"";

    public static String API_STATUS = URL + "api/status?&apikey="+API_KEY+"";

}
