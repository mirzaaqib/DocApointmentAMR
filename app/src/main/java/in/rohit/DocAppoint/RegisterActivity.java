package in.rohit.DocAppoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rohit.DocAppoint.R;

public class RegisterActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }
    public void jjClick(View view) {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }


}
