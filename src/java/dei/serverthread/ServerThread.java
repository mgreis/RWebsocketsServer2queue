/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dei.serverthread;

import dei.fwebsocket.FServerSession;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mgreis
 */
public class ServerThread extends Thread {

    private UsersList users = null;
    ConcurrentHashMap<Integer, FServerSession> fSessions = null;
    Random random = new Random();

    public ServerThread(UsersList users, ConcurrentHashMap<Integer, FServerSession> fSessions) {
        this.users = users;
        this.fSessions = fSessions;

    }

    @Override
    public void run() {
        System.out.println ("Starting ServerThread");
        while (true) {
            try {
                sleep(1000 + random.nextInt(10000));
            } catch (InterruptedException ex) {
                
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(!users.isEmpty()){
                FServerSession user = fSessions.get(users.getRandomUser());
                
                user.send("Hello World!");
                
            }

        }

    }

}


