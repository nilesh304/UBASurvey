package ubasurvey.nawin.com.ubasurvey;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BasicinfoActivity extends AppCompatActivity {
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    // Storing server url into String variable.
    String HttpUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ucbformone.php";


    Button btn_submit;
    HashMap<String,String> villageHashMap;
    HashMap<String,String> DistrictHashMap;

    String ubaid,villageSpinnerValue, districtSpinnerValue,blockSpinnerValue,wardNoSpinnerValue,gramPanchayatSpinnerValue, stateSpinnerValue,villageCode,districtCode;
    Spinner villageSpinnerHandler, districtSpinnerHandler,blockSpinnerHandler,wardNoSpinnerHandler,gramPanchayatSpinnerHandler, stateSpinnerHandler;
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

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Showing progress dialog at user registration time.
                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
               progressDialog.show();

                // Calling method to get value from EditText.
               GetValueFromForm();
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

                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(BasicinfoActivity.this);

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);


                // Explicit Intent by specifying its class name

            }
        });


    }
    // Creating method to get value from EditText.
    public void GetValueFromForm(){

        villageSpinnerValue = villageSpinnerHandler.getSelectedItem().toString();
        districtSpinnerValue = districtSpinnerHandler.getSelectedItem().toString();
        blockSpinnerValue = blockSpinnerHandler.getSelectedItem().toString();
        wardNoSpinnerValue = wardNoSpinnerHandler.getSelectedItem().toString();
        gramPanchayatSpinnerValue = gramPanchayatSpinnerHandler.getSelectedItem().toString();
        stateSpinnerValue = stateSpinnerHandler.getSelectedItem().toString();
        villageCode=villageHashMap.get(villageSpinnerValue);
        districtCode=DistrictHashMap.get(districtSpinnerValue);
        ubaid=stateSpinnerValue+districtCode+villageCode;


    }

}
