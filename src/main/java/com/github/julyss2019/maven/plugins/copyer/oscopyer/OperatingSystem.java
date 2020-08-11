package com.github.julyss2019.maven.plugins.copyer.oscopyer;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OperatingSystem {
    @Parameter
    private String name;
    @Parameter
    private List<File> dests;
    @Parameter(defaultValue = "false")
    private boolean overwrite;

    public boolean isOverwrite() {
        return overwrite;
    }

    public String getName() {
        return name;
    }

    public List<File> getDests() {
        return new ArrayList<>(dests);
    }
}
