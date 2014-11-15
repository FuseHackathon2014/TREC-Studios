package com.trecstudios.pillbox;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by derek on 11/15/14.
 */
public class PillStore implements Serializable {
    private final String FILE_NAME = "pill_store";
    private final int LOW_DAY_THRESHOLD = 5;
    private Context context;
    private HashSet<Pill> pillSet;

    public PillStore(Context context) {
        this.context = context;
        this.pillSet = new HashSet<Pill>();
    }

    public void addPill(Pill pill) {
        pillSet.add(pill);
    }

    public void removePill(Pill pill) {
        pillSet.remove(pill);
    }

    public List<Pill> getUntakenPills() {
        List<Pill> result = new ArrayList<Pill>();
        Date currentTime = new Date();
        for (Pill pill : this.pillSet) {
            if(pill.isUntaken(currentTime)){
                result.add(pill);
            }
        }
        return result;
    }

    public void markPillAsTaken(Pill pill) {
        pill.markAsTaken();
    }

    public List<Pill> getLowcountPills() {
        List<Pill> result = new ArrayList<Pill>();
        for (Pill pill : this.pillSet) {
            if(pill.isLow(LOW_DAY_THRESHOLD)){
                result.add(pill);
            }
        }
        return result;
    }

    public void loadFromFile() {
        try {
            FileInputStream is = context.openFileInput(FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(is);
            this.pillSet = (HashSet<Pill>) ois.readObject();
        } catch (FileNotFoundException e) {
            //File not found
        } catch (IOException e) {
            //Failed to read from file
        } catch (ClassNotFoundException e) {
            //Failed to load class
        }
    }

    public void saveToFile() {
        try {
            FileOutputStream os = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(this.pillSet);
        } catch (FileNotFoundException e) {
            //File not found, but it should be created if not exist
        } catch (IOException e) {
            //Could not create ObjectOutputStream
        }
    }
}
