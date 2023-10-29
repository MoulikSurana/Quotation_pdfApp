package com.example.pdfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    final static int REQUEST_CODE=111;
    ImageButton add;
    LinearLayout layoutList;
    Button submit;
    EditText et_date,et_vehicle;
    ArrayList<Items> array=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        askPermission();
        add=findViewById(R.id.addbtn);
        layoutList=findViewById(R.id.layoutList);
        submit=findViewById(R.id.submit);
        et_date=findViewById(R.id.et_date);
        et_vehicle=findViewById(R.id.et_vehicle);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            addView();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitdetails();
            }
        });

    }

    private void submitdetails() {

        for(int i=0;i<layoutList.getChildCount();i++) {
            View v = layoutList.getChildAt(i);
//            EditText tvSrno = (EditText) v.findViewById(R.id.tvSrno);

            EditText tvItems = (EditText) v.findViewById(R.id.tvItems);
            EditText tvQu = (EditText) v.findViewById(R.id.tvQu);
            EditText tvRate = (EditText) v.findViewById(R.id.tvRate);
//            EditText tvTotal=(EditText)v.findViewById(R.id.tvTotal);
//            TextView tvTotal = (TextView) v.findViewById(R.id.tvTotal);

            Items item = new Items();

            int sr = i+1;
            item.setId(sr);

            item.setItemName(tvItems.getText().toString());

            int q = Integer.parseInt(tvQu.getText().toString());
            item.setQuant(q);

            int r = Integer.parseInt(tvRate.getText().toString());
            item.setRate(r);


//                tvTotal.setText(q*r);
                item.setTotal(q*r);

            array.add(item);
        }
        String d=et_date.getText().toString();
        String v=et_vehicle.getText().toString();
//        Date d1=(Date)et_date.getText();
            Intent intent=new Intent(MainActivity2.this,MainActivity.class);
            Bundle b=new Bundle();
            b.putSerializable("list",array);
            b.putSerializable("date",d);
            b.putSerializable("vehicle",v);
            intent.putExtras(b);
            startActivity(intent);
            array.clear();

    }

    private void addView() {
        final View v=getLayoutInflater().inflate(R.layout.design_item,null,false);

//        EditText tvSrno=(EditText)v.findViewById(R.id.tvSrno);
       EditText tvItems=(EditText)v.findViewById(R.id.tvItems);
       EditText tvQu=(EditText)v.findViewById(R.id.tvQu);
       EditText tvRate=(EditText)v.findViewById(R.id.tvRate);
//       EditText tvTotal=(EditText)v.findViewById(R.id.tvTotal);
      TextView tvTotal=(TextView)v.findViewById(R.id.tvTotal);
       ImageButton imageButton=(ImageButton)v.findViewById(R.id.removeBtn);

       imageButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               layoutList.removeView(v);
           }
       });

        layoutList.addView(v);

    }
    private void askPermission(){
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);

    }


}