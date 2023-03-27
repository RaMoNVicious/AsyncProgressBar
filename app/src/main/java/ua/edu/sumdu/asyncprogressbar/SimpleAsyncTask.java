package ua.edu.sumdu.asyncprogressbar;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {

    private final WeakReference<TextView> mTextView;

    private final WeakReference<ProgressBar> mProgressBar;

    SimpleAsyncTask(TextView textView, ProgressBar progressBar) {
        mTextView = new WeakReference<>(textView);
        mProgressBar = new WeakReference<>(progressBar);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int n = new Random().nextInt(11);

        int DELAY = 200;
        try {
            for (int i = 0; i < n; i++) {
                publishProgress(i, n);
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Awake at last after sleeping for " + n * DELAY + " milliseconds!";
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
        setProgress(100);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        setProgress((int) 100f * values[0] / values[1]);
    }

    private void setProgress(int value) {
        mProgressBar.get().setProgress(value, true);
    }
}
