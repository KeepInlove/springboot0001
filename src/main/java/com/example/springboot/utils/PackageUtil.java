package com.example.springboot.utils;

import java.io.*;

public class PackageUtil {
    /**
     * 打包工具，鼠标右键运行后自动删除所有多余 前端依赖 和 后端缓存
     * @param args
     */
    public static void main(String[] args) {

        //基础目录
        String basePath = System.getProperty("user.dir");

        //如果是windows系统
        if (isWindows()){
            //关闭所有node程序
            executeCommands("TASKKILL /F /IM node.exe");
        }else {
            //mac系统
            //关闭所有node程序
            executeCommands("killall -9 node");
        }

        //删除.idea
        String idea=basePath+"/.idea";
        deleteDir(new File(idea));
        //删除target
        String target=basePath+"/target";
        deleteDir(new File(target));
        //删除node_modules
        String nodeModules=basePath+"/vue/node_modules";
        deleteDir(new File(nodeModules));
        //删除packageUtil
        String packageUtil=basePath+"/src/main/java/com/example/springboot/utils/PackageUtil.java";
        deleteFile(packageUtil);
    }

    /**
     * 执行系统命令
     * @param commands (必传)需要执行命令,多个String,逗号隔开
     */
    public static void executeCommands(String ...commands){
        String command="";
        for (String c : commands) {
            command+=c+" && ";
        }
        command=command.substring(0,command.length()-3);
        String[] commandsList;
        if (isWindows()){
            commandsList = new String[]{"cmd", "/c", command};
        }else{
            commandsList = new String[]{"bash", "-c", command};
        }
        try {
            Process process = Runtime.getRuntime().exec(commandsList);
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is,"GBK");
            BufferedReader br = new BufferedReader(isr);
            String content = br.readLine();
            while (content != null) {
                //打印内容
                System.out.println(content);
                content = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")>=0?true:false;
    }


    private static void deleteFile(String filePath) {
        File file = new File(filePath);
        file.delete();
        System.err.println("文件删除成功!:"+file);
    }

    public static void deleteDir(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
        System.err.println("文件删除成功!:"+file);
    }

}
