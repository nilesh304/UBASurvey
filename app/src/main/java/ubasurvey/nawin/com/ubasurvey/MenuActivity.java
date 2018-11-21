package ubasurvey.nawin.com.ubasurvey;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {
    ImageView insert,update;
    ChoiceApplication globalObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        globalObject=(ChoiceApplication)getApplicationContext();
        insert=(ImageView)findViewById(R.id.insertimg);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalObject.setMenu(0);
                startActivity( new Intent(MenuActivity.this, BasicinfoActivity.class));

            }
        });

        update=(ImageView)findViewById(R.id.updateimg);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalObject.setMenu(1);
                if(!(globalObject.getUbaid()==null))
                   startActivity( new Intent(MenuActivity.this, BasicinfoActivity.class));

            }
        });

    }
}