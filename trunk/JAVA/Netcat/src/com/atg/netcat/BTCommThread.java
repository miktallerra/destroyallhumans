/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.atg.netcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

class BTCommThread extends Thread {
    private BluetoothSocket socket;
    private InputStream istream;
    private OutputStream ostream;
    private Handler handler;
    private ProgressDialog dialog;
    private BluetoothAdapter adapter;

    public BTCommThread(BluetoothAdapter adapter, ProgressDialog dialog, Handler handler) {
        this.handler = handler;
        this.dialog = dialog;
        this.adapter = adapter;
    }

    public void run() {
        if (adapter == null)
            return;

        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        BluetoothDevice device = null;
        for (BluetoothDevice curDevice : devices) {
            if (curDevice.getName().matches(".*BlueRobot.*")) {
                device = curDevice;
                break;
            }
        }
        if (device == null)
            device = adapter.getRemoteDevice("00:06:66:03:A9:A2");

        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            socket.connect();
        } catch (IOException e) {
            socket = null;
        }
        if (socket == null) return;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        istream = tmpIn;
        ostream = tmpOut;
        
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();

        StringBuffer sb = new StringBuffer();
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        String message;
        int idx;
        HashMap<String, String> hm;
        String[] chunks;
        
        while (true) {
            try {
              String test = "test\n";
              ostream.write(test.getBytes());
              try
              {
                sleep(1100);
              }
              catch (InterruptedException e)
              {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
                // Read from the InputStream
              /*
                bytes = istream.read(buffer);
                sb.append(new String(buffer, 0, bytes));
                while ((idx = sb.indexOf("\r\n\r\n")) > -1) {
                    message = sb.substring(0, idx);
                    sb.replace(0, idx+4, "");
                    hm = new HashMap<String, String>();
                    for (String line : message.split("\n")) {
                        chunks = line.trim().split("=", 2);
                        if (chunks.length != 2) continue;
                        hm.put(chunks[0], chunks[1]);
                    }
                    handler.obtainMessage(0x2a, hm).sendToTarget();
                    */
                //}
            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main Activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            ostream.write(bytes);
        } catch (IOException e) {
            Log.e("CommThread.write", "exception during write", e);
        }
    }

    /* Call this from the main Activity to shutdown the connection */
    public void cancel() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException e) { }
    }
}
