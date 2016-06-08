package in.rohit.DocAppoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.rohit.DocAppoint.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    public void onButtonClick(View view) {
        Intent intent=new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
    public void sssClick(View view) {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

}
