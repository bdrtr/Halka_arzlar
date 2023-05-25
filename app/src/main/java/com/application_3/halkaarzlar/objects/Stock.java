package com.application_3.halkaarzlar.objects;

import java.io.Serializable;

public class Stock implements Serializable {
    private String name;
    private String  pop;
    private String target_price;
    private String id;
    private String date;
    private String plan;
    private String share_rate;
    private String name_code;
    private String bazaar;


    public Stock(String name, String pop, String target_price, String id, String date, String plan, String share_rate, String name_code, String bazaar) {
        this.name = name;
        this.pop = pop;
        this.target_price = target_price;
        this.id = id;
        this.date = date;
        this.plan = plan;
        this.share_rate = share_rate;
        this.name_code = name_code;
        this.bazaar = bazaar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getCurrent_price() {
        return target_price;
    }

    public void setCurrent_price(String target_price) {
        this.target_price = target_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getShare_rate() {
        return share_rate;
    }

    public void setShare_rate(String share_rate) {
        this.share_rate = share_rate;
    }

    public String getName_code() {
        return name_code;
    }

    public void setName_code(String name_code) {
        this.name_code = name_code;
    }

    public String getBazaar() {
        return bazaar;
    }

    public void setBazaar(String bazaar) {
        this.bazaar = bazaar;
    }

    public String getPOPDouble() {
        String[] str  = pop.split(" ");
        String[] temp_text = str[0].split(",");
        return temp_text[0]+"."+temp_text[1];
    }
}
