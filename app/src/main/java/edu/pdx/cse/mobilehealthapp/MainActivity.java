package edu.pdx.cse.mobilehealthapp;

import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private LinkedList<FoodItem> foodlist = new LinkedList<>();
    private LinkedList<Pair<FoodItem, FoodItem>> pairOfFoodlist = new LinkedList<>();
    private int score;
    private int pairNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpButton();
    }

    //example data
    private void injectData() {
        int max = 100;
        int min = 1;
        Random rn = new Random();

        for (int i = 0; i <= 10; ++i) {
            int randomNumber = rn.nextInt(max - min + 1) + min;
            foodlist.add(new FoodItem(randomNumber + "", randomNumber));
        }
    }

    private LinkedList<Pair<FoodItem, FoodItem>> createComboList(LinkedList<FoodItem> foodlist) {
        for (int i = 0; i < foodlist.size(); ++i) {
            for (int j = 0; j < foodlist.size(); j++) {
                pairOfFoodlist.add(new Pair<>(foodlist.get(i), foodlist.get(j)));
            }
        }
        return pairOfFoodlist;
    }

    private Pair<FoodItem, FoodItem> getPair(int n) {
        return pairOfFoodlist.get(n);
    }

    private void setUpButton() {
        injectData();
        createComboList(foodlist);


        Button button = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.textView);

        textView.setText(pairOfFoodlist.get(pairNumber).first.getName());

        TextView textView1 = (TextView) findViewById(R.id.textView2);
        textView1.setText(pairOfFoodlist.get(pairNumber).second.getName());

        button.setOnClickListener(new MyOnClickListener(pairNumber) {
            @Override
            public void onClick(View view) {
                Log.i("DemoButtonApp111", "You clicked1");
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(pairOfFoodlist.get(pairNumber).first.getName());
                TextView textView1 = (TextView) findViewById(R.id.textView2);
                textView1.setText(pairOfFoodlist.get(pairNumber).second.getName());
                pairNumber += 10;

            }
        });

        Button button1 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("DemoButtonApp222", "You clicked2");
                TextView textView = (TextView) findViewById(R.id.textView);
                textView.setText(pairOfFoodlist.get(pairNumber).first.getName());
                TextView textView1 = (TextView) findViewById(R.id.textView2);
                textView1.setText(pairOfFoodlist.get(pairNumber).second.getName());
                pairNumber += 10;
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
