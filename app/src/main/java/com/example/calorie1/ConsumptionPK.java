package com.example.calorie1;

import java.util.Date;

/**
 * Create a consumption primary key to store user and food information
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class ConsumptionPK {
    private int userid;
    private Date consumptiondate;
    private int foodid;
    public ConsumptionPK() {
    }

    public ConsumptionPK(int userid, Date consumptiondate, int foodid) {
        this.userid = userid;
        this.consumptiondate = consumptiondate;
        this.foodid = foodid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getConsumptiondate() {
        return consumptiondate;
    }

    public void setConsumptiondate(Date consumptiondate) {
        this.consumptiondate = consumptiondate;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }
}
