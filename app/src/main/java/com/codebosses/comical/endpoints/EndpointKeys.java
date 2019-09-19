package com.codebosses.comical.endpoints;

public interface EndpointKeys {

    //    Adapter click keys....
    String HOME_COMIC_CLICK = "home_comic_click";
    String SEARCH_COMIC_CLICK = "search_comic_click";
    String COMIC_DETAIL_COMIC_CLICK = "comic_detail_comic_click";

    //    Intent keys....
    String COMIC = "comic_model";
    String COMIC_URL = "comic_url";
    String COMIC_NAME = "comic_name";

    //    Shared preference keys....
    String IS_USER_FIRST_TIME = "is_user_first_time";
    String USER_NAME = "user_name";
    String USER_EMAIL = "user_email";
    String USER_PASSWORD = "user_password";

    //    Firebase table keys....
    String USERS = "Users";
    String COMICS = "Comics";
    String CHAPTERS = "Chapters";

    //    Facebook permission keys....
    String EMAIL = "email";
    String PUBLIC_PROFILE = "public_profile";

}