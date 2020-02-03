package com.xiayu.config;

import java.util.Locale;

public class RequestThreadLocalHolder {

    private static ThreadLocal<String> databaseIndex = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "databaseIndexDefaultValue";
        }
    };

    private static ThreadLocal<Integer> tableIndex = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    private static ThreadLocal<Locale> local = new ThreadLocal<Locale>(){
        @Override
        protected Locale initialValue() {
            return Locale.CHINA;
        }
    };

    public static void setDatabaseIndex(String databaseIndex){
        RequestThreadLocalHolder.databaseIndex.set(databaseIndex);
    }

    public static String getDatabasIndex(){
        return RequestThreadLocalHolder.databaseIndex.get();
    }

    public static void removeDatabasIndex(){
        RequestThreadLocalHolder.databaseIndex.remove();
    }

    public static void setTableIndex(Integer tableIndex){
        RequestThreadLocalHolder.tableIndex.set(tableIndex);
    }

    public static Integer getTableIndex(){
        return RequestThreadLocalHolder.tableIndex.get();
    }

    public static void removeTableIndex(){
        RequestThreadLocalHolder.tableIndex.remove();
    }

    public static void setLocal(Locale local){
        RequestThreadLocalHolder.local.set(local);
    }

    public static Locale getLocale(){
        return RequestThreadLocalHolder.local.get();
    }

    public static void removeLocale(){
        RequestThreadLocalHolder.local.remove();
    }
}
