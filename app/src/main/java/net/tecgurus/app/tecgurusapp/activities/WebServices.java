package net.tecgurus.app.tecgurusapp.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.stream.MalformedJsonException;

import net.tecgurus.app.tecgurusapp.R;
import net.tecgurus.app.tecgurusapp.web.FirstNode;
import net.tecgurus.app.tecgurusapp.web.RestClient;
import net.tecgurus.app.tecgurusapp.web.Result;
import net.tecgurus.app.tecgurusapp.web.RetrofitCore;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServices extends AppCompatActivity {

    private TextView mResult, mRestResult;
    private EditText mCode;
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap_service);

        mResult = findViewById(R.id.activity_soap_service_result);
        mRestResult = findViewById(R.id.activity_soap_service_result_rest);
        mCode = findViewById(R.id.activity_soap_service_code);

        findViewById(R.id.activity_soap_service_button_rest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerInfoRest();
            }
        });
        findViewById(R.id.activity_soap_service_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ObtenerVersion().execute();
            }
        });
    }
    //endregion

    //region Local Methods
    private void obtenerInfoRest(){
        String code = mCode.getText().toString();
        Call<FirstNode> all = RetrofitCore.getInstance().start(RestClient.class)
                .getAllCountryInfo(code);

        all.enqueue(new Callback<FirstNode>() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onResponse(@NonNull Call<FirstNode> call, @NonNull Response<FirstNode> response) {
                if (response.body() != null){
                    if (response.body().getRestResponse() != null){
                        List<Result> result = response.body().getRestResponse().getResult();
                        if (result != null && !result.isEmpty()){
                            StringBuilder value = new StringBuilder();
                            for (Result r: result) {
                                value.append("-----------------\n").append(r.toString());
                            }
                            mRestResult.setText(value.toString());
                        }else{
                            mRestResult.setText(getString(R.string.no_data_found));
                        }
                    }else{
                        mRestResult.setText(getString(R.string.no_data_found));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FirstNode> call, @NonNull Throwable t) {
                if (t instanceof ConnectException){
                    mRestResult.setText(getString(R.string.bad_internet_connection));
                }else if (t instanceof MalformedJsonException){
                    mRestResult.setText(getString(R.string.invalid_params));
                }else{
                    mRestResult.setText(t.toString());
                }
            }
        });
    }

    private String getVersionInfo(){
        String URL = "http://webservice.aspsms.com/aspsmsx2.asmx";
        String SOAP_ACTION = "https://webservice.aspsms.com/aspsmsx2.asmx/VersionInfo";
        String NAMESPACE = "http://webservice.aspsms.com/aspsmsx2";
        String METHOD = "VersionInfo";

        String result = null;

        SoapObject request = new SoapObject(NAMESPACE, METHOD);

        SoapSerializationEnvelope evelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        evelope.dotNet = true;
        evelope.setOutputSoapObject(request);

        HttpTransportSE ht = new HttpTransportSE(URL);
        ht.debug = true;

        SoapPrimitive response = null;

        try {
            ht.call(SOAP_ACTION, evelope);
            response = (SoapPrimitive) evelope.getResponse();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        if (response != null){
            result = response.toString();
        }
        return result;
    }
    //endregion



    //region Classes
    @SuppressLint("StaticFieldLeak")
    public class ObtenerVersion extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return getVersionInfo();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mResult.setText(s);
        }
    }
    //endregion
}
