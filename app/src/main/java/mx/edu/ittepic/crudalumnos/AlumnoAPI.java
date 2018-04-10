package mx.edu.ittepic.crudalumnos;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Bryan on 24/03/2018.
 */

public class AlumnoAPI extends AsyncTask <String, Void, Void> {

    String data = "";
    ArrayList<Alumno> alumnos = new ArrayList<>();

    @Override
    protected Void doInBackground(String... params) {
        if(params.length == 4){
            postModificar(params[0], params[1], params[2], params[3]);
        }if(params.length == 3){
            postAlumnos(params[0], params[1], params[2]);
        }if(params.length == 2){
            postEliminar(params[0], params[1]);
        }else {
            getAlumnos();
        }
        return null;
    }

    void getAlumnos(){
        try {
            URL url = new URL("http://172.20.2.145:8080/datos1/obtener_alumnos.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject JO = new JSONObject(data);
            JSONArray JA = (JSONArray) JO.getJSONArray("alumnos");
            for(int i = 0 ;i < JA.length(); i++){
                JSONObject JE = (JSONObject) JA.getJSONObject(i);
                alumnos.add(new Alumno(JE.getString("nombre"),
                        JE.getString("direccion"),
                        JE.getInt("idalumno")));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    boolean postAlumnos(String urlString, String nombre, String direccion){
        try {
            String json_string;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlString);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("nombre",nombre);
            json.put("direccion",direccion);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();
            int respuesta = 0;
            respuesta = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            }
        }catch (java.net.MalformedURLException e){
            return false;
        }catch (IOException e) {
            return false;
        }catch (org.json.JSONException e){
            return false;
        }
        return true;
    }

    boolean postEliminar(String urlString, String ids){
        try {
            int id = Integer.parseInt(ids);
            String json_string;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlString);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("idalumno",id+"");
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();
            int respuesta = 0;
            respuesta = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            }
        }catch (java.net.MalformedURLException e){
            return false;
        }catch (IOException e) {
            return false;
        }catch (org.json.JSONException e){
            return false;
        }
        return true;
    }

    boolean postModificar(String urlString, String id, String nombre, String direccion){
        try {
            String json_string;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL(urlString);
            HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("idalumno",id);
            json.put("nombre",nombre);
            json.put("direccion",direccion);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            writer.write(json.toString());
            writer.flush();
            writer.close();
            os.close();
            int respuesta = 0;
            respuesta = connection.getResponseCode();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            if (respuesta == HttpURLConnection.HTTP_OK) {
                while ((json_string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(json_string + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                connection.disconnect();
            }
        }catch (java.net.MalformedURLException e){
            return false;
        }catch (IOException e) {
            return false;
        }catch (org.json.JSONException e){
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        MainActivity.listaAlumnos.clear();
        for(Alumno a: alumnos){
            MainActivity.listaAlumnos.add(a);
        }
    }
}
