package ubasurvey.nawin.com.ubasurvey;


import android.app.Application;
import android.content.Intent;

public class ChoiceApplication extends Application {
    int menu;
    String ubaid;
    String jsonString;

    public String getFamilyjsonString() {
        return familyjsonString;
    }

    public void setFamilyjsonString(String familyjsonString) {
        this.familyjsonString = familyjsonString;
    }

    String familyjsonString;


    Integer familyMemCount;



    public String getUbaid() {
        return ubaid;
    }

    public Integer getFamilyMemCount() {
        return familyMemCount;
    }

    public void setFamilyMemCount(Integer familyMemCount) {
        this.familyMemCount = familyMemCount;
    }

    public void setIncrement() {
        this.familyMemCount++;
    }
    public void resetIncrement() {
        this.familyMemCount=1;
    }

    public void setUbaid(String ubaid) {
        this.ubaid = ubaid;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        familyMemCount=1;

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
