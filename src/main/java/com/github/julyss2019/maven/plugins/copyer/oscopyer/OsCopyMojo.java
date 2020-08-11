package com.github.julyss2019.maven.plugins.copyer.oscopyer;

import com.github.julyss2019.maven.plugins.copyer.Util;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@Mojo(name = "os-copy", defaultPhase = LifecyclePhase.PACKAGE)
public class OsCopyMojo extends AbstractMojo {
    @Parameter
    private List<OsCopyer> osCopyers;

    public void execute() {
        final Log log = getLog();

        log.info("当前系统: " + Util.OS_NAME);

        osCopyers.forEach(osCopyer -> {
            File sourceFile = osCopyer.getSource();

            if (!sourceFile.exists()) {
                throw new RuntimeException("文件不存在: " + sourceFile.getAbsolutePath());
            }

            osCopyer.getOperatingSystems()
                    .stream()
                    .filter(osConfig -> osConfig.getName().equalsIgnoreCase(Util.OS_NAME))
                    .collect(Collectors.toList())
                    .forEach(os -> {
                        os.getDests().forEach(destFile -> {
                            if (destFile.isDirectory()) {
                                throw new RuntimeException("路径必须是文件: " + destFile.getAbsolutePath());
                            }

                            if (destFile.exists() && !os.isOverwrite()) {
                                log.warn("文件未被复制: " + destFile.getAbsolutePath() + ", 因为文件已存在, 且未设置 overwrite 属性");
                                return;
                            }

                            try {
                                Util.copyFile(sourceFile, destFile);
                            } catch (IOException e) {
                                throw new RuntimeException("复制文件失败: " + sourceFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath(), e);
                            }

                            log.info("完成复制: " + sourceFile.getAbsolutePath() + " -> " + destFile.getAbsolutePath());
                        });
            });
        });
    }
}
