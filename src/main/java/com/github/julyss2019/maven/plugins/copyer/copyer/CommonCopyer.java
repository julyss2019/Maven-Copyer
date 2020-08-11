package com.github.julyss2019.maven.plugins.copyer.copyer;

import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommonCopyer {
    @Parameter
    private File source;
    @Parameter
    private List<File> dests;
    @Parameter(defaultValue = "false")
    private boolean overwrite;

    public boolean isOverwrite() {
        return overwrite;
    }

    public File getSource() {
        return source;
    }

    public List<File> getDests() {
        return new ArrayList<>(dests);
    }
}
