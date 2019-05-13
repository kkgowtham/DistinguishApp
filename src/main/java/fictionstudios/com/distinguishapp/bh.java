package fictionstudios.com.distinguishapp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bh {

    ArrayList<String> list1=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();
    String[] s1=new String[50];
    String[] s2=new String[50];
    private static final String TAG = "MainActivity";
    final JSONArray array1=new JSONArray();
    int i=0;
    /*    for (String a:list1)
    {
        s1[i]=a;
        i++;
        JSONObject object=new JSONObject();
        try {
            object.put("value",a);
            array1.put(object);
            s1[i]=a;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    final JSONArray array2=new JSONArray();
    int j=0;
        for (String a:list2)
    {
        s2[j]=a;
        j++;
        JSONObject object=new JSONObject();
        try {
            object.put("value",a);

            array2.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        Log.d(TAG, "onCreate: "+list1.toString());
        Log.d(TAG, "onCreate: "+list2.toString());
    String url="http://192.168.1.105/distinguish/insert.php";
    StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Log.d(TAG, "onResponse: "+response);
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "onErrorResponse: "+error);
        }
    })
    {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> map=new HashMap<>();
            map.put("thumbnail","ddd");
            map.put("term1","1");
            map.put("term2","2");
            map.put("explain1",array1.toString());
            map.put("explain2",array2.toString());
            map.put("addedby","addedby");
            map.put("datetime","datetime");
            map.put("description","description");
            map.put("category","category");
            return map;
        }


    };

    RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(request);*/
}
