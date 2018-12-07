package ubasurvey.nawin.com.ubasurvey;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FormsMenuActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {


    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;
    TextView ubaid;
    ChoiceApplication globalVar;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(FormsMenuActivity.this);
        globalVar=(ChoiceApplication)getApplicationContext();
        setContentView(R.layout.activity_forms_menu);
        getWindow().setFormat(PixelFormat.RGB_565);
        ubaid=(TextView)findViewById(R.id.ubaidLabel);
        ubaid.setText(globalVar.getUbaid());
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        arrayList.add(new DataModel("Basic Info", R.drawable.ic_assignment_black_24dp, "#660066"));
        arrayList.add(new DataModel("Househlod Information", R.drawable.ic_assignment_black_24dp, "#4d1f00"));
        arrayList.add(new DataModel("Respondant Profile", R.drawable.ic_assignment_black_24dp, "#000080"));
        arrayList.add(new DataModel("Family Member Information", R.drawable.ic_assignment_black_24dp, "#4BAA50"));
        arrayList.add(new DataModel("Migration Status in Family", R.drawable.ic_assignment_black_24dp, "#145214"));
        arrayList.add(new DataModel("Goverment Scheme", R.drawable.ic_assignment_black_24dp, "#0A9B88"));
        arrayList.add(new DataModel("Source of Water", R.drawable.ic_assignment_black_24dp, "#800000"));
        arrayList.add(new DataModel("Agricultural Inputs", R.drawable.ic_assignment_black_24dp, "#cc0066"));
        arrayList.add(new DataModel("Livestock Numbers", R.drawable.ic_assignment_black_24dp, "#003d4d"));
        arrayList.add(new DataModel("Major Problems", R.drawable.ic_assignment_black_24dp, "#cc0000"));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);


        /**
         AutoFitGridLayoutManager that auto fits the cells by the column width defined.
         **/

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);
        recyclerView.setLayoutManager(layoutManager);


        /**
         Simple GridLayoutManager that spans two columns
         **/
        /*GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);*/
    }

    @Override
    public void onItemClick(DataModel item) {

        Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
        switch (item.text)
        {
            case "Basic Info" :
                startActivity(new Intent(FormsMenuActivity.this, BasicinfoActivity.class));
                break;
            case "Househlod Information" :
                startActivity(new Intent(FormsMenuActivity.this, HouseholdActivity.class));
                break;
            case "Respondant Profile":
                startActivity(new Intent(FormsMenuActivity.this, RespondentProfileActivity.class));
                break;
            case "Family Member Information":
                startActivity(new Intent(FormsMenuActivity.this, FamilyInfoActivity.class));
                break;
            case "Migration Status in Family":
                startActivity(new Intent(FormsMenuActivity.this, MigrationStatusActivity.class));
                break;
            case "Source of Water":
                startActivity(new Intent(FormsMenuActivity.this, WaterSourceActivity.class));
                break;
            case "Agricultural Inputs":
                startActivity(new Intent(FormsMenuActivity.this, AgriculturalInputsActivity.class));
                break;
            case "Livestock Numbers":
                startActivity(new Intent(FormsMenuActivity.this, LiveStockActivity.class));
                break;
            case "Major Problems":
                startActivity(new Intent(FormsMenuActivity.this, ProblemsVillageActivity.class));
                break;


        }


    }

}
