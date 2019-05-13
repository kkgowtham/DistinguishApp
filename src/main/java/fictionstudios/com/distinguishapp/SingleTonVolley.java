package fictionstudios.com.distinguishapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleTonVolley {
    static RequestQueue requestQueue=null;
    public static RequestQueue getInstance(Context context)
    {
        if(requestQueue==null)
        {
            requestQueue=Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
}
