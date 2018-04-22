package cn.wglgg.play.tts.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FfmpegUtil {
    public static boolean processAudio(String oldfilepath, String newfilepath, String type) {
        List<String> commend = new ArrayList<String>();
        //commend.add("D:\\ffmpeg\\bin\\ffmpeg.exe");//win
        commend.add("ffmpeg");//linux Docker
        commend.add("-f");
        commend.add("s16le");
        commend.add("-ar");
        commend.add("8000");
        commend.add("-ac");
        commend.add("2");
        commend.add("-i");
        commend.add(oldfilepath);
        commend.add("-acodec");//音频选项， 一般后面加copy表示拷贝
        commend.add("mp3");
        commend.add("-y");//表示覆盖旧文件
        commend.add(newfilepath + "." + type);
        try {
            Process process = new ProcessBuilder(commend).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();// 加上这句，系统会等待转换完成。不加，就会在服务器后台自行转换。
            System.out.println("exitCode = " + exitCode);
            Boolean flag = exitCode == 0 ? true : false;
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
