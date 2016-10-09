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

    FirebaseDatabase fbdatabase;
    DatabaseReference fbref_products;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    String selected_cat;
    //int numOfProducts;


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

        /*
        fbref_products.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numOfProducts = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        */

        //Loop μεταξύ όλων των παιδιών στη συγκεκριμένη διαδρομή της βάσης και add των προϊόντων στη λίστα arrayList.
        for (int i = 0; i < 2; i++) {     //numOfProducts; i++) {                                    //ΑΝΤΙ ΓΙΑ 2, ΝΑ ΜΠΕΙ ΜΕΤΑΒΛΗΤΗ numOfProducts.
            String str = i + "";
            DatabaseReference product_ref = fbref_products.child(str);

            product_ref.addListenerForSingleValueEvent(new ValueEventListener() {
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){                       /*  ΝΑ ΦΤΙΑΧΤΕΙ ΤΙ ΘΑ ΚΑΝΕΙ Η ΕΦΑΡΜΟΓΗ ΟΤΑΝ ΕΠΙΛΕΓΕΤΑΙ ΠΡΟΙΟΝ  */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selected_product = (String) listView.getItemAtPosition(position);
                Log.v("SELECTED_PRODUCT",selected_product);
            }
        });
    }//end onCreate()
}//end Activity
