package edu.pdx.cse.mobilehealthapp;

import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by solus on 5/31/2015.
 */
public class MongoDB {

    public static void generateNoteOnSD(String sFileName, String sBody){
        try
        {
            File root = new File("data.txt");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    public void add(String pairID, String name1, String name2, Boolean pick1, Boolean pick2,Boolean isCorrect) throws UnknownHostException {


        MongoClient mongoClient = new MongoClient("capstonedd.cs.pdx.edu",  27017);
        DB db = mongoClient.getDB("test");

        DBCollection table = db.getCollection("FoodGuessingGameDB");

        //table.drop();

        BasicDBObject document = new BasicDBObject();
        document.put("database", "Food Guessing Game");

        BasicDBObject documentDetail = new BasicDBObject();
        documentDetail.put("FoodPair", pairID);
        document.put("detail", documentDetail);
        document.put("Food1", name1);
        document.put("Food2", name2);
        document.put("Pick1", pick1);
        document.put("Pick2", pick2);
        document.put("isCorrect",isCorrect);

        table.insert(document);


        DBCursor cursor = table.find();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static void main(String[] args) throws UnknownHostException {
       generateNoteOnSD("","sasdfasdt");
    }
}
