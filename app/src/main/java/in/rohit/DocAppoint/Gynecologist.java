package in.rohit.DocAppoint;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rohit.DocAppoint.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Gynecologist extends AppCompatActivity {

    private LocationManager manager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gynecologist);

        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	   Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	   if (location != null) {
		  Fields.LATITUDE = location.getLatitude();
		  Fields.LONGITUDE = location.getLongitude();
	   }
	   listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Fields.LATITUDE=latitude;
                Fields.LONGITUDE=longitude;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        String locationProvider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (manager.isProviderEnabled(locationProvider)) {
            manager.requestLocationUpdates(locationProvider, 0, 0, listener);
        }else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this)
                    .setMessage("GPS not enable!")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });
            builder.create().show();
        }

        //Download data and parse

        Downloadandparse downloadandparse=new Downloadandparse();
        downloadandparse.execute();


    }



    @Override
    protected void onStop() {
        super.onStop();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.removeUpdates(listener);
    }

    class Downloadandparse extends AsyncTask<String, Integer, ArrayList<Dentist>> {
        private ArrayList<Dentist> arra;
        private ArrayList<Dentist> arral;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arra=new ArrayList<>();
            arral=new ArrayList<>();
        }

        @Override
        protected ArrayList<Dentist> doInBackground(String... params) {
            try {
                String data=DownloadJsonContent.downloadContentUsingPostMethod(Fields.URL_Gynecologist,"latitude="+Fields.LATITUDE+"&longitude="+Fields.LONGITUDE);
                JSONObject object=new JSONObject(data);
                JSONArray array=object.getJSONArray(Fields.Gynecologist);
                for (int i=0;i<array.length();i++){
                    JSONObject obj=array.getJSONObject(i);
                    Dentist dentist=new Dentist();
                    dentist.setM_id(obj.getString("m_id"));
                    dentist.setName(obj.getString("name"));
                    dentist.setAddress(obj.getString("address"));
                    dentist.setDescription(obj.getString("description"));
                    dentist.setEmail_id(obj.getString("email_id"));
                    dentist.setNumber(obj.getString("number"));
                    dentist.setType(obj.getString("type"));
                    dentist.setLatitude(obj.getString("latitude"));
                    dentist.setLongitude(obj.getString("longitude"));
                    dentist.setDistance(obj.getString("distance"));
                    arra.add(dentist);
                }



            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return arra;
        }

        @Override
        protected void onPostExecute(final ArrayList<Dentist> dentists) {
            super.onPostExecute(dentists);
            ListView view= (ListView) findViewById(R.id.gynecoList);
            MyAdapter adapter=new MyAdapter(dentists);
            view.setAdapter(adapter);

            view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Gynecologist.this, DentistDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Fields.Dentist, dentists.get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });




        }

    }

    class MyAdapter extends BaseAdapter{


        private ArrayList<Dentist> arrayList;

        public MyAdapter(ArrayList<Dentist> arr){
            this.arrayList=arr;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getApplicationContext());
                v = vi.inflate(R.layout.view_list, null);
            }

            TextView textName= (TextView) v.findViewById(R.id.name);
            TextView textDistance= (TextView) v.findViewById(R.id.distance);
            textName.setText(arrayList.get(position).getName());
            textDistance.setText(arrayList.get(position).getDistance()+" M");
            return v;
        }
    }
}
