package com.example.pdfapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final static int REQUEST_CODE=121;
    public static double postScriptThreshold = 0.75;
    public final static int a4HeightInPX = 3508;
    public final static int a4WidthInPX = 2480;
    public static int a4HeightInPostScript = (int) (a4HeightInPX * postScriptThreshold);
    public static int a4WidthInPostScript = (int) (a4WidthInPX * postScriptThreshold);
    int pageWidthInPixel=0;
    int pageHeightInPixel=0;
    Button button;
    TextView gTotal,tvdate,tvvehicleNo,textView1,textView2,textView8;
    RecyclerView rTable;
    RecycleItemAdapt adapter;
    ArrayList<Items> arrItem=new ArrayList<>();
//    View container;
    RelativeLayout container;
    int total;
    String date,vv;
    @Override
    public void onBackPressed() {

        arrItem.clear();
        adapter.notifyDataSetChanged();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        askPermission();
        button=findViewById(R.id.button);
        tvdate=findViewById(R.id.tvdate);
        tvvehicleNo=findViewById(R.id.textView5);
        gTotal=findViewById(R.id.gTotal);
        rTable=findViewById(R.id.rTable);
        rTable.setLayoutManager(new LinearLayoutManager(this));

        textView1=findViewById(R.id.textView1);
        textView2=findViewById(R.id.textView2);
//        textView8=findViewById(R.id.textView8);

        arrItem=(ArrayList<Items>) getIntent().getExtras().getSerializable("list");
         date=(String)getIntent().getExtras().getSerializable("date");
        String vehicleNo=(String)getIntent().getExtras().getSerializable("vehicle");
        tvdate.setText(date);
//        tvvehicleNo.setText(vehicleNo);
        tvvehicleNo.append(vehicleNo);
//        textView8.append(getString(R.string.surana_automobiles));
        putAdapterandTotal();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.GONE);
                container=findViewById(R.id.outer_Container);
                 createPdf2();
            }
        });



    }

    private void putAdapterandTotal() {
       adapter = new RecycleItemAdapt(this,arrItem);
        rTable.setAdapter(adapter);
        total=findTotal();
        gTotal.setText(Integer.toString(total));
    }

    private int findTotal() {
        int sum=0;
        for(Items i:arrItem)
            sum+=i.getTotal();
        return sum;
    }



    private String fileName(String date) {
        String s="";
        for(int i=0;i<date.length();i++)
            if(date.charAt(i)!='/'){
                s+=date.charAt(i);
            }
        else{
            s+='-';
            }

        return s+".pdf";
    }

    private String template2(int p) {
        String str="";
        str="FROM "+getString(R.string.surana_automobiles);
        if(p==1){
        textView1.setText(getString(R.string.vikash_automobiles));
        textView2.setText(getString(R.string.address_vikashAutomobile));
        str="FROM "+getString(R.string.vikash_automobiles);

        for(Items i:arrItem){
            i.rate+=(.1*i.rate);
            i.total=i.rate*i.quant;
        }
        putAdapterandTotal();}
        else if(p==2){
        textView1.setText(getString(R.string.hari_om_traders));
        textView2.setText(getString(R.string.address_hariOmTraders));
        str="FROM "+getString(R.string.hari_om_traders);
        for(Items i:arrItem){
            i.rate+=(.1*i.rate);
            i.total=i.rate*i.quant;
        }
        putAdapterandTotal();}

        container=findViewById(R.id.outer_Container);
        return str;
    }


    private void askPermission(){
//        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
    }


    private void createPdf() {

        PdfDocument doc = new PdfDocument();
//        doc.setDefaultPageSize(PageSize.A4);
        for (int p = 0; p < 3; p++) {
            template2(p);
//        View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null);
            DisplayMetrics displayMetrics = new DisplayMetrics();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.getDisplay().getRealMetrics(displayMetrics);
            } else {
                this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }
//            container.setLayoutParams(new ViewGroup.LayoutParams(1189, 1684));
            container.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));

            container.layout(0, 0, displayMetrics.widthPixels-50, displayMetrics.heightPixels-20);
