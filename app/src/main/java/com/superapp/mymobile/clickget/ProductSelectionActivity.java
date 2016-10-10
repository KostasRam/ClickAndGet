package com.superapp.mymobile.clickget;

import android.app.Activity;
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


public class ProductSelectionActivity extends Activity {

    FirebaseDatabase fbdatabase;
    DatabaseReference fbref_products;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    String selected_cat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_selection);

        selected_cat = getIntent().getExtras().getString("pass_selected_cat");
        listView = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        fbdatabase = FirebaseDatabase.getInstance();
        fbref_products = fbdatabase.getReference("PRODUCTS/SM_0/" + selected_cat);                   /*  NA GINEI SM_0 DYNAMIKO  */


        //Εφαρμόζουμε FOR-Loop μεταξύ των προϊόντων της παραπάνω διαδρομής και προσθέτουμε την τιμή του κλειδιού "productName" στην "arrayList".
        fbref_products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot: dataSnapshot.getChildren()) {
                    String productName = productSnapshot.child("productName").getValue(String.class);
                    arrayList.add(productName);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        //Εφαρμογή Listener με τις ενέργειες που θα γίνονται όταν επιλεχθεί ένα προϊόν.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){                       /*  ΝΑ ΦΤΙΑΧΤΕΙ ΤΙ ΘΑ ΚΑΝΕΙ Η ΕΦΑΡΜΟΓΗ ΟΤΑΝ ΕΠΙΛΕΓΕΤΑΙ ΠΡΟΙΟΝ  */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected_product = (String) listView.getItemAtPosition(position);
                System.out.println("SELECTED_PRODUCT: " + selected_product);
            }
        });
    }//end onCreate()
}//end Activity
