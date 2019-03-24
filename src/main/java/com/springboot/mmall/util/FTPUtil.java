package com.springboot.mmall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FTPUtil {

    private static  final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    //根据key拿到value值
    private static String ftpIp = PropertiesUtil.getValue("ftp.servser.ip");
    private static String ftpUser = PropertiesUtil.getValue("ftp.user");
    private static String ftpPass = PropertiesUtil.getValue("ftp.pass");

    //构造器
    public FTPUtil(String ip,int port,String user,String pwd){
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pwd = pwd;
    }
    //public开放方法
    public static boolean uploadFile(List<File> fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始连接ftp服务器");
        //remotePath是"img"，也就是传到ftp文件夹下面的img这个文件夹下
        boolean result = ftpUtil.uploadFile("c:/img",fileList);
        logger.info("ftp服务器,结束上传,上传结果:{}", result);
        return result;
    }
    //上传的具体逻辑
    //remotePath：远程路径，ftp服务器上的相对路径，上传到ftp服务器上，ftp服务器是一个文件夹，如果需要上传到这个文件夹下的一个文件夹的话，就需要用到remotePath
    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接FTP服务器
        if(connectServer(this.ip,this.port,this.user,this.pwd)){
            try {
                //更改工作目录，将remotePath传入，如果是null，则不需要切换
                ftpClient.changeWorkingDirectory(remotePath);
                //设置缓冲区
                ftpClient.setBufferSize(1024);
                //设置encoding编码
                ftpClient.setControlEncoding("UTF-8");
                //设置文件类型为二进制类型，防止乱码
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //打开本地的ftp的被动模式
                ftpClient.enterLocalPassiveMode();
                for(File fileItem : fileList){
                    fis = new FileInputStream(fileItem);
                    //将文件存入ftpClient
                    ftpClient.storeFile(fileItem.getName(),fis);
                }

            } catch (IOException e) {
                logger.error("上传文件异常",e);
                uploaded = false;
                e.printStackTrace();
            } finally {
                //关闭ftpClient
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }
    //连接ftp服务器
    private boolean connectServer(String ip,int port,String user,String pwd){

        boolean isSuccess = false;
        ftpClient = new FTPClient();
        try {
            //ftp服务器的ip
            ftpClient.connect(ip);
            //验证ftp服务器用户验证是否通过
            isSuccess = ftpClient.login(user,pwd);
        } catch (IOException e) {
            logger.error("连接FTP服务器异常",e);
        }
        return isSuccess;
    }

    private String ip;
    private int port;
    private String user;
    private String pwd;
    //使用FTPClient上传下载
    private FTPClient ftpClient;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}