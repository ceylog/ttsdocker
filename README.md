# ttsdocker

*   构建基于ffmpeg的java基础镜像
*   通过docker运行讯飞语音转换
*   通过ffmpeg将pcm转换为mp3

#使用方法
1.   打包程序

    mvn package -DskipTests

2.   <del>构建镜像</del>(已集成dockerfile的maven插件，打包后会自动构建镜像,该步骤可省去)

    docker build -t wglgg/ttsweb .

3.运行

    docker run --rm -p 8888:8080 wglgg/ttsweb
        
访问http://localhost:8888/index.html即可试试效果啦:smirk:

