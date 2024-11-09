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

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    final static int REQUEST_CODE=111;
    private static DecimalFormat decfor=new DecimalFormat("0.00");
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
            EditText tvPerc = (EditText) v.findViewById(R.id.tvPerc);
//            EditText tvTotal=(EditText)v.findViewById(R.id.tvTotal);
//            TextView tvTotal = (TextView) v.findViewById(R.id.tvTotal);

            Items item = new Items();

            int sr = i+1;
            item.setId(sr);

            item.setItemName(tvItems.getText().toString());

            int q = Integer.parseInt(tvQu.getText().toString());
            item.setQuant(q);

            double r = Double.parseDouble(tvRate.getText().toString());
            int p = Integer.parseInt(tvPerc.getText().toString());
            int divisor=p+100;
//            r=(r/divisor)*100;
            r=Double.parseDouble(decfor.format((r/divisor)*100));
            item.setRate(r);
            item.setPerc(p);


//                tvTotal.setText(q*r);
            double taxablevalue=q*r;
            double cgst=Double.parseDouble(decfor.format(taxablevalue*p/200));
            double sgst=Double.parseDouble(decfor.format(taxablevalue*p/200));
            // cgst=Math.round(cgst*100)/100;
//            sgst=Math.round(sgst*100)/100;
            double total=taxablevalue+cgst+sgst;
//            total=Math.round(total*100)/100;
                item.setTaxableValue(Double.parseDouble(decfor.format(taxablevalue)));
                item.setCgst(Double.parseDouble(decfor.format(cgst)));
                item.setSgst(Double.parseDouble(decfor.format(sgst)));
                item.setTotal(Double.parseDouble(decfor.format(total)));

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
       EditText tvPerc=(EditText)v.findViewById(R.id.tvPerc);
//      TextView tvTotal=(TextView)v.findViewById(R.id.tvTotal);
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