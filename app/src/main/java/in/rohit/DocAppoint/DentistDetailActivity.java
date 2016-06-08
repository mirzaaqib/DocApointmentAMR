package in.rohit.DocAppoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rohit.DocAppoint.R;

public class DentistDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Dentist  dentist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist_detail);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        dentist= (Dentist) bundle.getSerializable(Fields.Dentist);

        ImageView profile;
        TextView tName= (TextView) findViewById(R.id.mName);
        TextView tDescription= (TextView) findViewById(R.id.mdescription);
        TextView tDistance= (TextView) findViewById(R.id.distance);
        TextView tPhone= (TextView) findViewById(R.id.phoneNumber);
        TextView tfee=(TextView)findViewById(R.id.fees);

        tName.setText(dentist.getName());
        tDescription.setText(dentist.getDescription());
        tDistance.setText(dentist.getDistance());
        tPhone.setText(dentist.getNumber());
        tfee.setText(dentist.getEmail_id());

        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        LatLng latLng = new LatLng(Double.valueOf(dentist.getLatitude()), Double.valueOf(dentist.getLongitude()));
//        LatLng latLng = new LatLng(22.75, 75.88);
        mMap.addMarker(new MarkerOptions().position(latLng).title(dentist.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
    public void onBook(View view){
        Intent intent=new Intent(this,Appointment.class);
        startActivity(intent);
    }
}
