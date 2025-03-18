package com.efs.efs;

public class ContextHolder {

    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static void setShard(String shard){
        CONTEXT.set(shard);
    }

    public static String getShard(){
        return CONTEXT.get();
    }

    public static void clear(){
        CONTEXT.remove();
    }
}
