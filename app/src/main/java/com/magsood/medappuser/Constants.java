package com.magsood.medappuser;

public class Constants {
static String domain = "Https://www.maqsood.com.sd/api/v1/user/";
static String domain2 = "http://api.dawakom.com.sd/api/v1/user/";

    public static final String RESET_PASSWORD = domain2+"resetPassword";
    public static final String NEW_PASSWORD = domain2+"newPassword";


    public static  String REGISTRATION_URL =domain2+"registerUser";
    public static  String LOGIN_URL =domain2+"login";
    public static  String PROFILE_URL =domain2+"userProfile";
    public static  String LOGOUT_URL =domain2+"logout";
    public static  String SEARCH_URL =domain2+"search";
    public static  String SEND_REQUEST_URL =domain2+"order";

    public static String GET_MEDICINE = domain2+"medicines";
    public static String VerificationNumber = domain2+"sendCode";
    public static String SendVerificationNumber = "http://api.dawakom.com.sd/api/v1/user/verifyAccount";
    public static String PROCESS_ORDERS = "http://api.dawakom.com.sd/api/v1/user/userRecentOrders";


    public static String PREV_ORDER = "http://api.dawakom.com.sd/api/v1/user/userPrevOrders";





}
