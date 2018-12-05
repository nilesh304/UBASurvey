package ubasurvey.nawin.com.ubasurvey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Switch;
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

public class FamilyDetailsActivity extends AppCompatActivity {
    // Creating Progress dialog.
    ProgressDialog progressDialog;
    ChoiceApplication globalVar;
    Button familydetails_btn_submit_handler;
    TextView headNameText;
    EditText  nameEdit_handler,ageEditHandler,majorHealthEdit_Handler;
    Spinner genderSpinnerHandler, maritialSpinnerHandler,educationSpinnerHandler,schoolSpinnerHandler,ssPensinSpinner,occupationSpinnerHandler;
    Switch aadharSwitchHandler,compSwitchHandler,bankACSwitchHandler,mnrgeaSwitchhandler,shGroupSwitchhandler;
    String ubaid,nameValue,ageValue,genderValue,maritialValue,educationValue,schoolValue,aadharValue,bankACValue,compLiteratureValue,
    ssPensionValue,majorHealthValue,mnregaValue,SHGroupvalue,occupationValue;
    // Storing server url into String variable.
    String HttpInsertUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubainsertfamilydetails.php";
    String HttpSelectUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubagetformone.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_family_details);
        Bundle bundle = getIntent().getExtras();
        progressDialog = new ProgressDialog(FamilyDetailsActivity.this);
        globalVar=(ChoiceApplication)getApplicationContext();
        ubaid=globalVar.getUbaid();


        nameEdit_handler = findViewById(R.id.familymembers_name_text);
        ageEditHandler = (EditText)findViewById(R.id.family_age_text);
        majorHealthEdit_Handler=(EditText)findViewById(R.id.healthproblems_text);

        genderSpinnerHandler =(Spinner)findViewById(R.id.familygender);
        maritialSpinnerHandler = (Spinner)findViewById(R.id.maritalstatusspinner);
        educationSpinnerHandler = (Spinner)findViewById(R.id.educationlevelspinner);
        schoolSpinnerHandler= (Spinner) findViewById(R.id.schoolspinner);
        occupationSpinnerHandler=(Spinner)findViewById(R.id.occupationcode);
        ssPensinSpinner=(Spinner)findViewById(R.id.socialsecuritypensionspinner);


        aadharSwitchHandler= (Switch)findViewById(R.id.aadhaarcard_switch);
        compSwitchHandler=(Switch)findViewById(R.id.computerliterate_switch);
        shGroupSwitchhandler =(Switch) findViewById(R.id.selfhelpgroups_switch);

        bankACSwitchHandler=(Switch)findViewById(R.id.bankaccount_switch);
        mnrgeaSwitchhandler=(Switch)findViewById(R.id.mnregajobcard_switch);

        familydetails_btn_submit_handler = findViewById(R.id.familydetails_btn_submit);
        if(globalVar.getMenu()==1) {
            //selectDatafromDB(globalVar.getUbaid());
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Basic info"+globalVar.getJsonString(),
                    Toast.LENGTH_LONG);

            toast.show();
            setValuetoForm(globalVar.getJsonString());
            familydetails_btn_submit_handler.setText("Update");
        }
