package com.example.d308realproject.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursion")
public class Excursion {
   @PrimaryKey(autoGenerate = true)
    private int id;
    private String excursionName;
    private double price;
    private int vacationID;

    public Excursion(int id, String excursionName, double price, int vacationID) {
        this.id = id;
        this.excursionName = excursionName;
        this.price = price;
        this.vacationID = vacationID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }
}
