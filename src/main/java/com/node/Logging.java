package com.node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logging {


    public static void log(String str) {
        try {
            String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

            File folder = new File("logs");
            if (!(folder.exists() || folder.mkdir())) return;

            String FILENAME = "log-" + localDate + ".txt";

            File file = new File(folder.getPath() + File.separator + FILENAME);
            if (!(file.exists() || file.createNewFile())) return;

            FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(localDateTime + "\t" + str + "\n\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static void  cleanExpiredLogs(){
        try {
            Logging.log("start clean expired logs");
            File folder = new File("logs");
            File[] files = folder.listFiles();
            if (files == null || files.length <= 0) return;
            for (File file : files) {
                long day = file.lastModified() / 1000 / 3600 / 24;
                LocalDate lastModified = LocalDate.ofEpochDay(day);
                LocalDate deathDate = LocalDate.now().minusMonths(1);
                if (lastModified.isBefore(deathDate)) {
                    Logging.log("deleting " + file.getAbsolutePath());
                    if (!file.delete()) {
                        Logging.log("fail to delete " + file.getAbsolutePath());
                    }
                }
            }
            Logging.log("end clean expired logs");
        } catch (Exception e) {
            Logging.log(e.toString());
        }

    }
}
