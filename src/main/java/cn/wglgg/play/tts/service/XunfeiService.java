package cn.wglgg.play.tts.service;

import cn.wglgg.play.tts.entity.VoiceEntity;
import cn.wglgg.play.tts.util.FfmpegUtil;
import com.iflytek.cloud.speech.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class XunfeiService {

    @Value("${xunfei.appId}")
    private String appId;

    private static final Logger log = LoggerFactory.getLogger(XunfeiService.class);

    public XunfeiService() {
        SpeechUtility.createUtility(SpeechConstant.APPID + "=" + appId);
    }

    public static Boolean flag = true;
    // 合成监听器
    private final static SynthesizeToUriListener mSynListener = new SynthesizeToUriListener() {
        //progress为合成进度0~100
        public void onBufferProgress(int progress) {

        }

        //会话合成完成回调接口 URI为合成保存地址，error为错误信息，为null时表示合成会话成功
        public void onSynthesizeCompleted(String uri, SpeechError error) {
            if (error == null) {
                log.info("生成路径地址:" + uri);
            } else {
                log.info(error.toString());
            }
            flag = true;
        }

        @Override
        public void onEvent(int arg0, int arg1, int arg2, int arg3,
                            Object arg4, Object arg5) {
        }
    };

    private void convert(VoiceEntity v, String path) {
        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, v.getVoiceName());//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, v.getSpeed() + "");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, v.getVolume() + "");//设置音量，范围0~100

        // 3.开始合成
        mTts.setParameter("LIB_NAME_64", "lib_name_64");
        mTts.synthesizeToUri(v.getText(), path, mSynListener);
    }

    public Map<String, Object> tts(VoiceEntity vo) {
        String basePath = System.getProperty("java.io.tmpdir");
        String fileSeparator = System.getProperty("file.separator");
        Calendar c = Calendar.getInstance();
        String audioName = String.valueOf(c.getTimeInMillis()) + Math.round(Math.random() * 100000);
        //讯飞转PCM
        String pcmPath = basePath + fileSeparator + audioName + ".pcm";

        convert(vo, pcmPath);
        //由于生成PCM是异步的，这里while一直等待，直到生成
        File file = new File(pcmPath);
        boolean result;
        while (true) {
            if (file.exists()) {
                //PCM转MP3
                String audioPath = basePath + fileSeparator + audioName;
                result = FfmpegUtil.processAudio(pcmPath, audioPath, "mp3");
                break;
            }
        }
        Map<String, Object> r = new HashMap<>();
        r.put("success", result);
        if (result) {
            r.put("data", audioName + ".mp3");
        } else {
            r.put("message", "失败");
        }
        return r;
    }
}
