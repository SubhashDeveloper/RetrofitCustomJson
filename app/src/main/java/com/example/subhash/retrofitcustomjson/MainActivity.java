package com.example.subhash.retrofitcustomjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<HashMap<String, String>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.listview);

        arrayList=new ArrayList<HashMap<String, String>>();


        OkHttpClient okHttpClient = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
//                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("http://api.androiddeft.com/")
                .build();
        ScalarService scalarService=retrofit.create(ScalarService.class);
        Call<String> stringCall=scalarService.getStringResponse("/retrofit/json_object.json");
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                Toast.makeText(MainActivity.this, "Resopnse"+response.body(), Toast.LENGTH_SHORT).show();
                String json=response.body();
                if (json!=null)
                {
                    try {
                        JSONObject jsonObj = new JSONObject(json);

                        // Getting JSON Array node

                        JSONArray contacts = jsonObj.getJSONArray("employee");
                        // looping through All employee
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String id=c.getString("employee_id");
                            String name=c.getString("name");
                            String contact=c.getString("contact_number");
                            String salary=c.getString("salary");

                            HashMap<String, String> hashMap = new HashMap<>();

                            // adding each child node to HashMap key => value
                            hashMap.put("Id", id);
                            hashMap.put("Name", name);
                            hashMap.put("Contact", contact);
                            hashMap.put("Salary", salary);

                            // adding hashmap to Array list
                            arrayList.add(hashMap);


                        }
                        Setdata();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "JSON ERROR "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error"+t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Setdata() {
        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, arrayList,
                R.layout.list_item, new String[]{"Id", "Name",
                "Contact", "Salary"}, new int[]{R.id.text1,
                R.id.text2, R.id.text3,R.id.text4});

        listView.setAdapter(adapter);
    }

}
