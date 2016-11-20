package com.bonch.agronet;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button scan_btn;
    //private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scan_btn = (Button)findViewById(R.id.scan_btn);
        final Activity activity = this;
        //formatTxt = (TextView)findViewById(R.id.scan_format);
        //contentTxt = (TextView)findViewById(R.id.scan_content);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_SHORT).show();
                connectToServer(result.getContents());

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void connectToServer(String str) {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {

            TextView textView13 = (TextView) findViewById(R.id.textView13);
            TextView textView14 = (TextView) findViewById(R.id.textView14);
            TextView textView15 = (TextView) findViewById(R.id.textView15);
            TextView textView16 = (TextView) findViewById(R.id.textView16);
            TextView textView17 = (TextView) findViewById(R.id.textView17);
            TextView textView18 = (TextView) findViewById(R.id.textView18);
            TextView textView19 = (TextView) findViewById(R.id.textView19);
            TextView textView20 = (TextView) findViewById(R.id.textView20);
            TextView textView21 = (TextView) findViewById(R.id.textView21);



            //URL url = new URL("http://192.168.43.29:8080/");
            URL url = new URL("http://192.168.43.29:8080/get/productInfo/");

            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();
            //String[] tokens = str.split("/");
            //String[] n_tokens = tokens[0].split(".");
            String[] tokens = new String[3];
            tokens[0]= str.substring(0,2);
            tokens[1]=str.substring(3,7);
            tokens[2]=str.substring(8);
            //Toast.makeText(this,tokens[0],Toast.LENGTH_SHORT).show();
            //String urlParameters = "registry="+n_tokens[0]+"&registrant="+n_tokens[1]+"&suffix="+tokens[1];
            String urlParameters = "registry="+tokens[0]+"&registrant="+tokens[1]+"&suffix="+tokens[2];
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
            dStream.writeBytes(urlParameters);
            dStream.flush();
            dStream.close();

            int responseCode = connection.getResponseCode();
            String output = "";
            //String output = "Request URL "+url;
            //output += System.getProperty("line.separator") + "Request Parameters " + urlParameters;
            //output += System.getProperty("line.separator") + "Response Code " + responseCode;

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            StringBuilder responseOutput = new StringBuilder();
            while ((line = br.readLine()) != null){
                responseOutput.append(line);
            }

            br.close();

            //output += System.getProperty("line.separator") + responseOutput.toString();
            JSONObject jObject = new JSONObject(responseOutput.toString());
            //JSONArray jArray = jObject.getJSONArray("AboutProduct");
            JSONObject prefix = jObject.getJSONObject("preffix");
            JSONObject company = prefix.getJSONObject("company");
            String compName = company.getString("name");
            String compAddr = company.getString("address");

            JSONObject product = jObject.getJSONObject("product");
            String prodName = product.getString("productName");
            String prodInfo = product.getString("info");
            String prodShelf = product.getString("shelfTime");

            JSONObject prodComp = product.getJSONObject("company");
            String prodCompName = prodComp.getString("name");
            String prodCompAddr = prodComp.getString("address");

            JSONObject factory = jObject.getJSONObject("factory");
            String facAddr = factory.getString("address");

            String manuDate = jObject.getString("manufactureDate");

            textView13.setText(prodName);
            textView14.setText(prodInfo);
            textView15.setText(manuDate);
            textView16.setText(prodShelf+" дней(дня)");
            textView17.setText(prodCompName);
            textView18.setText(prodCompAddr);
            textView19.setText(compName);
            textView20.setText(compAddr);
            textView21.setText(facAddr);


        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
