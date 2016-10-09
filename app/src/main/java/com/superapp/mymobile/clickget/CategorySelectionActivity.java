package com.superapp.mymobile.clickget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class CategorySelectionActivity extends Activity {

    FirebaseDatabase fbdatabase;
    DatabaseReference fbref_categories;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        fbdatabase = FirebaseDatabase.getInstance();
        fbref_categories = fbdatabase.getReference("CATEGORIES");
        listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        for (int i = 0; i < 5; i++) {
            String str = (String) (i + "");
            DatabaseReference product_ref = fbref_categories.child(str);

            product_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    String cat = (String) map.get("name");

                    arrayList.add(cat);
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
                String selected_cat = (String) listView.getItemAtPosition(position);

                Intent intent = new Intent(CategorySelectionActivity.this, ProductSelectionActivity.class);
                intent.putExtra("pass_selected_cat", selected_cat);
                startActivity(intent);
            }
        });
    }//end onCreate()
}//end Activity

