package ubasurvey.nawin.com.ubasurvey;

public class FamilyDetails {

    public FamilyDetails(Integer ubaindid, String name) {
        this.ubaindid = ubaindid;
        this.name = name;
    }

    public Integer getUbaindid() {
        return ubaindid;
    }

    public void setUbaindid(Integer ubaindid) {
        this.ubaindid = ubaindid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    Integer ubaindid;
    String name;

}
