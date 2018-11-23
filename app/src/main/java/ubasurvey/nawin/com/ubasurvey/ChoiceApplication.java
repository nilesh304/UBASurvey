package ubasurvey.nawin.com.ubasurvey;


import android.app.Application;

public class ChoiceApplication extends Application {
    int menu;
    String jsonString;
    UBASurvey ubasurvey;


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
        ubasurvey =new UBASurvey();

    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }


}
