package edu.pdx.cse.mobilehealthapp;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private LinkedList<FoodItem> foodlist = new LinkedList<>();
    private LinkedList<Pair<FoodItem, FoodItem>> pairOfFoodlist = new LinkedList<>();
    private int score;
    private int pairNumber = 0;
    public final static String foodID = "01009";
    public final static String apiKEY = "QqkqLPSUUjn5jSZvSyEdAkkhNwgLrbMYEbI249we";
    public final static String apiURL = "http://api.nal.usda.gov/usda/ndb/reports/?ndbno=" + foodID + "&type=b&format=xml&api_key=" + apiKEY;

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
                if (i != j) {
                    pairOfFoodlist.add(new Pair<>(foodlist.get(i), foodlist.get(j)));
                }
            }
        }
        return pairOfFoodlist;
    }

    private Pair<FoodItem, FoodItem> getPair(int n) {
        return pairOfFoodlist.get(n);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void setUpButton() {

        InputStream in = getResources().openRawResource(R.raw.fooditems);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        try {
            line = reader.readLine();
            while (line != null) {

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    private class api extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            InputStream input = null;
            FoodItem result = null;

            //HTTP Get
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                input = new BufferedInputStream(connection.getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Now we need to parse the XML
            XmlPullParserFactory pullParserFactory;
            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = pullParserFactory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(input, null);
                result = parseXML(parser);
                System.out.println(result);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Hi";
        }

        /**
         * Uses a parser to extract the food name and calories from the
         *
         * @param parser
         * @return
         * @throws XmlPullParserException
         * @throws IOException
         */
        private FoodItem parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            String name = null;
            int cal = -1;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    //Gets the name of the food
                    if (parser.getName().equals("food")) {
                        if (parser.getAttributeName(1).equals("name")) {
                            System.out.println("name=" + parser.getAttributeValue(1));
                            name = parser.getAttributeValue(1);
                        }
                    }
                    //Gets the calories
                    if (parser.getName().equals("nutrient")) {
                        if (parser.getAttributeValue(2).equals("kcal")) {
                            System.out.println("kcal=" + parser.getAttributeValue(3));
                            cal = Integer.parseInt(parser.getAttributeValue(3));
                        }
                    }

                }

                eventType = parser.next();
            }
            return new FoodItem(name, cal);
        }
    }

    /*
       Calls the API service from the view
     */
    public void call_api(View view) {
        new api().execute(apiURL);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void buildList(View view) {
        File file = new File("src/foodconfig.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




















