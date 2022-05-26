package com.se.termproject.ui.customer.bookmark;

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
    DatabaseReference customer = database.getReference("customer");

    @Override
    protected FragmentBookmarkBinding setViewBinding() {
        return FragmentBookmarkBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initAfterViewBinding() {

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //customer.child("first").child("second").child("third").setValue("helloworld");

                db.child("Together_group_list").child("customer").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Together_group_list group = dataSnapshot.getValue(Together_group_list.class);

                        //각각의 값 받아오기 get어쩌구 함수들은 Together_group_list.class에서 지정한것
                        //gintro = group.getGintro();
                        //goaltime = group.getGoaltime();
                        //gdate = group.getGoalday();

                        //텍스트뷰에 받아온 문자열 대입하기
                        //goaltime_tv.setText(goaltime);
                        //gintro_tv.setText(gintro);
                        //gdate_tv.setText(gdate);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //Log.e("MainActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                    }
                });
            }
        });
    }
}
