package com.example.springbootstarter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "media-mtx")
public class MediaMTXProperties {

    private String workingDir;
    private String exe;
    private String yml;

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = workingDir;
    }

    public String getExe() {
        return exe;
    }

    public void setExe(String exe) {
        this.exe = exe;
    }

    public String getYml() {
        return yml;
    }

    public void setYml(String yml) {
        this.yml = yml;
    }
}
