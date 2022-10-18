package com.domain.api.utils;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pei hao on 2021/9/1.
 */
public class GlobalSettings {

    private static final Properties prpo = getProperties();

    private static Properties getProperties(){
        Properties p = new Properties();
        try {
                FileInputStream file = new FileInputStream(".\\src\\main\\resources\\env.properties");
                InputStreamReader reader = new InputStreamReader(file, "UTF-8");
                p.load(reader);
                file.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        return p;
    }
    public static String getProperty(String Property){
        Pattern pattern = Pattern.compile("(^\\$\\{)(.+?)(\\}$)");
        String value = prpo.getProperty(Property);
        if (value != null) {
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                return getProperty(matcher.group(2));
            }
        }
        return value;
    }
}
