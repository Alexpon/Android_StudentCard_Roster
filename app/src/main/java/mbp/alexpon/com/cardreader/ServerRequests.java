package mbp.alexpon.com.cardreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by apple on 15/9/23.
 */
public class ServerRequests {
    private ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://Address/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void fetchCourseInBackground(GetCourseCallBack courseCallBack){
        progressDialog.show();
        new fetchCourseAsyncTask(courseCallBack).execute();
    }

    public void storeUserDataInBackground(User user, GetUserCallBack userCallback) {
        progressDialog.show();
        new storeUserDataAsyncTask(user, userCallback).execute();
    }

    public void storeRosterDataInBackground(int index, String[] student, int[] year, int[] month, int[] date ,
                                            int[] hour, int[] minute, int[] second, String course,
                                            GetUserCallBack userCallback) {
        progressDialog.show();
        new storeRosterDataAsyncTask(index, student, year, month, date,
                hour, minute, second, course, userCallback).execute();
    }

    public class fetchCourseAsyncTask extends AsyncTask<Void, Void, Course> {

        GetCourseCallBack courseCallBack;


        public fetchCourseAsyncTask(GetCourseCallBack courseCallBack) {
            this.courseCallBack = courseCallBack;
        }

        @Override
        protected Course doInBackground(Void... params) {

            Course returnedCourse = null;
            String result = "";

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpParams);
            HttpPost httpPost = new HttpPost(SERVER_ADDRESS + "NUCourse_StudentCard.php");

            try {
                HttpResponse httpResponse = client.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);
                //JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);
                if(jsonArray.length() == 0){
                    returnedCourse = null;
                }
                else{
                    int index = jsonArray.length();
                    int[] id = new int[jsonArray.length()];
                    String[] course = new String[jsonArray.length()];
                    for(int i=0; i<index; i++){
                        JSONObject stock_data = jsonArray.getJSONObject(i);
                        Log.i("TEST", stock_data.getInt("class_id")+stock_data.getString("class_name"));
                        id[i] = stock_data.getInt("class_id");
                        course[i] = stock_data.getString("class_name");
                    }
                    returnedCourse = new Course(index, id, course);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return returnedCourse;
        }

        @Override
        protected void onPostExecute(Course returnedCourse) {
            progressDialog.dismiss();
            courseCallBack.done(returnedCourse);
            super.onPostExecute(returnedCourse);
        }
    }

    public class storeUserDataAsyncTask extends AsyncTask<Void, Void, Void> {

        User user;
        GetUserCallBack userCallBack;

        public storeUserDataAsyncTask(User user, GetUserCallBack userCallback) {
            this.user = user;
            this.userCallBack = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("card_id", user.card_id));
            dataToSend.add(new BasicNameValuePair("student_id", user.student_id));
            dataToSend.add(new BasicNameValuePair("student_name", user.student_name));
            dataToSend.add(new BasicNameValuePair("department", user.department));
            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);

            HttpPost post = new HttpPost(SERVER_ADDRESS + "NUPerso_StudentCard.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend, HTTP.UTF_8));
                client.execute(post);
          } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class storeRosterDataAsyncTask extends AsyncTask<Void, Void, Void> {
        int index;
        String[] student;
        int[] year, month, date , hour, minute, second;
        String course;
        GetUserCallBack userCallBack;

        public storeRosterDataAsyncTask(int index, String[] student, int[] year, int[] month, int[] date ,
                                        int[] hour, int[] minute, int[] second,
                                        String course, GetUserCallBack userCallback) {
            this.index = index;
            this.student = student;
            this.year = year;
            this.month = month;
            this.date = date;
            this.hour = hour;
            this.minute = minute;
            this.second = second;
            this.course = course;
            this.userCallBack = userCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();

            dataToSend.add(new BasicNameValuePair("course", course));
            dataToSend.add(new BasicNameValuePair("index", index+""));
            for(int i=0; i<index; i++){
                dataToSend.add(new BasicNameValuePair("student"+i, student[i]));
                dataToSend.add(new BasicNameValuePair("year"+i, year[i]+""));
                dataToSend.add(new BasicNameValuePair("month"+i, month[i]+""));
                dataToSend.add(new BasicNameValuePair("date"+i, date[i]+""));
                dataToSend.add(new BasicNameValuePair("hour"+i, hour[i]+""));
                dataToSend.add(new BasicNameValuePair("minute"+i, minute[i]+""));
                dataToSend.add(new BasicNameValuePair("second"+i, second[i]+""));

                dataToSend.add(new BasicNameValuePair("time"+i, year[i]+"-"+month[i]+"-"+date[i]+" "+hour[i]+":"+minute[i]+":"+second[i]));
            }

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "NURoster_StudentCard.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend, HTTP.UTF_8));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }
    }

}
