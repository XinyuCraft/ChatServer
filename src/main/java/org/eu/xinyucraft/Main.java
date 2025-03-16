package org.eu.xinyucraft;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/*
聊天服务器:
1.登录 login:[用户名]:[密码]
2.注册 reg:[用户名]:[密码]:[邮箱]
3.世界频道 w:[信息]
*/

public class Main {
    private ServerSocket serverSocket; //服务器套接字
    public ConcurrentHashMap<String, BufferedWriter> UserWriters = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, String> UserList = new ConcurrentHashMap<>();
    public static int Port; //服务器端口

    public void handleClient(Socket clientSocket){
        String username = "";
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))
        ){
            String message; //读取客户端发送的信息
            while ((message = reader.readLine()) != null){
                String[] parts = message.split(":");
                if(parts[0].equals("login")){//登录
                    if (UserList.containsKey(parts[1]) && UserList.get(parts[1]).equals(parts[2])){//账号密码正确
                        writer.write("登录成功!\n");
                        writer.flush();
                        username = parts[1]; //读取用户名
                        System.out.println("欢迎用户: " + username + " 加入服务器");
                        UserWriters.put(username,writer);//存入用户
                    } else {//密码错误
                        writer.write("用户名或密码错误!\n");
                        writer.flush();
                    }
                } else if (parts[0].equals("reg")) {
                    regUser(parts[1], parts[2]);
                } else if(parts[0].equals("w")){
                    System.out.println("客户端[" + clientSocket + "]: " + parts[1]);
                    sendMessage("[" + username + "]: " + parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("客户端[" + clientSocket + "]异常退出");
            UserWriters.remove(username);
        }
    }

    private void sendMessage(String message){//发送信息
        UserWriters.forEach((username, writer) -> {
            try {
                writer.write(message + "\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println("发送消息给 " + username + " 失败");
            }
        });
    }

    private void regUser(String username, String password){ //添加用户
        UserList.put(username, password); //存入列表
        try (FileOutputStream fileOutputStream = new FileOutputStream("user.properties", true)) { //存入配置文件
            Properties properties = new Properties();
            properties.setProperty(username, password);
            properties.store(fileOutputStream, "新增用户");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer(){
        try {
            serverSocket = new ServerSocket(Port);
            System.out.println("服务器套接字已经成功创建");
            System.out.println("等待客户端连接...");
            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("客户端[" + clientSocket + "]已连接");
                new Thread(() -> handleClient(clientSocket)).start(); //创建线程接收信息
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static { //读取配置文件
        File chatfile = new File("chat.properties"); //服务器配置文件
        File userfile = new File("user.properties"); //玩家登录信息
        if (!chatfile.exists()){
            System.out.println("未发现配置文件\n正在创建中...");
            try {
                chatfile.createNewFile();
                FileWriter  writer = new FileWriter(chatfile, true);
                writer.append("serverPort=8888");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!userfile.exists()){
            try {
                userfile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("创建成功!");
        Properties properties = new Properties();
        try {
            //读取配置
            properties.load(new FileInputStream("chat.properties"));
            //获取端口
            Port = Integer.parseInt(properties.getProperty("serverPort"));
            System.out.println("服务器端口为: " + Port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            //读取用户配置
            properties.load(new FileInputStream("user.properties"));
            properties.forEach((key, value) ->
                    UserList.put((String) key, (String) value) //读取用户的账号密码
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        Main main = new Main();
        main.startServer();
    }
}