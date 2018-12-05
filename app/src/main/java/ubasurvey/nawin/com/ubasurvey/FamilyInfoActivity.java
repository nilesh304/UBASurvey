package ubasurvey.nawin.com.ubasurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FamilyInfoActivity extends AppCompatActivity {
Button addButton,nextButton;
TextView famMembers;
ChoiceApplication globalVar;
    private int request_Code = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code) {
            if (resultCode == RESULT_OK) {
            }
             famMembers.setText(globalVar.getFamilyMemCount()+" Family Members Added");
            }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalVar=(ChoiceApplication)getApplicationContext();
        setContentView(R.layout.activity_family_info);
        famMembers=(TextView)findViewById(R.id.familymem_Textview) ;
        addButton=(Button)findViewById(R.id.addfamilymem_btn);
        nextButton=(Button)findViewById(R.id.next_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FamilyInfoActivity.this, FamilyDetailsActivity.class);
                startActivityForResult(i,request_Code);

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalVar.resetIncrement();



            }
        });
    }

}
