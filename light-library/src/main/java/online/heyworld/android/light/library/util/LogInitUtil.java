package online.heyworld.android.light.library.util;

import android.content.Context;

import com.google.common.collect.Iterators;
import com.google.common.io.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;

public class LogInitUtil {
    public static void initLog(Context context){
        Logger logger = LoggerFactory.getLogger("root");
        if(logger instanceof ch.qos.logback.classic.Logger){

            for(Appender<ILoggingEvent> appender : Collections.list(Iterators.asEnumeration(((ch.qos.logback.classic.Logger) logger).iteratorForAppenders()))){
                if(appender instanceof FileAppender){
                    FileAppender fileAppender = (FileAppender) appender;
                    String oldFile = fileAppender.getFile();
                    String fileName = new File(oldFile).getName();
                    File newFile =FileUtil.file(context.getExternalFilesDir(null).getAbsolutePath(),"logs",fileName);
                    try {
                        Files.createParentDirs(newFile);
                        fileAppender.setFile(newFile.getAbsolutePath());
                        if(fileAppender.getOutputStream()!=null){
                            fileAppender.stop();
                            fileAppender.start();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