//        else
//            householdID_Handler.setText(bundle.getString("houseid"));

        familydetails_btn_submit_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( getValueFrom()) {

                    insertToDB(HttpInsertUrl);
                }
                else
                {
                    YoYo.with(Techniques.BounceInUp)
                            .duration(700)
                            .playOn(findViewById(R.id.familydetails_btn_submit));
                }


            }
        });
    }

    private boolean getValueFrom() {

        nameValue= String.valueOf(nameEdit_handler.getText());
        ageValue = String.valueOf(ageEditHandler.getText());
        genderValue = genderSpinnerHandler.getSelectedItem().toString();
        maritialValue=maritialSpinnerHandler.getSelectedItem().toString();
        educationValue = educationSpinnerHandler.getSelectedItem().toString();
        schoolValue = schoolSpinnerHandler.getSelectedItem().toString();
        if(bankACSwitchHandler.isChecked())
              bankACValue = bankACSwitchHandler.getTextOn().toString();
        else
            bankACValue = bankACSwitchHandler.getTextOff().toString();
        if(compSwitchHandler.isChecked())
             compLiteratureValue = compSwitchHandler.getTextOn().toString();
        else
            compLiteratureValue = compSwitchHandler.getTextOff().toString();
        if(aadharSwitchHandler.isChecked())
            aadharValue=aadharSwitchHandler.getTextOn().toString();
        else
            aadharValue=aadharSwitchHandler.getTextOff().toString();
        ssPensionValue = ssPensinSpinner.getSelectedItem().toString();
        if(shGroupSwitchhandler.isChecked())
            SHGroupvalue = shGroupSwitchhandler.getTextOn().toString();
        else
            SHGroupvalue = shGroupSwitchhandler.getTextOff().toString();
        if(mnrgeaSwitchhandler.isChecked())
            mnregaValue=mnrgeaSwitchhandler.getTextOn().toString();
        else
            mnregaValue=mnrgeaSwitchhandler.getTextOff().toString();

        majorHealthValue=String.valueOf(majorHealthEdit_Handler.getText());
        occupationValue =occupationSpinnerHandler.getSelectedItem().toString();


       /* if(bankACValue.compareTo("Select Value")==0||genderValue.compareTo("Select Value")==0||maritialValue.compareTo("Select Value")==0||
                schoolValue.compareTo("Select Value")==0||aadharValue.compareTo("Select Value")==0||ageValue.compareTo("")==0
                ||povertyStatusValue.compareTo("Select Value")==0||educationValue.compareTo("Select Value")==0
                ||compLiteratureValue.compareTo("Select Value")==0||biogasValue.compareTo("Select Value")==0)
            return false;
        else*/
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
                                globalVar.setIncrement();
                                Intent data = new Intent();
                                data.setData(Uri.parse(globalVar.getFamilyMemCount().toString()));
                                setResult(RESULT_OK, data);
                                // Intent i = new Intent(RespondentProfileActivity.this, MigrationStatusActivity.class);

                                // Starts TargetActivity
                                //  startActivity(i);
                            }

                            else
                            {
                                globalVar.setIncrement();
                                Intent data = new Intent();
                                data.setData(Uri.parse(globalVar.getFamilyMemCount().toString()));
                                setResult(RESULT_OK, data);

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
                        Toast.makeText(FamilyDetailsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.					goingto								
                params.put("ubaid", ubaid);
                params.put("ubaindid",globalVar.getFamilyMemCount().toString());
                params.put("name", nameValue);
                params.put("age", ageValue);
                params.put("gender",genderValue );
                params.put("marritalstatus", maritialValue);
                params.put("education", educationValue);
                params.put("school", schoolValue);
                params.put("aadharcard",aadharValue);
                params.put("bankac", bankACValue);
                params.put("compliterate", compLiteratureValue);
                params.put("pension", ssPensionValue);
                params.put("healthprob", majorHealthValue);
                params.put("mnrega", mnregaValue);
                params.put("selfhelpgroup", SHGroupvalue);
                params.put("occupation", occupationValue);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(FamilyDetailsActivity.this);

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
                        Toast.makeText(FamilyDetailsActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(FamilyDetailsActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

    public void setValuetoForm(String jsonString){

       /* try {
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
            maritialValue = jobj.getString("category");
            if(maritialValue.compareTo("null")==0)
                maritialValue = "Select Value";
            povertyStatusValue = jobj.getString("povertystatus");
            if(povertyStatusValue.compareTo("null")==0)
                povertyStatusValue = "Select Value";
            educationValue = jobj.getString("ownhouse");
            if(educationValue.compareTo("null")==0)
                educationValue= "Select Value";
            schoolValue =jobj.getString("toilet");

            if(schoolValue.compareTo("null")==0)
                schoolValue= "Select Value";
            bankACValue = jobj.getString("typeofhouse");
            if(bankACValue.compareTo("null")==0)
                bankACValue = "Select Value";
            occupationValue = jobj.getString("drainage");
            if(occupationValue.compareTo("null")==0)
                occupationValue= "Select Value";
            aadharValue = jobj.getString("wastecollection");
            if(aadharValue.compareTo("null")==0)
                aadharValue= "Select Value";
            compLiteratureValue = jobj.getString("compostpit");
            if(compLiteratureValue.compareTo("null")==0)
                compLiteratureValue= "Select Value";
            biogasValue = jobj.getString("biogasplant");
            if(biogasValue.compareTo("null")==0)
                biogasValue = "Select Value";

            ageValue = jobj.getString("annualincome");
            if(ageValue .compareTo("null")==0)
                ageValue="";



        } catch (JSONException e) {
            e.printStackTrace();
        }

        bankACSwitchHandler.setSelection(setSpinnerPos(bankACSwitchHandler,bankACValue));
        mnrgeaSwitchhandler.setSelection(setSpinnerPos(mnrgeaSwitchhandler,genderValue));
        maritialSpinnerHandler.setSelection(setSpinnerPos(maritialSpinnerHandler,maritialValue));
        educationSpinnerHandler.setSelection(setSpinnerPos(educationSpinnerHandler,povertyStatusValue));
        educationSpinnerHandler.setSelection(setSpinnerPos(educationSpinnerHandler,educationValue));
        schoolSpinnerHandler.setSelection(setSpinnerPos(schoolSpinnerHandler,schoolValue));
        occupationSpinnerHandler.setSelection(setSpinnerPos(occupationSpinnerHandler,occupationValue));
        aadharSwitchHandler.setSelection(setSpinnerPos(aadharSwitchHandler,aadharValue));
        ssGroupSwitchhandler.setSelection(setSpinnerPos(ssGroupSwitchhandler,biogasValue));
        nameEdit_handler.setText(household_headNameValue);
        ageEditHandler.setText(ageValue);*/

    }
    int  setSpinnerPos(Spinner spinner,String value)
    {


        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        return myAdap.getPosition(value);

        //set the default according to value
        // spinner.setSelection(spinnerPosition);
    }


}