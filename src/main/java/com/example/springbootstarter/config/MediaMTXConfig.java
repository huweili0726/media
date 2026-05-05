package com.example.springbootstarter.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class MediaMTXConfig implements ApplicationRunner, DisposableBean {

    private Process mediaMTXProcess;
    private final MediaMTXProperties mediaMTXProperties;

    //构造函数，在创建对象时自动执行，用于初始化对象的状态，注入MediaMTXProperties
    public MediaMTXConfig(MediaMTXProperties mediaMTXProperties) {
        this.mediaMTXProperties = mediaMTXProperties;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 启动mediaMTX
        try {
            // 防重复启动检查
            if (mediaMTXProcess != null && mediaMTXProcess.isAlive()) {
                System.out.println("mediaMTX is already running, skipping startup");
                return;
            }

            // 使用绝对路径来确保系统能找到可执行文件
            String workingDir = mediaMTXProperties.getWorkingDir();

            // 使用File类构建跨平台的路径
            java.io.File exeFile = new java.io.File(workingDir, mediaMTXProperties.getExe());
            java.io.File ymlFile = new java.io.File(workingDir, mediaMTXProperties.getYml());

            // 检查exe文件是否存在
            if (!exeFile.exists()) {
                throw new IllegalStateException("mediaMTX executable not found: " + exeFile.getAbsolutePath());
            }

            // 检查yml文件是否存在
            if (!ymlFile.exists()) {
                throw new IllegalStateException("mediaMTX configuration file not found: " + ymlFile.getAbsolutePath());
            }

            String[] command = {exeFile.getAbsolutePath(), ymlFile.getAbsolutePath()};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new java.io.File(workingDir));
            processBuilder.inheritIO();

            System.out.println("Starting mediaMTX...");
            System.out.println("Command: " + String.join(" ", command));
            mediaMTXProcess = processBuilder.start();
            System.out.println("mediaMTX started successfully");
        } catch (Exception e) {
            System.err.println("Failed to start mediaMTX: " + e.getMessage());
            throw new RuntimeException("Failed to start mediaMTX", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        // 关闭mediaMTX
        if (mediaMTXProcess != null && mediaMTXProcess.isAlive()) {
            System.out.println("Stopping mediaMTX...");
            // 向进程发送中断信号
            mediaMTXProcess.destroy();
            // 等待进程终止，最多等待5秒
            if (!mediaMTXProcess.waitFor(5, TimeUnit.SECONDS)) {
                // 如果进程没有在5秒内终止，强制终止
                mediaMTXProcess.destroyForcibly();
            }
            System.out.println("mediaMTX stopped successfully");
        }
    }
}
