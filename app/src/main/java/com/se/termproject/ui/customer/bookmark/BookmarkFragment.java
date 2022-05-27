package com.se.termproject.ui.customer.bookmark;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.se.termproject.base.java.BaseFragment;
import com.se.termproject.databinding.FragmentBookmarkBinding;

public class BookmarkFragment extends BaseFragment<FragmentBookmarkBinding> {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference();
    DatabaseReference customer = database.getReference("customer").child("first");

    @Override
    protected FragmentBookmarkBinding setViewBinding() {
        return FragmentBookmarkBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.child("").child("second").child("third").setValue("helloworld");

                /*
                DatabaseReference customer1 = database.getReference("customer");
                customer1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("MainActivity", "Single ValueEventListener : " + snapshot.getClass());
                            //Log.d("MainActivity", "Single ValueEventListener : " + snapshot.classgetChildren());
                        }
                        //출처: https://stack07142.tistory.com/282 [Hello World:티스토리]

                    }



                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

                // 출처: https://stack07142.tistory.com/282 [Hello World:티스토리]
            }
        });
    }
}
