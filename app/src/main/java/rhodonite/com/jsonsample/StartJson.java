package rhodonite.com.jsonsample;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class StartJson {

    private static StartJson INSTANCE = null;

    private StartJson() {
    }

    public static StartJson getInstance() {
        if (INSTANCE == null)
            INSTANCE = new StartJson();
        return (INSTANCE);
    }

    public interface StartJsonListener {
        void onSuccess(ArrayList<ListModel> success);

        void onTotalCount(String totalCount);

        void onError(String error);
    }

    public void func(String url, StartJsonListener listener) {
        ActivateTask task = new ActivateTask(url, listener);
        task.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public static class ActivateTask extends AsyncTask {
        String urlString = "";
        StartJsonListener SJlistener;

        public ActivateTask(String urls, StartJsonListener listener) {
            urlString = urls;
            SJlistener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            String result = "";
            InputStream inputStream = null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpResponse httpResponse = client.execute(new HttpGet(urlString));
                inputStream = httpResponse.getEntity().getContent();
                if (inputStream != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {
                        publishProgress((int) ((i / (float) 720) * 10));
                        result += line;
                        i++;
                    }
                    inputStream.close();
                    return result;
                } else
                    result = "Did not work!";
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return result;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
                JSONObject jsonObj = new JSONObject(o.toString());
                if (jsonObj.getString("message").equals("OK")) {
                    jsonObj = new JSONObject(jsonObj.getString("data"));
                    SJlistener.onTotalCount(jsonObj.getString("totalCount"));
                    JSONArray itemsArray = jsonObj.getJSONArray("items");
                    ArrayList<ListModel> ListData = new ArrayList<ListModel>();
                    for (int i = 0; i < itemsArray.length(); i++) {
                        ArrayList<String> TagsArray = new ArrayList<String>();
                        ListModel mListModel = new ListModel();
                        jsonObj = new JSONObject(itemsArray.getJSONObject(i).getString("user"));
                        JSONArray numberList = itemsArray.getJSONObject(i).getJSONArray("tags");
                        for (int ii = 0; ii < numberList.length(); ii++)
                            TagsArray.add(ii, numberList.getString(ii));
                        mListModel.addData(jsonObj.getString("nickName"), jsonObj.getString("imageUrl"), TagsArray);
                        ListData.add(i, mListModel);
                    }
                    SJlistener.onSuccess(ListData);

                } else
                    SJlistener.onError(o.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
