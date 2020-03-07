package com.xiayu.demo;

public interface WorkDayInterface {
    void toWork();

    void eatFood();

    void morningWork();

    void rest();

    void afternoonWork();

    void goHome();
}

abstract class WorkDay implements WorkDayInterface{
    public void toWork(){
        System.out.println("坐地铁");
    }

    public void eatFood(){
        System.out.println("喝牛奶");
    }

    public abstract void morningWork();

    public void rest(){
        System.out.println("午睡");
    }

    public abstract void afternoonWork();

    public void goHome(){
        System.out.println("坐地铁");
    }
}

class Monday extends WorkDay{

    @Override
    public void morningWork() {
        System.out.println("调试代码");
    }

    @Override
    public void afternoonWork() {
        System.out.println("调试代码");
    }
}

class Tuesday extends WorkDay{

    @Override
    public void morningWork() {
        System.out.println("编码xx模块");
    }

    @Override
    public void afternoonWork() {
        System.out.println("编码xx模块");
    }
}
