package edu.wit.mobileapp.listview;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;


public class Current extends AppCompatActivity
{
    // UUIDs for UAT service and associated characteristics.
    // UUID for the BTLE client characteristic which is necessary for notifications.
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID RX_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    // UI elements
    private int temp;
    String temp1;
    int bpm, esignal;
    int counter=0;
    private ScrollView scroll;
    // BTLE state
    private BluetoothAdapter adapter;
    private BluetoothGatt gatt;
    private BluetoothGattCharacteristic tx;
    private BluetoothGattCharacteristic rx;
    SQLiteDatabase db;
    String username_login;
    int sex, age;
    public int hb;
    boolean user_exist=false;
    TextView current_heartbeat;

    private BluetoothGattCallback callback = new BluetoothGattCallback()
    {
        // Called whenever the device connection state changes, i.e. from disconnected to connected.
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState)
        {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothGatt.STATE_CONNECTED)
            {

                if (!gatt.discoverServices())
                {

                }
            } else if (newState == BluetoothGatt.STATE_DISCONNECTED)
            {


            } else {

            }
        }

        // Called when services have been discovered on the remote device.
        // It seems to be necessary to wait for this discovery to occur before
        // manipulating any services or characteristics.
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status)
        {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS)
            {

            }
            else
            {

            }

            // Save reference to each characteristic.
            tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
            rx = gatt.getService(UART_UUID).getCharacteristic(RX_UUID);

            Log.v("BLE", "tx:" + tx);
            Log.v("BLE", "rx:" + rx.getDescriptor(CLIENT_UUID));

            // Setup notifications on RX characteristic changes (i.e. data received).
            // First call setCharacteristicNotification to enable notification.
            if (!gatt.setCharacteristicNotification(rx, true))
            {

            }

            // Next update the RX characteristic's client descriptor to enable notifications.
            if (rx.getDescriptor(CLIENT_UUID) != null)
            {
                BluetoothGattDescriptor desc = rx.getDescriptor(CLIENT_UUID);
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                if (!gatt.writeDescriptor(desc))
                {

                }
            }
            else
            {

            }
        }

        // Called when a remote characteristic changes (like the RX characteristic).
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic)
        {
            super.onCharacteristicChanged(gatt, characteristic);
            temp1= characteristic.getStringValue(0);
            esignal = Integer.parseInt(temp1.substring(0,temp1.indexOf(",")));
//            temp = Integer.parseInt((characteristic.getStringValue(0)));
            bpm = Integer.parseInt(temp1.substring(temp1.indexOf(",")+1));

        }
    };

    // BTLE device scanning callback.
    private BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback()
    {
        // Called when a device is found.
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes)
        {
            //writeLine("Found device: " + bluetoothDevice.getAddress());
            // Check if the device has the UART service.
            if (parseUUIDs(bytes).contains(UART_UUID)) {
                // Found a device, stop the scan.
                adapter.stopLeScan(scanCallback);
                //writeLine("Found UART service!");
                // Connect to the device.
                // Control flow will now go to the callback functions when BTLE events occur.
                gatt = bluetoothDevice.connectGatt(getApplicationContext(), false, callback);
            }
        }
    };
    // Filtering by custom UUID is broken in Android 4.3 and 4.4, see:
//   http://stackoverflow.com/questions/18019161/startlescan-with-128-bit-uuids-doesnt-work-on-native-android-ble-implemen
// This is a workaround function from the SO thread to manually parse advertisement data.
    private List<UUID> parseUUIDs(final byte[] advertisedData)
    {
        List<UUID> uuids = new ArrayList<UUID>();

        int offset = 0;
        while (offset < (advertisedData.length - 2)) {
            int len = advertisedData[offset++];
            if (len == 0)
                break;

            int type = advertisedData[offset++];
            switch (type) {
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                    while (len > 1) {
                        int uuid16 = advertisedData[offset++];
                        uuid16 += (advertisedData[offset++] << 8);
                        len -= 2;
                        uuids.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", uuid16)));
                    }
                    break;
                case 0x06:// Partial list of 128-bit UUIDs
                case 0x07:// Complete list of 128-bit UUIDs
                    // Loop through the advertised 128-bit UUID's.
                    while (len >= 16) {
                        try {
                            // Wrap the advertised bits and order them.
                            ByteBuffer buffer = ByteBuffer.wrap(advertisedData, offset++, 16).order(ByteOrder.LITTLE_ENDIAN);
                            long mostSignificantBit = buffer.getLong();
                            long leastSignificantBit = buffer.getLong();
                            uuids.add(new UUID(leastSignificantBit,
                                    mostSignificantBit));
                        } catch (IndexOutOfBoundsException e) {
                            // Defensive programming.
                            //Log.e(LOG_TAG, e.toString());
                            continue;
                        } finally {
                            // Move the offset to read the next uuid.
                            offset += 15;
                            len -= 16;
                        }
                    }
                    break;
                default:
                    offset += (len - 1);
                    break;
            }
        }
        return uuids;
    }
    private WebView webview;







    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.current);
        Backbtn();
        Button Evaluation_btn= (Button)findViewById(R.id.evaluationBtn);

        //Open the database
        String path= "/data/data/"+getPackageName()+"/user_store.db";
        db= SQLiteDatabase.openOrCreateDatabase(path,null);
        String sql= "CREATE TABLE IF NOT EXISTS username(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,sex INTEGER, age INTEGER, date TEXT, averagehb INTEGER );";
        db.execSQL(sql);

        Bundle b = this.getIntent().getExtras();
        username_login= b.getString("username");
        //String username_register=b.getString("name");
        TextView name;
        name = (TextView)findViewById(R.id.username_value);
        name.setText(username_login);
        current_heartbeat= (TextView)findViewById(R.id.bpm_value);


        try{
            sex=b.getInt("sex");
            age=b.getInt("gender");
        }catch (Exception exception)
        {
            user_exist=true;
        }

        //////////////////////////////Belove is the Bluetooth part
        webview = (WebView) findViewById(R.id.bpm_graph);
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("file:///android_asset/chart.html")) {
                    new Timer().scheduleAtFixedRate(new TimerTask()
                    {
                        public void run() {
                            runOnUiThread(new Runnable()
                            {
                                public void run()
                                {

                                       webview.loadUrl("javascript:setData(" + esignal * 10000 + ")");
                                    Log.v("myApp", "esignal: "+esignal);
                                    if(bpm>0&&bpm<200) {
                                        Log.v("myApp", "bpm: " + bpm);
                                        hb = bpm;

                                        current_heartbeat.setText(""+bpm);
                                    }


                                }
                            });
                        }
                    }, 0, 20);
                }
            }
        });

