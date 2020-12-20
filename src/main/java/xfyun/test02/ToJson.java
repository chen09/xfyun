package xfyun.test02;

import com.google.gson.Gson;
import com.iflytek.msp.lfasr.LfasrClient;
import com.iflytek.msp.lfasr.model.Message;

import java.io.*;
import java.util.concurrent.TimeUnit;

public class ToJson {
    public static void main(String[] args) throws InterruptedException {
        String appId = args[0];
        String secretKey = args[1];
        String audioFile = args[2];
        String jsonFile = args[3];
        LfasrClient lfasrClient = LfasrClient.getInstance(appId, secretKey, 1, 1, 1, 10800000, 10800000, null);
        standard(lfasrClient, audioFile, jsonFile);
    }

    /**
     * 简单 demo 样例
     *
     * @throws InterruptedException e
     */
    private static void standard(LfasrClient lfasrClient, String audioFile, String jsonFile) throws InterruptedException {
        //1、创建客户端实例
//        lfasrClient

        //2、上传
        System.out.println(audioFile);
        Message task = lfasrClient.upload(audioFile);
        String taskId = task.getData();
        System.out.println("转写任务 taskId：" + taskId);

//        String taskId = "b4204783e85243c890aee0796e708b27";

        //3、查看转写进度
        int status = 0;
        while (status != 9) {
            Message message = lfasrClient.getProgress(taskId);
            Gson gson = new Gson();
            Status statusTask = gson.fromJson(message.getData(), Status.class);

            status = statusTask.status;
            System.out.println(message.getData());
            TimeUnit.SECONDS.sleep(2);
        }
        //4、获取结果
        Message result = lfasrClient.getResult(taskId);
        System.out.println("转写结果: \n" + result.getData());

        PrintStream out = null;

        try {
            out = new PrintStream(new FileOutputStream(jsonFile, false), true, "UTF-8");
            out.println(result.getData());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }

        //退出程序，关闭线程资源，仅在测试main方法时使用。
        System.exit(0);
    }
}
