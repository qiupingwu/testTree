package com.xinlukou.testtree;

import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.xinlukou.mmcommon.Stopwatch;

import org.apache.http.util.EncodingUtils;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        runTest();
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


    public void runTest() {
        Stopwatch sw = new Stopwatch();
        sw.reset();

        try {
            InputStream is = getAssets().open("data/sh/weekday.csv");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            String str = EncodingUtils.getString(bytes, "utf-8");
            int[][] result = parseIntCSV(str, 109571, 2);
            sw.out(new Integer(result[109570][1]).toString());
        } catch (Exception ex) {
        }

        sw.out("runTest");
    }

    public static int[][] parseIntCSV(String str, int row, int col) {
        int[][] result = new int[row][col];
        final char cr = '\r';
        final char cn = '\n';
        final char cc = ',';
        int len = str.length();
        int index = 0;
        int value = 0;
        int rowIndex = 0;
        int colIndex = 0;
        while(index < len) {
            char c = str.charAt(index);
            switch (c) {
                case cr:
                    break;
                case cn:
                    result[rowIndex][colIndex] = value;
                    value = 0;
                    colIndex = 0;
                    rowIndex++;
                    break;
                case cc:
                    result[rowIndex][colIndex] = value;
                    value = 0;
                    colIndex++;
                    break;
                default:
                    value = value * 10 + (c - '0');
                    break;
            }
            index++;
        }
        return result;
    }
}