//            container.layout(0, 0, 2480,3508);
            int vwidth = container.getMeasuredWidth();
            int vheight = container.getMeasuredHeight();

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(vwidth, vheight, p + 1).create();
//            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(2480,3508, p+1).create();

            PdfDocument.Page page = doc.startPage(pageInfo);

            Canvas canvas = page.getCanvas();
            container.draw(canvas);
            doc.finishPage(page);
            Toast.makeText(this, "page " + (p + 1) + " Complete!", Toast.LENGTH_SHORT).show();

        }


        File dowDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filename = fileName(date);
//        String filename=date;
        File file = new File(dowDir, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            doc.writeTo(fos);
            doc.close();
            fos.close();
            Toast.makeText(this, "Sucess", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Log.d("my log", "error" + e.toString());
            Toast.makeText(this, "Error!" + e.toString(), Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPdf2() {
     String str="";
        PdfDocument doc = new PdfDocument();
        for (int p = 0; p < 3; p++) {
                    str=template2(p);
//        View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null);
            DisplayMetrics displayMetrics = new DisplayMetrics();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.getDisplay().getRealMetrics(displayMetrics);
            } else {
                this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }



            container.measure(View.MeasureSpec.makeMeasureSpec(pageWidthInPixel, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(pageHeightInPixel, View.MeasureSpec.UNSPECIFIED));

        pageHeightInPixel = container.getMeasuredHeight();
        pageWidthInPixel = container.getMeasuredWidth();
        Log.d("Values","value of page height"+pageHeightInPixel+" and value of width "+pageWidthInPixel);

            postScriptThreshold = 1.0;
//            a4HeightInPostScript = pageHeightInPixel;

        /*Convert page size from pixel into post script because PdfDocument takes
         * post script as a size unit*/

            pageHeightInPixel = (int) (pageHeightInPixel * postScriptThreshold);
            pageWidthInPixel = (int) (pageWidthInPixel * postScriptThreshold);

            container.measure(View.MeasureSpec.makeMeasureSpec(pageWidthInPixel, View.MeasureSpec.EXACTLY), View.MeasureSpec.UNSPECIFIED);
            Log.d("Values","value of page height "+container.getMeasuredHeight()+" Exact Width"+container.getMeasuredWidth());
            Log.d("Values","value of A4 page height "+a4HeightInPostScript+" width "+a4WidthInPostScript);
            pageHeightInPixel = (Math.max(container.getMeasuredHeight(), a4HeightInPostScript));

            PdfDocument.PageInfo pageInfo =
            new PdfDocument.PageInfo.Builder((pageWidthInPixel), (pageHeightInPixel), p + 1).create();
            PdfDocument.Page page = doc.startPage(pageInfo);

            container.layout(0, 0, pageWidthInPixel, pageHeightInPixel);
            Canvas c = page.getCanvas();
//            Canvas c=new Canvas();
            container.draw(c);

            Paint paint=new Paint();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.setTextSize(38f);
            c.drawText(str,40,pageHeightInPixel-80,paint);

            doc.finishPage(page);
//
            Toast.makeText(this, "page " + (p + 1) + " Complete!", Toast.LENGTH_SHORT).show();

        }


//        File dowDir= new File(Environment.getExternalStoragePublicDirectory(),"GNP");
//        File dowDir=new File(Environment.getExternalStorageDirectory(),"GNP");

        String folderName = "GNP";
        String filename=fileName(date);
        File folder= new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),folderName);
        if (!folder.exists()){
            if (folder.mkdirs()) {
                // Folder created successfully
                Toast.makeText(MainActivity.this, "Folder created successfully", Toast.LENGTH_SHORT).show();
            } else {
                // Folder creation failed
                Toast.makeText(MainActivity.this, "Failed to create folder", Toast.LENGTH_SHORT).show();
            }
        }
        // Folder already exists
        Toast.makeText(MainActivity.this, "Folder already exists", Toast.LENGTH_SHORT).show();

        // If the folder exists, create a File object representing the new file
        File file = new File(folder,filename);
//        File file=new File(dowDir,filename);

        try {
            FileOutputStream fos=new FileOutputStream(file);
//            PdfWriter pdfWriter=new PdfWriter(file);
//            com.itextpdf.kernel.pdf.PdfDocument pdfDocument=new com.itextpdf.kernel.pdf.PdfDocument(pdfWriter);
//            Document doc=new Document(pdfDocument);
//            pdfDocument.setDefaultPageSize(PageSize.A4);
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                this.getDisplay().getRealMetrics(displayMetrics);
//            } else {
//                this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            }
//            container.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
//                    View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));
//            container.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//
//            int vwidth = container.getMeasuredWidth();
//            int vheight = container.getMeasuredHeight();
//
//            Bitmap bitmap = Bitmap.createBitmap(vwidth,vheight, Bitmap.Config.ARGB_8888);
//            ByteArrayOutputStream stream=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

//            byte[] bitData=stream.toByteArray();
//            Image image=new Image(ImageDataFactory.create(bitData));
//
//            doc.add(image);
            doc.writeTo(fos);
            doc.close();
            fos.close();
            Toast.makeText(this,"Sucess",Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Log.d("my log","error"+e.toString());
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    private void createPdf1() {
//        RelativeLayout layout=findViewById(R.id.outer_Container);

// Changes the height and width to the specified *pixels* w=1189, h=1684

        PdfDocument doc = new PdfDocument();
//        doc.setDefaultPageSize(PageSize.A4);
        for (int p = 0; p < 3; p++) {
            template2(p);
//        View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null);
            DisplayMetrics displayMetrics = new DisplayMetrics();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                this.getDisplay().getRealMetrics(displayMetrics);
            } else {
                this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            }
            container.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));

            container.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);


        pageHeightInPixel = container.getMeasuredHeight();
        pageWidthInPixel = container.getMeasuredWidth();

//            container.setDrawingCacheEnabled(true);
//
//            container.buildDrawingCache();
//
//            Bitmap bitmap = container.getDrawingCache();
            Bitmap bitmap=viewToBitmap(container,pageWidthInPixel,pageHeightInPixel);

//            Bitmap bitmap = Bitmap.createBitmap(pageWidthInPixel,pageHeightInPixel, Bitmap.Config.ARGB_8888);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, a4WidthInPX,a4HeightInPX, true);


            PdfDocument.PageInfo pageInfo =
            new PdfDocument.PageInfo.Builder(a4WidthInPX,a4HeightInPX, p + 1).create();
            PdfDocument.Page page = doc.startPage(pageInfo);
            Canvas canvas=page.getCanvas();
            canvas.drawBitmap(resized,0f,0f,null);

            doc.finishPage(page);
//
            Toast.makeText(this, "page " + (p + 1) + " Complete!", Toast.LENGTH_SHORT).show();

        }


        File dowDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filename=date.replace('/','-')+".pdf";
//        String filename=date;
        File file=new File(dowDir,filename);
        try {
            FileOutputStream fos=new FileOutputStream(file);
//            PdfWriter pdfWriter=new PdfWriter(file);
//            com.itextpdf.kernel.pdf.PdfDocument pdfDocument=new com.itextpdf.kernel.pdf.PdfDocument(pdfWriter);
//            Document doc=new Document(pdfDocument);
//            pdfDocument.setDefaultPageSize(PageSize.A4);
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                this.getDisplay().getRealMetrics(displayMetrics);
//            } else {
//                this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//            }
//            container.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
//                    View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));
//            container.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//
//            int vwidth = container.getMeasuredWidth();
//            int vheight = container.getMeasuredHeight();
//
//            Bitmap bitmap = Bitmap.createBitmap(vwidth,vheight, Bitmap.Config.ARGB_8888);
//            ByteArrayOutputStream stream=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

//            byte[] bitData=stream.toByteArray();
//            Image image=new Image(ImageDataFactory.create(bitData));
//
//            doc.add(image);

            doc.writeTo(fos);
            doc.close();
            fos.close();
            Toast.makeText(this,"Sucess",Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Log.d("my log","error"+e.toString());
            Toast.makeText(this,"Sucess"+e.toString(),Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static Bitmap viewToBitmap(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

}