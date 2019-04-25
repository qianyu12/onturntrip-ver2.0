package com.example.springtest.utils.helpclass;

import java.util.LinkedList;
import java.util.List;

/**
 * 这个类：范围，表示哪几个方位属于一个范围，在一个范围内的城市可以通过这个范围内的中转站中转
 * E,N,SW,NE,M,S,NW
 */
public class Scope {
    private String startScope;
    private String middleScope;
    private String endScope;

    public String getStartScope() {
        return startScope;
    }

    public void setStartScope(String startScope) {
        this.startScope = startScope;
    }

    public String getMiddleScope() {
        return middleScope;
    }

    public void setMiddleScope(String middleScope) {
        this.middleScope = middleScope;
    }

    public String getEndScope() {
        return endScope;
    }

    public void setEndScope(String endScope) {
        this.endScope = endScope;
    }
    public Scope(String startScope,String middleScope,String endScope){
        this.startScope = startScope;
        this.middleScope = middleScope;
        this.endScope = endScope;
    }

    private static List<Scope> scopes = new LinkedList<>();

    static {
        //N方位
        scopes.add(new Scope("N","","NE"));
        scopes.add(new Scope("N","M","E"));
        scopes.add(new Scope("N","NW","SW"));
        scopes.add(new Scope("N","","NW"));
        scopes.add(new Scope("N","","M"));
        scopes.add(new Scope("N","M","S"));
        //NE方位：
        scopes.add(new Scope("NE","N","E"));
        scopes.add(new Scope("NE","N|NW","SW"));
        scopes.add(new Scope("NE","N","NW"));
        scopes.add(new Scope("NE","N","M"));
        scopes.add(new Scope("NE","N|M","S"));
        //E方位
        scopes.add(new Scope("E","M|NW","SW"));
        scopes.add(new Scope("E","N","NW"));
        scopes.add(new Scope("E","","M"));
        scopes.add(new Scope("E","M","S"));
        //SW方位
        scopes.add(new Scope("SW","","NW"));
        scopes.add(new Scope("SW","","M"));
        scopes.add(new Scope("SW","","S"));
        //NW方位
        scopes.add(new Scope("NW","","M"));
        scopes.add(new Scope("NW","M|SW","S"));
        //S方位
        scopes.add(new Scope("S","","M"));
        //M方位
    }

    public static String returnMiddleOrientation(String startScope, String endScope){
        String result = "";
        for(Scope s:scopes){
            if((s.startScope.equals(startScope)&&s.endScope.equals(endScope))||(s.startScope.equals(endScope)&&s.endScope.equals(startScope))){
                result = s.middleScope;
                break;
            }
        }
        return result;
    }
    public static List<Scope> returnScopes(){
        return scopes;
    }
    @Override
    public String toString(){
        return startScope+"-"+middleScope+"-"+endScope;
    }
}
