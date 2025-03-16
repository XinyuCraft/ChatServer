package org.eu.xinyucraft;

//用户管理器类

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserManager {
    private String UserFilePath; //用户文件目录
    private File file; //用户文件
    private FileWriter fileWriter;
    private FileReader fileReader;

    UserManager(String UserFilePath){ //构造函数
        this.UserFilePath = UserFilePath;
        try {
            this.file = new File(UserFilePath);
            fileWriter = new FileWriter(file, true);
            fileReader = new FileReader(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addUser(String UserName, String PassWord, String EmailAddress){ //创建用户
        try {
            fileWriter.append(UserName + ':' + PassWord + ':' + EmailAddress + ";\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delUser(String UserName){
        String file = "";
        try {
            fileReader.read(file.toCharArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
