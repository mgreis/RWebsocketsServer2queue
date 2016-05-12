/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dei.serverthread;

import dei.fwebsocket.FServerSession;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Mário
 */
public class Dispatcher extends Thread{
    private ConcurrentHashMap<Integer, FServerSession> fSessions;
    private UsersList usersList;
    
    public Dispatcher (){
        
    }
    
    public Dispatcher(ConcurrentHashMap<Integer, FServerSession> fSessions, UsersList usersList){
        this.fSessions = fSessions;
        this.usersList = usersList; 
        
    }
    
    @Override
    public void run() {
        if (usersList!=null && !usersList.isEmpty()){
            for(Integer user:usersList.getUsers()){
                fSessions.get(user).sendFirstOnBuffer();
            }
            
            
            
        }
    
    }
    
}
