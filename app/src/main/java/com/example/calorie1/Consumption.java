package com.example.calorie1;

import java.util.Date;

/**
 * Create a Consumption class to store a consumption record
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class Consumption {
    private static final long serialVersionUID = 1L;
    protected ConsumptionPK consumptionPK;
    private Integer quantity;
    private Enduser enduser;
    private Food food;

    public Consumption() {
    }

    public Consumption(ConsumptionPK consumptionPK) {
        this.consumptionPK = consumptionPK;
    }

    public Consumption(int userid, Date consumptiondate, int foodid) {
        this.consumptionPK = new ConsumptionPK(userid, consumptiondate, foodid);
    }

    public ConsumptionPK getConsumptionPK() {
        return consumptionPK;
    }

    public void setConsumptionPK(ConsumptionPK consumptionPK) {
        this.consumptionPK = consumptionPK;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Enduser getEnduser() {
        return enduser;
    }

    public void setEnduser(Enduser enduser) {
        this.enduser = enduser;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
