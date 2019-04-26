package com.example.springtest.service;

public interface UserSerive {
    //返回插入的主键：
    public int addOneUser(String openID,String SessionID);
    public String getLoginMD5(String openID, String SessionID);
    public int getUserByOpenIDAndSessionId(String openID,String SessionId);
    public int getUserByMD5ID(String md5ID);
}
