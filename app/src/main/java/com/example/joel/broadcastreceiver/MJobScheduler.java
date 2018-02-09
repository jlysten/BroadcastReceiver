package com.example.joel.broadcastreceiver;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.widget.Toast;

public class MJobScheduler extends JobService {

    private MyAsync myAsync;
    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        myAsync = new MyAsync(){
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                jobFinished(jobParameters,false);

            }
        };
        myAsync.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        myAsync.cancel(true);
        return false;
    }
}
