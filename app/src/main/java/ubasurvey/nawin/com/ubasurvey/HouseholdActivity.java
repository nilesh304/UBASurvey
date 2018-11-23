package ubasurvey.nawin.com.ubasurvey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HouseholdActivity extends AppCompatActivity {
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    ChoiceApplication globalVar;
    Button household_btn_submit_handler;
    EditText householdID_Handler, household_HeadName_handler,annualIncome_Handler;
    Spinner typeofhouseSpinnerHandler;
    RadioGroup radioGender_Handler,radioCategory_Handler,radio_poverty_status_Handler,radio_ownhouse_Handler;
    RadioGroup radio_toilet_column1_Handler,radio_drainage_column1_Handler;
    RadioGroup waste_DoorStep_column1_Handler,radio_compost_Handler, radio_biogas_column1_Handler;

    RadioButton radioGenderButton,radioCategoryButton,radioPovertyStatusButton,radioOwnHouseButton;
    RadioButton radioToiletButton,radioDrainageButton,radioWastageButton,radioCompostButton,radioBiogasButton;
    String ubaid,householdID,household_HeadName,GenderValue,radioCategoryValue,radioPovertyStatusValue,radioOwnHouseValue,spinnerTypeHouseValue,radioToiletValue;
    String radioDrainageValue,radioWastageValue,radioCompostValue,radioBiogasValue,annualIncomeValue;

    // Storing server url into String variable.
    String HttpInsertUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubaupdateformtwo.php";
    String HttpSelectUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubagetformone.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_household);

        progressDialog = new ProgressDialog(HouseholdActivity.this);
        globalVar=(ChoiceApplication)getApplicationContext();
        ubaid=globalVar.getUbaid();

        household_btn_submit_handler = findViewById(R.id.household_btn_submit);
        if(globalVar.getMenu()==1) {
            setValuetoForm(globalVar.getJsonString());
            household_btn_submit_handler.setText("Update");
        }

        household_btn_submit_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( getValueFromForm()) {
                    if (globalVar.getMenu() == 1) {
                        setValuetoForm(globalVar.getJsonString());
                        insertToDB(HttpInsertUrl);
                    } else
                        insertToDB(HttpInsertUrl);
                    ;

                }
                else
                {
                    YoYo.with(Techniques.BounceInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.household_btn_submit));
                }


                //Intent i = new Intent(HouseholdActivity.this, FamilyMembersInfo.class);
                // Starts TargetActivity
               // startActivity(i);
            }
        });



    }

    private boolean getValueFromForm() {
        householdID_Handler = findViewById(R.id.householdID);
        household_HeadName_handler = findViewById(R.id.household_HeadName);
        annualIncome_Handler = findViewById(R.id.annualIncome);
        radioGender_Handler = findViewById(R.id.radioGender);
        radioCategory_Handler = findViewById(R.id.radioCategory);
        radio_poverty_status_Handler = findViewById(R.id.radio_poverty_status);
        radio_ownhouse_Handler = findViewById(R.id.radio_ownhouse);
       // radio_typeHouse_column1_Handler = findViewById(R.id.radio_typeHouse);
        radio_toilet_column1_Handler = findViewById(R.id.radio_toilet_column1);
        radio_drainage_column1_Handler = findViewById(R.id.radio_drainage_column1);
        waste_DoorStep_column1_Handler = findViewById(R.id.waste_DoorStep_column1);
        radio_compost_Handler = findViewById(R.id.radio_compost);
        radio_biogas_column1_Handler = findViewById(R.id.radio_biogas_column1);
        typeofhouseSpinnerHandler=findViewById(R.id.typeofhouseSpinner);



        householdID = String.valueOf(householdID_Handler.getText());
        household_HeadName = String.valueOf(household_HeadName_handler.getText());
        // Radio Gender Option Value Fetching
        int selectedGenderId = radioGender_Handler.getCheckedRadioButtonId();
        radioGenderButton = (RadioButton) findViewById(selectedGenderId);
        GenderValue = (String) radioGenderButton.getText();

        // Radio Category Option Value Fetching
        int selectedCategoryId = radioCategory_Handler.getCheckedRadioButtonId();
        radioCategoryButton =(RadioButton) findViewById(selectedCategoryId);
        radioCategoryValue = (String) radioCategoryButton.getText();

        // Radio Poverty Status Option Value Fetching
        int selectedPovertyId = radio_poverty_status_Handler.getCheckedRadioButtonId();
        radioPovertyStatusButton =(RadioButton) findViewById(selectedPovertyId);
        radioPovertyStatusValue = (String) radioPovertyStatusButton.getText();

        // Radio own house Option Value Fetching
        int selectedOwnHouseId = radio_ownhouse_Handler.getCheckedRadioButtonId();
        radioOwnHouseButton =(RadioButton) findViewById(selectedOwnHouseId);
        radioOwnHouseValue = (String) radioOwnHouseButton.getText();

        // Radio Type House column1 Option Value Fetching

        spinnerTypeHouseValue = typeofhouseSpinnerHandler.getSelectedItem().toString();

        // Radio toilet Option Value Fetching
        int selectedToilet1Id =radio_toilet_column1_Handler.getCheckedRadioButtonId();
        radioToiletButton =(RadioButton) findViewById(selectedToilet1Id);
        radioToiletValue = (String) radioToiletButton.getText();


        // Radio Drainage Option Value Fetching
        int selectedDrainage1Id =radio_drainage_column1_Handler.getCheckedRadioButtonId();
        radioDrainageButton =(RadioButton) findViewById(selectedDrainage1Id);
        radioDrainageValue = (String) radioDrainageButton.getText();

        // Radio waste DoorStep Option Value Fetching
        int selectedWastageId =waste_DoorStep_column1_Handler.getCheckedRadioButtonId();
        radioWastageButton =(RadioButton) findViewById(selectedWastageId);
        radioWastageValue = (String) radioWastageButton.getText();

        // Radio compost Option Value Fetching
        int selectedCompostId =radio_compost_Handler.getCheckedRadioButtonId();
        radioCompostButton =(RadioButton) findViewById(selectedCompostId);
        radioCompostValue = (String) radioCompostButton.getText();

        // Radio biogas  Option Value Fetching
        int selectedBiogasId =radio_biogas_column1_Handler.getCheckedRadioButtonId();
        radioBiogasButton =(RadioButton) findViewById(selectedBiogasId);
        radioBiogasValue = (String) radioBiogasButton.getText();

        annualIncomeValue = String.valueOf(annualIncome_Handler.getText());

        if(spinnerTypeHouseValue.compareTo("Select Value")==0||annualIncomeValue.compareTo("")==0)
            return false;
        else
            return true;

    }

    void  insertToDB(String HttpUrl)
    {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                         Toast toast = Toast.makeText(getApplicationContext(),
                                ServerResponse,
                                Toast.LENGTH_LONG);

                        toast.show();

                       // Intent i = new Intent(BasicinfoActivity.this, HouseholdActivity.class);
                        // Starts TargetActivity
                       // startActivity(i);
                      //  finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(HouseholdActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("ubaid", ubaid);
                params.put("nameofthehead",household_HeadName);
                params.put("gender", GenderValue);
                params.put("category", radioCategoryValue);
                params.put("povertystatus", radioPovertyStatusValue);
                params.put("ownhouse", radioOwnHouseValue);
                params.put("typeofhouse", spinnerTypeHouseValue);
                params.put("toilet", radioToiletValue);
                params.put("drainage",radioDrainageValue);
                params.put("wastecollection", radioWastageValue);
                params.put("compostpit", radioCompostValue);
                params.put("biogasplant", radioBiogasValue);
                params.put("annualincome", annualIncomeValue);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(HouseholdActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    void  selectDatafromDB(final String ubaidlocal)
    {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpSelectUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        setValuetoForm(ServerResponse);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                ServerResponse,
                                Toast.LENGTH_LONG);

                        toast.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(HouseholdActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        finish();;
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("ubaid", ubaidlocal);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(HouseholdActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    public void setValuetoForm(String jsonString){

        try {
            JSONObject jobj = new JSONObject(jsonString);
            household_HeadName=jobj.getString("nameofthehead");
            GenderValue = jobj.getString("gender");
            radioCategoryValue = jobj.getString("category");
            radioPovertyStatusValue = jobj.getString("povertystatus");
            radioOwnHouseValue = jobj.getString("ownhouse");
            spinnerTypeHouseValue = jobj.getString("typeofhouse");
            radioDrainageValue = jobj.getString("drainage");
            radioWastageValue = jobj.getString("wastecollection");
            radioCompostValue = jobj.getString("compostpit");
            radioBiogasValue = jobj.getString("biogasplant");
            annualIncomeValue = jobj.getString("annualincome");

            

        } catch (JSONException e) {
            e.printStackTrace();
        }

        typeofhouseSpinnerHandler.setSelection(setSpinnerPos(typeofhouseSpinnerHandler,spinnerTypeHouseValue));
       // radio_toilet_column1_Handler.ch
        //ubaid=stateSpinnerValue+districtCode+villageCode+householdIDValue;



    }
    int  setSpinnerPos(Spinner spinner,String value)
    {


        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        return myAdap.getPosition(value);

        //set the default according to value
        // spinner.setSelection(spinnerPosition);
    }

}