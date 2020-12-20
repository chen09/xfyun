package xfyun.test02;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.List;

public class ToText {
    ////    private static final String JSON_FILE_PATH = ToText.class.getResource("/").getPath() + "/json/F2_20200911.json";
//    private static final String JSON_FILE_PATH = "/Users/chenxin/Downloads/out.json";
//    private static final String TEXT_FILE_PATH = "/Users/chenxin/Downloads/out.txt";
    private static final Type REVIEW_TYPE = new TypeToken<List<ResultRow>>() {
    }.getType();

    public static void main(String[] args) throws IOException {
        String json = args[0];
        String text = args[1];
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(json));
        List<ResultRow> data = gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list

        PrintStream out = null;
        try {
            out = new PrintStream(new FileOutputStream(text, false), true, "UTF-8");
            PrintStream finalOut = out;
            data.stream().forEach(resultRow -> {
                System.out.println(resultRow.onebest);
                finalOut.println(resultRow.onebest);
            });
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }

    }

}
