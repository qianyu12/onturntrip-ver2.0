package com.example.springtest.utils.helpclass;

public class Weight {
    int price;
    int comfort;
    int time;
    int transferTime;
    public Weight(){

    }

    public Weight(int price, int comfort, int time,int transferTime) {
        this.price = price;
        this.comfort = comfort;
        this.time = time;
        this.transferTime = transferTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getComfort() {
        return comfort;
    }

    public void setComfort(int comfort) {
        this.comfort = comfort;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(int transferTime) {
        this.transferTime = transferTime;
    }

    public float getPriceScale(){
        return (float)price/(price+comfort+time+transferTime);
    }

    public float getComftScale(){
        return (float)comfort/(price+comfort+time+transferTime);
    }

    public float getTimeScale(){
        return (float)time/(price+comfort+time+transferTime);
    }

    public float getTranferTimeScale(){
        return (float)transferTime/(price+comfort+time+transferTime);
    }
}
