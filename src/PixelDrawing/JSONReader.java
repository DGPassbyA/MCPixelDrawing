package PixelDrawing;

import com.google.gson.Gson;
import java.io.*;

public class JSONReader {
    public static String readJSON(String fileName){
        try{
            FileInputStream fileInput = new FileInputStream(fileName);
            InputStreamReader reader = new InputStreamReader(fileInput);
            BufferedReader buffReader = new BufferedReader(reader);
            String str = "";
            String strTmp = "";
            while((strTmp = buffReader.readLine())!=null){
                str = str.concat(strTmp);
            }
            buffReader.close();
            return str;
        }catch (FileNotFoundException e){
            System.err.println("FileNotFoundException : " + e);
            e.printStackTrace();
            return null;
        }catch (IOException e){
            System.err.println("IOException : " + e);
            e.printStackTrace();
            return null;
        }
    }
    public static Block[] strToList(String str){
        Gson gson = new Gson();
        return gson.fromJson(str,Block[].class);
    }
}
