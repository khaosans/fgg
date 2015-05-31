package edu.pdx.cse.mobilehealthapp;

import android.view.View;

/**
 * Created by solus on 5/30/2015.
 */
public class MyOnClickListener implements View.OnClickListener {
    private int itemNumber;

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public MyOnClickListener(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    @Override
    public void onClick(View view) {
    }

}
