package id.noidea.firstblood.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import id.noidea.firstblood.R;

public class FindActivity extends AppCompatActivity {

    private String[] arraySpinnerBlood;
    private String[] arraySpinnerRhesus;
    Button find;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        find = (Button) findViewById(R.id.find);
        this.arraySpinnerBlood = new String[] {
                "A", "B", "O", "AB"
        };
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerBlood);
        s.setAdapter(adapter);

        this.arraySpinnerRhesus= new String[] {
                "+", "-"
        };
        Spinner sR = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> adapterR = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinnerRhesus);
        sR.setAdapter(adapterR);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),HomeActivity.class);
                intent.putExtra("viewpager_position", 0);
                startActivity(intent);
            }
        });
    }
}
