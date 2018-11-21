package ubasurvey.nawin.com.ubasurvey;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class BasicinfoActivity extends AppCompatActivity {
    ChoiceApplication globalVar;//=(ChoiceApplication)getApplicationContext()
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpInsertUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ucbformone.php";
    String HttpSelectUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubagetformone.php";
    String HttpUpdatetUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubaupdateformone.php";


    Button btn_submit;
    HashMap<String,String> villageHashMap;
    HashMap<String,String> DistrictHashMap;

    String ubaid,householdIDValue,villageSpinnerValue, districtSpinnerValue,blockSpinnerValue,wardNoSpinnerValue,gramPanchayatSpinnerValue, stateSpinnerValue,villageCode,districtCode;
    Spinner villageSpinnerHandler, districtSpinnerHandler,blockSpinnerHandler,wardNoSpinnerHandler,gramPanchayatSpinnerHandler, stateSpinnerHandler;
    EditText householdIDEditTextHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(BasicinfoActivity.this);
        villageHashMap = new HashMap<String, String>();
        villageHashMap.put("Anjur","anjur");
        villageHashMap.put("Pattaravakkam","patta");
        villageHashMap.put("Thenmelpakkam","thenm");
        villageHashMap.put("Orathur","orath");
        villageHashMap.put("Nattarasampattu","natta");
        DistrictHashMap = new HashMap<String, String>();
        DistrictHashMap.put("Kancheepuram","Ka");

        setContentView(R.layout.activity_basic_info);

        btn_submit = findViewById(R.id.basic_btn_submit);
        villageSpinnerHandler = findViewById(R.id.villageSpinner);
        districtSpinnerHandler = findViewById(R.id.districtspinner);
        blockSpinnerHandler = findViewById(R.id.BlockSpinner);
        gramPanchayatSpinnerHandler = findViewById(R.id.GramPanachayatSpinner);
        wardNoSpinnerHandler = findViewById(R.id.wardNoSpinner);
        stateSpinnerHandler = findViewById(R.id.StateSpinner);
        householdIDEditTextHandler = findViewById(R.id.householdID);
        globalVar=(ChoiceApplication)getApplicationContext();
        if(globalVar.getMenu()==1) {
            selectDatafromDB(globalVar.getUbaid());
            btn_submit.setText("Update");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                // Calling method to get value from EditText.
              if( GetValueFromForm())
                  if(globalVar.getMenu()==0)
                      insertToDB(HttpInsertUrl);
                  else
                      updateToDB(HttpUpdatetUrl);


              else
              {
                  YoYo.with(Techniques.BounceInUp)
                          .duration(700)
                          .playOn(findViewById(R.id.basic_btn_submit));
              }

            }
        });


    }
    // Creating method to get value from EditText.
    public boolean GetValueFromForm(){

        villageSpinnerValue = villageSpinnerHandler.getSelectedItem().toString();
        districtSpinnerValue = districtSpinnerHandler.getSelectedItem().toString();
        blockSpinnerValue = blockSpinnerHandler.getSelectedItem().toString();
        wardNoSpinnerValue = wardNoSpinnerHandler.getSelectedItem().toString();
        gramPanchayatSpinnerValue = gramPanchayatSpinnerHandler.getSelectedItem().toString();
        stateSpinnerValue = stateSpinnerHandler.getSelectedItem().toString();
        villageCode=villageHashMap.get(villageSpinnerValue);
        districtCode=DistrictHashMap.get(districtSpinnerValue);
        householdIDValue=String.valueOf(householdIDEditTextHandler.getText());
        ubaid=stateSpinnerValue+districtCode+villageCode+householdIDValue;

        if(villageSpinnerValue.compareTo("Select Value")==0||blockSpinnerValue.compareTo("Select Value")==0 ||wardNoSpinnerValue.compareTo("Select Value")==0||gramPanchayatSpinnerValue.compareTo("Select Value")==0||householdIDValue.compareTo("")==0)
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

                       globalVar.setUbaid(ubaid);
                       Toast toast = Toast.makeText(getApplicationContext(),
                               ServerResponse,
                               Toast.LENGTH_LONG);

                       toast.show();
                       finish();
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError volleyError) {

                       // Hiding the progress dialog after all task complete.
                       progressDialog.dismiss();

                       // Showing error message if something goes wrong.
                       Toast.makeText(BasicinfoActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                   }
               }) {
           @Override
           protected Map<String, String> getParams() {

               // Creating Map String Params.
               Map<String, String> params = new HashMap<String, String>();

               // Adding All values to Params.
               params.put("ubaid", ubaid);
               params.put("village", villageSpinnerValue);
               params.put("grampanchayat", gramPanchayatSpinnerValue);
               params.put("wardno", wardNoSpinnerValue);
               params.put("block", blockSpinnerValue);
               params.put("district", districtSpinnerValue);
               params.put("state", stateSpinnerValue);
               params.put("householdid",householdIDValue);

               return params;
           }

       };

       // Creating RequestQueue.
       RequestQueue requestQueue = Volley.newRequestQueue(BasicinfoActivity.this);

       // Adding the StringRequest object into requestQueue.
       requestQueue.add(stringRequest);

   }
    void  updateToDB(String HttpUrl)
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

                        globalVar.setUbaid(ubaid);
                        Toast toast = Toast.makeText(getApplicationContext(),
                                ServerResponse,
                                Toast.LENGTH_LONG);

                        toast.show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing error message if something goes wrong.
                        Toast.makeText(BasicinfoActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("oldubaid",globalVar.getUbaid());
                params.put("ubaid", ubaid);
                params.put("village", villageSpinnerValue);
                params.put("grampanchayat", gramPanchayatSpinnerValue);
                params.put("wardno", wardNoSpinnerValue);
                params.put("block", blockSpinnerValue);
                params.put("district", districtSpinnerValue);
                params.put("state", stateSpinnerValue);
                params.put("householdid",householdIDValue);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(BasicinfoActivity.this);

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
                        Toast.makeText(BasicinfoActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(BasicinfoActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    public void setValuetoForm(String jsonString){

        try {
            JSONObject jobj = new JSONObject(jsonString);
            householdIDValue=jobj.getString("householdid");
            villageSpinnerValue = jobj.getString("village");
            districtSpinnerValue = jobj.getString("district");
            blockSpinnerValue = jobj.getString("block");
            wardNoSpinnerValue = jobj.getString("wardno");
            gramPanchayatSpinnerValue = jobj.getString("grampanchayat");
            stateSpinnerValue = jobj.getString("state");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        villageSpinnerHandler.setSelection(setSpinnerPos(villageSpinnerHandler,villageSpinnerValue));
         districtSpinnerHandler.setSelection(setSpinnerPos(districtSpinnerHandler,districtSpinnerValue));
         blockSpinnerHandler.setSelection(setSpinnerPos(blockSpinnerHandler,blockSpinnerValue));
         wardNoSpinnerHandler.setSelection(setSpinnerPos(wardNoSpinnerHandler,blockSpinnerValue));
         gramPanchayatSpinnerHandler.setSelection(setSpinnerPos(gramPanchayatSpinnerHandler,gramPanchayatSpinnerValue));
        stateSpinnerHandler.setSelection(setSpinnerPos(stateSpinnerHandler, stateSpinnerValue));
        villageCode=villageHashMap.get(villageSpinnerValue);
        districtCode=DistrictHashMap.get(districtSpinnerValue);
        householdIDEditTextHandler.setText(householdIDValue);
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
