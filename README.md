# Media Stream Project

一个本地视频流媒体传输解决方案，支持将视频文件通过 RTSP 协议发布，并通过 WebRTC 在浏览器中播放。

## 项目结构

```
tools/
├── createRTSP/          # EasyRTSPServer - 将视频文件转 RTSP 流
│   ├── EasyRTSPServer_Demo.exe   # RTSP 流服务器
│   ├── *.dll                      # 依赖库
│   ├── easy.mp4                   # 示例视频
│   └── drone.mp4 / drone1.mp4    # 测试视频
└── mediaMTX/           # MediaMTX - RTSP/WebRTC 流媒体网关
    ├── mediamtx.exe              # 流媒体服务器
    ├── mediamtx.yml              # 配置文件
    └── logs/                     # 日志目录
```

## 快速开始

### 方式一：使用 EasyRTSPServer（VLC 播放）

1. 双击运行 `tools/createRTSP/EasyRTSPServer_Demo.exe`（确保 554 端口未被占用）
2. 将视频文件（如 `easy.mp4`）放到 exe 同目录下
3. 使用 VLC 等播放器访问：`rtsp://127.0.0.1:554/easy.mp4`

### 方式二：使用 MediaMTX（浏览器播放）

1. 运行 `tools/mediaMTX/mediamtx.exe`
2. 浏览器访问：`http://127.0.0.1:9997` 查看 API 和在线播放器

## 配置说明

### MediaMTX 路径配置

在 `mediamtx.yml` 的 `paths` 段配置流来源：

```yaml
paths:
  live:
    source: rtsp://127.0.0.1:554/drone1.mp4  # 指向 EasyRTSPServer 的流
    sourceOnDemand: yes
```

## 常用地址

| 服务 | 地址 |
|------|------|
| EasyRTSPServer 流 | rtsp://127.0.0.1:554/视频名.mp4 |
| WebRTC 播放器 | 通过 http://127.0.0.1:8889/live 界面访问 |