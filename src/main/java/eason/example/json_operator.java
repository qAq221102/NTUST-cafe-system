package eason.example;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class json_operator {
    public static void writejson(School school) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 使用 writerWithDefaultPrettyPrinter 方法生成美化的 JSON 文件
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(school.getName() + ".json"), school);
            System.out.println("json write success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static School readjson(String schoolname) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            School school = mapper.readValue(new File(schoolname + ".json"), School.class);
            return school;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
