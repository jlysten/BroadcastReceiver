package com.example.joel.broadcastreceiver;

import android.Manifest;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int JOB_ID = 101;
    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.cons);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE}, 100);
            }
        }
    @Override
    public void onRequestPermissionsResult(int requestCode,
    String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    finish();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    Snackbar snackbar = Snackbar
                .make(constraintLayout, "Click Ok to Start Job Scheduler", Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(constraintLayout, "Starting Job!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                        startJob();
                    }
                });

        snackbar.show();
    }

    private void startJob() {
        ComponentName componentName = new ComponentName(this,MJobScheduler.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,componentName);
        builder.setPeriodic(5000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);

        jobInfo = builder.build();
        jobScheduler= (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);


        jobScheduler.schedule(jobInfo);
        Snackbar snackbar2 =Snackbar.make(constraintLayout, "Starting AsyncTask!", Snackbar.LENGTH_SHORT);
        snackbar2.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case (R.id.button): jobScheduler.cancel(JOB_ID);
                Snackbar snackbar3 =Snackbar.make(constraintLayout, "AsyncTask Cancelled", Snackbar.LENGTH_INDEFINITE);
                snackbar3.show();
        }
    }
}
