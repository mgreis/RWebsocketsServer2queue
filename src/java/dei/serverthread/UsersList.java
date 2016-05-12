/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dei.serverthread;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.websocket.Session;

/**
 *
 * @author mgreis
 */
public class UsersList {
    private CopyOnWriteArrayList <Integer> users = 
            new CopyOnWriteArrayList <Integer>();
    
    
    public void addUser (Integer user){
        System.out.println("User: " + user);
        getUsers().add(user);
        for (Integer u:users){
            System.out.println("I am user "+u);
        }

       
    }
    
    public Integer getRandomUser(){
       
        return getUsers().get (new Random().nextInt(getUsers().size()));
        
    }
    
    public void removeUser (Integer user){
        getUsers().remove(user);
    }
    
    boolean isEmpty(){
        return getUsers().isEmpty();
    }

    /**
     * @return the users
     */
    public CopyOnWriteArrayList <Integer> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(CopyOnWriteArrayList <Integer> users) {
        this.users = users;
    }
    
}
