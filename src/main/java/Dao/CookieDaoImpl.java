package Dao;

import java.util.ArrayList;

public class CookieDaoImpl implements CookieDao {

    private static final int VALID_TIME=60000;

    private ArrayList<Cookie> cookies;

    private CookieDaoImpl() {
        cookies = new ArrayList<Cookie>();
    }

    private static CookieDaoImpl cookieDaoInstance = new CookieDaoImpl();

    public static CookieDaoImpl getCookieDaoInstance() {
        return cookieDaoInstance;
    }

    @Override
    public boolean isValid(String cookieStr) {
        if (cookieStr == null) return false;

        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);

            if ((cookie.toString()).equals(cookieStr)) {
                if (System.currentTimeMillis() - cookie.getTime() <= VALID_TIME) {//单位是毫秒
                    cookie.updataTime();//cookie存在且未失效，则更新cookie时间
                    return true;
                } else {
                    cookies.remove(cookie);//如果cookie存在但已失效，则删除该cookie；
                    i--;
                }
            }
        }
        return false;
    }


    @Override
    public String getNewCookie(String userName) {
        Cookie cookie = new Cookie(userName);
        if (!isValid(cookie.toString())) cookies.add(cookie);
        return cookie.toString();
    }

    @Override
    public boolean disableCookie(String cookieStr){
        boolean exist=false;//cookieStr是否存在于cookies
        for (int i = 0; i < cookies.size(); i++) {
            Cookie cookie = cookies.get(i);
            if ((cookie.toString()).equals(cookieStr)) {
                cookies.remove(cookie);//如果cookie存在则删除该cookie；
                exist=true;
                i--;
            }
        }
        return exist;
    }
}
