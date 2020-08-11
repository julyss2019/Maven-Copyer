package com.github.julyss2019.maven.plugins.copyer.copyer;

import com.github.julyss2019.maven.plugins.copyer.Util;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Mojo(name = "common-copy", defaultPhase = LifecyclePhase.PACKAGE)
public class CommonCopyMojo extends AbstractMojo {
    @Parameter
    private List<CommonCopyer> commonCopyers;

    @Override
    public void execute() {
        final Log log = getLog();

        commonCopyers.forEach(copyer -> {
            File sourceFile = copyer.getSource();

            if (!sourceFile.exists()) {
                throw new RuntimeException("文件不存在: " + sourceFile.getAbsolutePath());
            }

            copyer.getDests().forEach(destFile -> {
                if (destFile.isDirectory()) {
                    throw new RuntimeException("路径必须是文件: " + destFile.getAbsolutePath());
                }

                if (destFile.exists() && !copyer.isOverwrite()) {
                    throw new RuntimeException("文件未被复制: " + destFile.getAbsolutePath() + ", 因为文件已存在, 且未设置 overwrite 属性");
                }

                try {
                    Util.copyFile(sourceFile, destFile);
                } catch (IOException e) {
                    throw new RuntimeException("复制文件失败: " + sourceFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath(), e);
                }

                log.info("完成复制: " + sourceFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath());
            });
        });
    }
}
