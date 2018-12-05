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
import android.widget.TextView;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class HouseholdActivity extends AppCompatActivity {
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    ChoiceApplication globalVar;
    Button household_btn_submit_handler;
    TextView householdID_Handler;
    EditText  household_headNameValue_handler,annualIncome_Handler;
    Spinner typeofhouseSpinnerHandler,genderSpinnerHandler,categorySpinnerHandler,poverty_statusSpinnerHandler,ownhouseSpinnerHandler
            ,toilet_column1SpinnerHandler,drainage_column1SpinnerHandler,waste_DoorStepSpinnerHandler,compostSpinnerHandler,biogasSpinnerHandler;

    String ubaid,householdID,household_headNameValue,genderValue,categoryValue,povertyStatusValue,ownHouseValue,typeHouseValue,toiletValue;
    String drainageValue,wastageValue,compostValue,biogasValue,annualIncomeValue;

    // Storing server url into String variable.
    String HttpInsertUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubaupdateformtwo.php";
    String HttpSelectUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubagetformone.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_household);
        Bundle bundle = getIntent().getExtras();
        progressDialog = new ProgressDialog(HouseholdActivity.this);
        globalVar=(ChoiceApplication)getApplicationContext();
        ubaid=globalVar.getUbaid();

        householdID_Handler = (TextView)findViewById(R.id.householdID);

        household_headNameValue_handler = findViewById(R.id.household_HeadName);
        annualIncome_Handler = findViewById(R.id.annualIncome);
       
        categorySpinnerHandler = (Spinner)findViewById(R.id.spinner_category);
        poverty_statusSpinnerHandler = (Spinner)findViewById(R.id.spinner_poverty_status);
        ownhouseSpinnerHandler = (Spinner)findViewById(R.id.spinner_ownhouse);
        toilet_column1SpinnerHandler= (Spinner) findViewById(R.id.spinner_toilet_column1);
        drainage_column1SpinnerHandler=(Spinner)findViewById(R.id.spinner_drainage_column1);

        waste_DoorStepSpinnerHandler= (Spinner)findViewById(R.id.spinner_DoorStep_column1);
        compostSpinnerHandler=(Spinner)findViewById(R.id.spinner_compost);

        biogasSpinnerHandler = findViewById(R.id.spinner_biogas_column1);
       
        typeofhouseSpinnerHandler=findViewById(R.id.typeofhouseSpinner);
        genderSpinnerHandler=findViewById(R.id.spinner_Gender);

        household_btn_submit_handler = findViewById(R.id.household_btn_submit);
        if(globalVar.getMenu()>0) {
            //selectDatafromDB(globalVar.getUbaid());
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Basic info"+globalVar.getJsonString(),
                    Toast.LENGTH_LONG);

            toast.show();
            setValuetoForm(globalVar.getJsonString());
            household_btn_submit_handler.setText("Update");
        }
        else
            householdID_Handler.setText(bundle.getString("houseid"));

        household_btn_submit_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( getValueFrom()) {

                        insertToDB(HttpInsertUrl);
                }
                else
                {
                    YoYo.with(Techniques.BounceInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.household_btn_submit));
                }


            }
        });
    }

    private boolean getValueFrom() {


       // householdID = String.valueOf(householdID_Handler.getText());
        household_headNameValue = String.valueOf(household_headNameValue_handler.getText());

        genderValue = genderSpinnerHandler.getSelectedItem().toString();
        categoryValue=categorySpinnerHandler.getSelectedItem().toString();
        povertyStatusValue = poverty_statusSpinnerHandler.getSelectedItem().toString();
        ownHouseValue = ownhouseSpinnerHandler.getSelectedItem().toString();
        typeHouseValue = typeofhouseSpinnerHandler.getSelectedItem().toString();
        toiletValue = toilet_column1SpinnerHandler.getSelectedItem().toString();
        drainageValue =drainage_column1SpinnerHandler.getSelectedItem().toString();
        wastageValue = waste_DoorStepSpinnerHandler.getSelectedItem().toString();
        compostValue = compostSpinnerHandler.getSelectedItem().toString();;
        biogasValue = biogasSpinnerHandler.getSelectedItem().toString();
        annualIncomeValue = String.valueOf(annualIncome_Handler.getText());

        if(typeHouseValue.compareTo("Select Value")==0||genderValue.compareTo("Select Value")==0||categoryValue.compareTo("Select Value")==0||
                toiletValue.compareTo("Select Value")==0||wastageValue.compareTo("Select Value")==0||annualIncomeValue.compareTo("")==0
                ||povertyStatusValue.compareTo("Select Value")==0||ownHouseValue.compareTo("Select Value")==0
                ||compostValue.compareTo("Select Value")==0||biogasValue.compareTo("Select Value")==0)
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
                        if(ServerResponse.compareTo("0")==0) {


                        }
                        else
                        {

                            if(globalVar.getMenu()==0)
                            {
                               // Intent i = new Intent(RespondentProfileActivity.this, MigrationStatusActivity.class);

                                // Starts TargetActivity
                              //  startActivity(i);
                            }

                            else
                            {
                                globalVar.setJsonString(ServerResponse);

                            }
                        }

                        finish();
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
                params.put("nameofthehead",household_headNameValue);
                params.put("gender", genderValue);
                params.put("category", categoryValue);
                params.put("povertystatus", povertyStatusValue);
                params.put("ownhouse", ownHouseValue);
                params.put("typeofhouse", typeHouseValue);
                params.put("toilet", toiletValue);
                params.put("drainage",drainageValue);
                params.put("wastecollection", wastageValue);
                params.put("compostpit", compostValue);
                params.put("biogasplant", biogasValue);
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
                        //code to globar var
                        globalVar.setJsonString(ServerResponse);
                        finish();

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
            householdID=jobj.getString("householdid");
            if(householdID.compareTo("null")==0)
                householdID= "";
            household_headNameValue=jobj.getString("nameofthehead");
            if(household_headNameValue.compareTo("null")==0)
                household_headNameValue = "";
            genderValue = jobj.getString("gender");
            if(genderValue.compareTo("null")==0)
                genderValue= "Select Value";
            categoryValue = jobj.getString("category");
            if(categoryValue.compareTo("null")==0)
                    categoryValue = "Select Value";
            povertyStatusValue = jobj.getString("povertystatus");
                    if(povertyStatusValue.compareTo("null")==0)
                                povertyStatusValue = "Select Value";
            ownHouseValue = jobj.getString("ownhouse");
            if(ownHouseValue.compareTo("null")==0)
                  ownHouseValue= "Select Value";
            toiletValue =jobj.getString("toilet");

            if(toiletValue.compareTo("null")==0)
                toiletValue= "Select Value";
            typeHouseValue = jobj.getString("typeofhouse");
            if(typeHouseValue.compareTo("null")==0)
                   typeHouseValue = "Select Value";
            drainageValue = jobj.getString("drainage");
            if(drainageValue.compareTo("null")==0)
            drainageValue= "Select Value";
            wastageValue = jobj.getString("wastecollection");
            if(wastageValue.compareTo("null")==0)
            wastageValue= "Select Value";
            compostValue = jobj.getString("compostpit");
                    if(compostValue.compareTo("null")==0)
                        compostValue= "Select Value";
            biogasValue = jobj.getString("biogasplant");
            if(biogasValue.compareTo("null")==0)
                  biogasValue = "Select Value";

            annualIncomeValue = jobj.getString("annualincome");
            if(annualIncomeValue .compareTo("null")==0)
                annualIncomeValue="";

            

        } catch (JSONException e) {
            e.printStackTrace();
        }

        typeofhouseSpinnerHandler.setSelection(setSpinnerPos(typeofhouseSpinnerHandler,typeHouseValue));
        genderSpinnerHandler.setSelection(setSpinnerPos(genderSpinnerHandler,genderValue));
        categorySpinnerHandler.setSelection(setSpinnerPos(categorySpinnerHandler,categoryValue));
        poverty_statusSpinnerHandler.setSelection(setSpinnerPos(poverty_statusSpinnerHandler,povertyStatusValue));
        ownhouseSpinnerHandler.setSelection(setSpinnerPos(ownhouseSpinnerHandler,ownHouseValue));
        toilet_column1SpinnerHandler.setSelection(setSpinnerPos(toilet_column1SpinnerHandler,toiletValue));
        drainage_column1SpinnerHandler.setSelection(setSpinnerPos(drainage_column1SpinnerHandler,drainageValue));
        waste_DoorStepSpinnerHandler.setSelection(setSpinnerPos(waste_DoorStepSpinnerHandler,wastageValue));
        biogasSpinnerHandler.setSelection(setSpinnerPos(biogasSpinnerHandler,biogasValue));
        household_headNameValue_handler.setText(household_headNameValue);
        annualIncome_Handler.setText(annualIncomeValue);
        householdID_Handler.setText(householdID);

    }
    int  setSpinnerPos(Spinner spinner,String value)
    {


        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        return myAdap.getPosition(value);

        //set the default according to value
        // spinner.setSelection(spinnerPosition);
    }

}