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

public class LiveStockActivity extends AppCompatActivity {
    // Creating Progress dialog.

    EditText total_Cows_EditHandler,total_Buffalo_EditHandler,total_Goat_Sheep_Handler,total_Calves_EditHandler,total_Bullock_EditHandler,total_Poultry_Ducks_Handler,others_EditHandler,milk_Production_EditHandler,waste_EditHandler;
    ProgressDialog progressDialog;
    ChoiceApplication globalVar;
    Button livestock_btn_submit_handler;
    Spinner typeofshelterSpinnerHandler;
    String ubaid, cowsValue,buffalosValue,goats_sheep_Value,calvesValue,bullocksValue,poultry_ducks_Value,othersValue,shelterValue,milkProductionValue,wasteValue;

    // Storing server url into String variable.
    String HttpInsertUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubaupdateformtwelve.php";
    String HttpSelectUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubagetformone.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_live_stock);

        progressDialog = new ProgressDialog(LiveStockActivity.this);
        globalVar=(ChoiceApplication)getApplicationContext();
        ubaid=globalVar.getUbaid();
        total_Cows_EditHandler=(EditText)findViewById(R.id.total_cow_count);
        total_Buffalo_EditHandler=(EditText)findViewById(R.id.total_buffalo_count);
        total_Calves_EditHandler=(EditText)findViewById(R.id.total_calves_count);
        total_Goat_Sheep_Handler=(EditText)findViewById(R.id.total_goats_sheep_count);
        total_Bullock_EditHandler=(EditText)findViewById(R.id.total_bullocks_count);
        total_Poultry_Ducks_Handler=(EditText)findViewById(R.id.total_poultry_ducks_count);
        others_EditHandler=(EditText)findViewById(R.id.total_other_stock_count);
        milk_Production_EditHandler=(EditText)findViewById(R.id.total_milk_prodcution);
        waste_EditHandler=(EditText)findViewById(R.id.total_waste_prodcution);

        typeofshelterSpinnerHandler=(Spinner)findViewById(R.id.typeofshelter);
        livestock_btn_submit_handler = findViewById(R.id.livestock_btn_submit);
        if(globalVar.getMenu()==1) {
            setValuetoForm(globalVar.getJsonString());
            livestock_btn_submit_handler.setText("Update");
        }

        livestock_btn_submit_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( getValueFromForm()) {

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

// EditText total_Cows_EditHandler,total_Buffalo_EditHandler,total_Goat_Sheep_Handler,total_Calves_EditHandler,total_Bullock_EditHandler,total_Poultry_Ducks_Handler;
//String ubaid, cowsValue,buffalosValue,goats_sheep_Value,calvesValue,bullocksValue,poultry_ducks_Value;


            cowsValue = String.valueOf(total_Cows_EditHandler.getText());
            buffalosValue = String.valueOf(total_Buffalo_EditHandler.getText());
            goats_sheep_Value = String.valueOf(total_Goat_Sheep_Handler.getText());
            calvesValue = String.valueOf(total_Calves_EditHandler.getText());
            bullocksValue = String.valueOf(total_Bullock_EditHandler.getText());
            poultry_ducks_Value = String.valueOf(total_Poultry_Ducks_Handler.getText());
            othersValue=String.valueOf(others_EditHandler.getText());
            milkProductionValue=String.valueOf(milk_Production_EditHandler.getText());
            wasteValue=String.valueOf(waste_EditHandler.getText());
            shelterValue = typeofshelterSpinnerHandler.getSelectedItem().toString();










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
                        Toast.makeText(LiveStockActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("ubaid", ubaid);
                params.put("livestockcows",cowsValue);
                params.put("livestockbuffalo", buffalosValue);
                params.put("livestockgoatsheep", goats_sheep_Value);
                params.put("livestockcalves", calvesValue);
                params.put("livestockbullocks", bullocksValue);
                params.put("livestockpoultryducks", poultry_ducks_Value);
                params.put("livestockothers",othersValue);
                params.put("shelterforlivestock", shelterValue);
                params.put("avgdailyproductionofmilk",milkProductionValue);
                params.put("animalwastecowdung",wasteValue);

                return params;
            }

        };
        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LiveStockActivity.this);
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
                        Toast.makeText(LiveStockActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(LiveStockActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    public void setValuetoForm(String jsonString){

        try {
            JSONObject jobj = new JSONObject(jsonString);
            cowsValue=jobj.getString("livestockcows");
            buffalosValue = jobj.getString("livestockbuffalo");
            goats_sheep_Value = jobj.getString("livestockgoatsheep");
            calvesValue = jobj.getString("livestockcalves");
            bullocksValue = jobj.getString("livestockbullocks");
            poultry_ducks_Value = jobj.getString("livestockpoultryducks");
            othersValue=jobj.getString("livestockothers");
            shelterValue=jobj.getString("shelterforlivestock");
            milkProductionValue=jobj.getString("avgdailyproductionofmilk");
            wasteValue=jobj.getString("animalwastecowdung");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(cowsValue.compareTo("NA")==0||cowsValue.compareTo("0")==0)
        {
            total_Cows_EditHandler.setText("0");
        }
        else
            total_Cows_EditHandler.setText(cowsValue);

        if(buffalosValue.compareTo("NA")==0||buffalosValue.compareTo("0")==0)
        {
            total_Buffalo_EditHandler.setText("0");
        }
        else
            total_Buffalo_EditHandler.setText(buffalosValue);

        if(goats_sheep_Value.compareTo("NA")==0||goats_sheep_Value.compareTo("0")==0)
        {
            total_Goat_Sheep_Handler.setText("0");
        }
        else
            total_Goat_Sheep_Handler.setText(goats_sheep_Value);
        if(calvesValue.compareTo("NA")==0||calvesValue.compareTo("0")==0)
        {
            total_Calves_EditHandler.setText("0");
        }
        else
            total_Calves_EditHandler.setText(calvesValue);

        if(bullocksValue.compareTo("NA")==0||bullocksValue.compareTo("0")==0)
        {
            total_Bullock_EditHandler.setText("");
        }
        else
            total_Bullock_EditHandler.setText(bullocksValue);
        if(poultry_ducks_Value.compareTo("NA")==0||poultry_ducks_Value.compareTo("0")==0)
        {
            total_Poultry_Ducks_Handler.setText("");
        }
        else
            total_Poultry_Ducks_Handler.setText(poultry_ducks_Value);

        if(othersValue.compareTo("NA")==0||othersValue.compareTo("0")==0)
        {
            others_EditHandler.setText("");
        }
        else
            others_EditHandler.setText(othersValue);

        if(milkProductionValue.compareTo("NA")==0||milkProductionValue.compareTo("0")==0)
        {
            milk_Production_EditHandler.setText("");
        }
        else
            milk_Production_EditHandler.setText(milkProductionValue);

        if(wasteValue.compareTo("NA")==0||wasteValue.compareTo("0")==0)
        {
            waste_EditHandler.setText("");
        }
        else
            waste_EditHandler.setText(wasteValue);
        if(shelterValue.compareTo("NA")==0||shelterValue.compareTo("")==0)
        {
            shelterValue="Select Value";
            typeofshelterSpinnerHandler.setSelection(setSpinnerPos(typeofshelterSpinnerHandler,shelterValue));
        }
        else
            typeofshelterSpinnerHandler.setSelection(setSpinnerPos(typeofshelterSpinnerHandler,shelterValue));
    }
    int  setSpinnerPos(Spinner spinner,String value)
    {
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
        return myAdap.getPosition(value);

    }


}

