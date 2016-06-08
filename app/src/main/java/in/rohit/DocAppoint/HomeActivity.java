package in.rohit.DocAppoint;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.rohit.DocAppoint.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import in.rohit.DocAppoint.Data.Advert;
import in.rohit.DocAppoint.Data.Dietition;

public class HomeActivity extends AppCompatActivity {

    private ImageView imageView;
    AutoCompleteTextView text;
    //MultiAutoCompleteTextView text1;
    String[] languages={"Dr.Prashant Nanda-Dentist ","Dr.Amit Damia-Gyne","Dr.Manav Kalra-Dentist","Dr.Aval Luthra-Physician","Dr.Ekta Mathur-Gyne","Dr.Ashish-Homeo","Dr.Anil-ENT","Dr.Pooja Khanna-Dentist","Dr.Manu Drishti-Dentist","Dr.Smriti Malhotra-Dentist"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        text=(AutoCompleteTextView)findViewById(R.id.editText1);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,languages);

        text.setAdapter(adapter);
        text.setThreshold(1);



        imageView = (ImageView) findViewById(R.id.Buttonservice);

        DownAdvert advert = new DownAdvert();
        advert.execute();
    }

    public void openDentistActivity(View view) {
        Intent intent = new Intent(this, DentistActivity.class);
        startActivity(intent);
    }

    public void openDietition(View view) {
        Intent intent = new Intent(this, Dietition.class);
        startActivity(intent);
   }

    public void openHomeopath(View view) {
        Intent intent = new Intent(this, Homeopath.class);
        startActivity(intent);
    }

    public void openGynecologist(View view) {
        Intent intent = new Intent(this, Gynecologist.class);
        startActivity(intent);
    }

    public  void  openEnt(View view){

        Intent intent=new Intent(this,Ent.class);
        startActivity(intent);
    }


    public  void  openPhysician(View view){

        Intent intent=new Intent(this,Physician.class);
        startActivity(intent);
    }


    class DownAdvert extends AsyncTask<String, Intent, ArrayList<Advert>> {

        ArrayList<Advert> arrayList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arrayList = new ArrayList<>();
        }

        @Override
        protected ArrayList<Advert> doInBackground(String... params) {
            try {
                String data = DownloadJsonContent.downloadContent(Fields.URL_ADVERTISEMENT);
                JSONObject object = new JSONObject(data);
                JSONArray array = object.getJSONArray("advertisement");
                for (int i = 0; i < array.length(); i++) {
                    Advert advert = new Advert();
                    JSONObject jsonObject = array.getJSONObject(i);
                    String url = jsonObject.getString("imageurl");
                    String redirectUrl = jsonObject.getString("redirecturl");
                    advert.setImageURL(url);
                    advert.setRedirectURL(redirectUrl);
                    arrayList.add(advert);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        @Override
        protected void onPostExecute(final ArrayList<Advert> adverts) {
            super.onPostExecute(adverts);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (adverts.size() > 0) {
                        int l = adverts.size();
                        int incr = 0;
                        while (true) {
                            if (incr == l - 1) {
                                incr = 0;
                            } else {
                                incr = incr + 1;
                            }
                            final int finalIncr = incr;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (!HomeActivity.this.isDestroyed()) {


                                        Glide
                                                .with(HomeActivity.this)
                                                .load(adverts.get(finalIncr).getImageURL())
                                                .override(1000,300)
                                                .into(imageView);
                                    }
                                }
                            });
                            Log.e(Fields.Ent, adverts.get(0).getImageURL());
                            synchronized (this) {
                                try {
                                    this.wait(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });
            thread.start();


        }
    }
}
