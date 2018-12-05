package ubasurvey.nawin.com.ubasurvey;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import  android.view.MotionEvent;
import android.view.View;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.databinding.DataBindingUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectRecordActivity extends AppCompatActivity implements RecordAdapter.RecordsAdapterListener{

    private SearchView searchView;
    private List<Record> RecordList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecordAdapter mAdapter;
    ChoiceApplication globalVar;

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


    // Creating Progress dialog.
    ProgressDialog progressDialog;


    String HttpSelectUrl = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubaselectrecords.php";
    String HttpSelectUrl1 = "http://navinsjavatutorial.000webhostapp.com/ucbsurvey/ubagetformone.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_record);
        globalVar=(ChoiceApplication)getApplicationContext();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // toolbar fancy stuff
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.toolbar_title);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Record record=mAdapter.getRecordListFiltered().get(position);
                Toast.makeText(getApplicationContext(), "Selected: " + record.getTitle() + ", " + record.getGenre(), Toast.LENGTH_LONG).show();
               globalVar.setUbaid(record.getTitle());
                selectDatafromDB(record.getTitle());
               // mAdapter.getFilter().filter("tnkaor");
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(SelectRecordActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
        // white background notification bar
        whiteNotificationBar(recyclerView);
        mAdapter = new RecordAdapter(this,RecordList,this);
        mAdapter.setOnItemClickListener(new RecordAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
               // Log.d(TAG, "onItemClick position: " + position);
                Record record=RecordList.get(position);
                Toast.makeText(getApplicationContext(), "Selected: " + record.getTitle() + ", " + record.getGenre(), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onItemLongClick(int position, View v) {
               // Log.d(TAG, "onItemLongClick pos = " + position);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());


        recyclerView.setLayoutManager(mLayoutManager); recyclerView.setItemAnimator(new DefaultItemAnimator()); recyclerView.setAdapter(mAdapter); recyclerView.setHasFixedSize(true);
        progressDialog = new ProgressDialog(SelectRecordActivity.this);
        prepareRecordData();
    }
    private void prepareRecordData() {


        progressDialog.setMessage("Loading Records...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                HttpSelectUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {

                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String ubaid = jsonobject.getString("ubaid");
                        String district = jsonobject.getString("district");
                        String state = jsonobject.getString("state");
                        Record Record = new Record(ubaid, district, state);
                        RecordList.add(Record);
                    }
                    mAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SelectRecordActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
             // Toast.makeText(SelectRecordActivity.this, "Error" + query, Toast.LENGTH_SHORT).show();
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
               // Toast.makeText(SelectRecordActivity.this,  query, Toast.LENGTH_SHORT).show();
                      mAdapter.getFilter().filter(query);

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }

    @Override
    public void onRecordSelected(Record record) {
        Toast.makeText(getApplicationContext(), "Selected: " + record.getTitle() + ", " + record.getGenre(), Toast.LENGTH_LONG).show();
    }

    void  selectDatafromDB(final String ubaidlocal)
    {
        // Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        progressDialog.show();
        // Creating string request with post method.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpSelectUrl1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        //code to globar var
                        //setValuetoForm(ServerResponse);
                        globalVar.setJsonString(ServerResponse);


                     /*   Toast toast = Toast.makeText(getApplicationContext(),
                                "Menu "+globalVar.getJsonString(),
                                Toast.LENGTH_LONG);

                        toast.show();*/
                        startActivity(new Intent(SelectRecordActivity.this, FormsMenuActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();
                        globalVar.setJsonString("");

                        // Showing error message if something goes wrong.
                        Toast.makeText(SelectRecordActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                        //finish();;
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
        RequestQueue requestQueue = Volley.newRequestQueue(SelectRecordActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
}
