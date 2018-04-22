package cn.wglgg.play.tts.web;

import cn.wglgg.play.tts.entity.VoiceEntity;
import cn.wglgg.play.tts.service.XunfeiService;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/tts")
public class TTSController {

    @Resource
    private XunfeiService xunfeiService;

    @PostMapping("/t")
    public Map<String,Object> tts(VoiceEntity vo) {

        return xunfeiService.tts(vo);
    }

    @RequestMapping("/d")
    public void download(String fname, HttpServletResponse response) throws IOException {
        String basePath = System.getProperty("java.io.tmpdir");
        //下载文件路径
        String audioPath = basePath + System.getProperty("file.separator") + fname;
        File file = new File(audioPath);
        String downloadFielName = new String(fname.getBytes("UTF-8"), "iso-8859-1");

        byte[] data = FileUtils.readFileToByteArray(file);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + downloadFielName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }
}
