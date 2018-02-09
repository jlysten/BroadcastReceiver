package com.example.joel.broadcastreceiver;

import android.os.AsyncTask;

public class MyAsync extends AsyncTask<Void,Void,String> {
    @Override
    protected String doInBackground(Void... voids) {
        return "Background task complete";
    }
}
