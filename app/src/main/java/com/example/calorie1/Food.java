package com.example.calorie1;

import java.math.BigDecimal;

/**
 * Create a Food class to store food information
 *
 * @author Rongzhi Wang
 * @version 1.1
 */
public class Food {
    private Integer foodid;
    private String foodname;
    private String category;
    private BigDecimal calorieamount;
    private String servingunit;
    private BigDecimal servingamount;
    private Integer fat;

    public Food() { }

    public Food(Integer foodid) {
        this.foodid = foodid;
    }

    public Integer getFoodid() {
        return foodid;
    }

    public void setFoodid(Integer foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getCalorieamount() {
        return calorieamount;
    }

    public void setCalorieamount(BigDecimal calorieamount) {
        this.calorieamount = calorieamount;
    }

    public String getServingunit() {
        return servingunit;
    }

    public void setServingunit(String servingunit) {
        this.servingunit = servingunit;
    }

    public BigDecimal getServingamount() {
        return servingamount;
    }

    public void setServingamount(BigDecimal servingamount) {
        this.servingamount = servingamount;
    }

    public Integer getFat() {
        return fat;
    }

    public void setFat(Integer fat) {
        this.fat = fat;
    }
}
