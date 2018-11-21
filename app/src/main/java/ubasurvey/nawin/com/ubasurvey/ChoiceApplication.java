package ubasurvey.nawin.com.ubasurvey;


import android.app.Application;

public class ChoiceApplication extends Application {
    int menu;

    public String getUbaid() {
        return ubaid;
    }

    public void setUbaid(String ubaid) {
        this.ubaid = ubaid;
    }

    String ubaid;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }
}