// Enable JavaScript
        webview.getSettings().setJavaScriptEnabled(true);




// Zoom out the page to fit the display
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.loadUrl("file:///android_asset/chart.html");
        adapter = BluetoothAdapter.getDefaultAdapter();Evaluation_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(!user_exist) {
                String[] column_select2 = {"sex", "age"};
                String where1 = "name LIKE '" + username_login + "'";
                Cursor cursor2 = db.query("username",column_select2,where1,null,null,null,null);
                if(cursor2.moveToNext())
                {
                    sex = cursor2.getInt(cursor2.getColumnIndex("sex"));
                    age = cursor2.getInt(cursor2.getColumnIndex("age"));
                }
            }
            EvaluationCalculate cal = new EvaluationCalculate(sex,age,hb);

            Toast.makeText(Current.this, cal.calculate(), Toast.LENGTH_SHORT).show();

        }
    });

    }
    protected void onResume()
    {
        super.onResume();
        // Scan for all BTLE devices.
        // The first one with the UART service will be chosen--see the code in the scanCallback.
//    writeLine("Scanning for devices...");
        adapter.startLeScan(scanCallback);
    }

    // OnStop, called right before the activity loses foreground focus.  Close the BTLE connection.
    @Override
    protected void onStop()
    {
        super.onStop();
        if (gatt != null) {
            // For better reliability be careful to disconnect and close the connection.
            gatt.disconnect();
            gatt.close();
            gatt = null;
            tx = null;
            rx = null;
        }
    }

    private void Backbtn() //function for button back,go back to menu1
    {

            Button backbtn = (Button) findViewById(R.id.BacktoMenuBtn);
            backbtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intents = new Intent();
                    intents.setClass(Current.this,Menu1.class);
//                    //Get the Current Date for the Database insertion
                    Calendar calendar= Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH)+1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    String date;
                    String year_str=""+year, month_str=null, day_str=null;
                    Log.v("Year:",year_str);
                    if(month < 10){
                        month_str = "0" + month;
                        Log.v("Month:",month_str);
                    }
                    if(day < 10){
                        day_str  = "0" + day ;
                        Log.v("Day:",day_str);
                    }
                    date = month_str+"/"+day_str+"/"+year_str;
                    Log.v("MyApp",date);
//                    hb = bpm/counter;
                    Log.v("MyApp","Heart Beat:"+hb);
                    //check if the data with date is already exist
                    String[] column_select={"sex","age","averagehb"};
                    String where = "date LIKE '"+date+"' AND name LIKE '"+username_login+"'";
                    String where1 = "name LIKE '"+username_login+"'";
                    Cursor cursor=db.query("username",column_select,where,null,null,null,null);
                    Cursor cursor1=db.query("username",column_select,where1,null,null,null,null);

                    if(cursor.moveToNext())
                    {
                        //Date exist, Alert the data
                        ContentValues values = new ContentValues();
                        values.put("name",username_login);
                        values.put("sex",sex);
                        values.put("age",age);
                        values.put("averagehb",hb);
                        String[] filt_arg={date};
                        db.update("username",values,"date =? ",filt_arg);


                    }
                    else
                    {
                        //Date doesn't exist, Insert Data
                        if(user_exist) {
                            ContentValues values = new ContentValues();
                            values.put("name", username_login);
                            sex = cursor1.getInt(cursor1.getColumnIndex("sex"));
                            values.put("sex",sex);
                            age = cursor1.getInt(cursor1.getColumnIndex("age"));
                            values.put("age",age);
                            values.put("date",date);
                            //assume the data recieved
                            values.put("averagehb",hb);
                            db.insert("username",null,values);
                        }
                        else
                        {
                            ContentValues values = new ContentValues();
                            values.put("name", username_login);
                            values.put("sex",sex);
                            values.put("age",age);
                            values.put("date",date);
                            //assume the data recieved
                            values.put("averagehb",hb);
                            db.insert("username",null,values);
                        }
                    }
                    Bundle b = getIntent().getExtras();
                    intents.putExtras(b);
                    startActivity(intents);
                }
            });
    }
}

