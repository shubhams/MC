package com.iiitd.mcproject.TabFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.iiitd.mcproject.RailsServerSignUp;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by udayantandon on 01/12/14.
 */
public class FetchProfile extends AsyncTask<Void, Void, String> {
    int id;
    private ProgressDialog pDialog;
    private final String UrlRails = "https://tranquil-stream-2635.herokuapp.com/profile";
    private Context context;
    private StringBuilder sb=new StringBuilder();
    public FetchProfile(int id,Context cnt){
        this.id=id;
        context=cnt;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Logging in...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        InputStream inputStream=null;
        try{
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(UrlRails);
            String json="";

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id",id);

            }catch (JSONException e){
                e.printStackTrace();
            }
            json = jsonObject.toString();
            Log.d("json", json);

            StringEntity se = new StringEntity(json);
            httpPost.setHeader("Accept","application/json");
            httpPost.setHeader("Content-type","application/json");
            httpPost.setEntity(se);

            org.apache.http.HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();
            StatusLine sl = httpResponse.getStatusLine();
            Log.v("debug", Integer.toString(sl.getStatusCode()) + " " + sl.getReasonPhrase());

            try {
                int ch;
                while ((ch = inputStream.read()) != -1) {
                    sb.append((char) ch);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }

        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    protected void onPostExecute(String str)
    {
        pDialog.dismiss();
        if(str != null)
        {
            Log.d("system response in login",str);
        }
        else
            Log.d("system response","is null");
    }
}
