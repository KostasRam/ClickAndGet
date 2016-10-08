package com.superapp.mymobile.clickget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;


public class ProductSelectionActivity extends Activity {

    private FirebaseDatabase fbdatabase;
    private DatabaseReference fbref_product;
    private ListView listView;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String selected_cat;
    private int numOfChildren;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);

        selected_cat = getIntent().getExtras().getString("pass_selected_cat");
        listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        fbdatabase = FirebaseDatabase.getInstance();
        fbref_product = fbdatabase.getReference("PRODUCTS/SM_0/" + selected_cat);   /*  NA GINEI SM_0 DYNAMIKO  */

        /*
        int count = 0;
        fbref_product.once('value', function(snapshot) {
            count++;
        });

        numOfChildren = fbref_product.numChildrenCount();
        */

        for (int i = 0; i < numOfChildren; i++) {
            String str = (String) (i + "");
            DatabaseReference product_ref = fbref_product.child(str);

            product_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    String productName = (String) map.get("productName");

                    arrayList.add(productName);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    //textView.setText("The read failed: " + databaseError.getMessage());
                }
            });
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected_product = (String) listView.getItemAtPosition(position);
                Log.v("SELECTED_PRODUCT",selected_product);
            }
        });
    }//end onCreate()
}//end Activity
