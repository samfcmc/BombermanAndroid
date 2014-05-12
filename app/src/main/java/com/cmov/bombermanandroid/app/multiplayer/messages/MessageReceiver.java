package com.cmov.bombermanandroid.app.multiplayer.messages;

import org.json.JSONObject;

import java.io.ObjectOutputStream;

import pt.utl.ist.cmov.wifidirect.sockets.SimWifiP2pSocket;

/**
 * This class represents the behavior associated with each
 * received message
 */
public abstract class MessageReceiver {
    public abstract void afterReceive(JSONObject json, ObjectOutputStream outputStream);
}
