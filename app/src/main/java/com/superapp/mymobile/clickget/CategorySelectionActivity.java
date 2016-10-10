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


public class CategorySelectionActivity extends Activity {

    FirebaseDatabase fbdatabase;
    DatabaseReference fbref_categories;
    ListView listView_categories;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        fbdatabase = FirebaseDatabase.getInstance();
        fbref_categories = fbdatabase.getReference("CATEGORIES");
        listView_categories = (ListView) findViewById(R.id.listView_categories);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView_categories.setAdapter(adapter);


        //Εφαρμόζουμε FOR-Loop μεταξύ των κατηγοριών της παραπάνω διαδρομής και προσθέτουμε την τιμή του κλειδιού "name" στην "arrayList".
        fbref_categories.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    String category = categorySnapshot.child("name").getValue(String.class);
                    arrayList.add(category);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Όταν επιλεχθεί μία κατηγορία, την περνάμε στην ProductSelectionActivity.
        listView_categories.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_cat = (String) listView_categories.getItemAtPosition(position);

                Intent intent = new Intent(CategorySelectionActivity.this, ProductSelectionActivity.class);
                intent.putExtra("pass_selected_cat", selected_cat);
                startActivity(intent);
            }
        });
    }//end onCreate()
}//end Activity

