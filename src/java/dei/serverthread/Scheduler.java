package dei.serverthread;

import dei.fwebsocket.FServerSession;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public final class Scheduler extends TimerTask {

    private final static long fONCE_PER_SECOND = 1000;
    private final static long fONCE_PER_MINUTE = 1000 * 60;
    private final static long fONCE_PER_HOUR = 1000 * 60 * 60;
    private final static long fONCE_PER_DAY = 1000 * 60 * 60 * 24;
    private final ConcurrentHashMap<Integer, FServerSession> fSessions;
    private final UsersList usersList ; 
    /**
     * Construct and use a TimerTask and Timer.
     *
     * @param args
     */
    
    public Scheduler(ConcurrentHashMap<Integer, FServerSession> fSessions , UsersList usersList){
        System.out.println("Starting Scheduler!");
        this.fSessions = fSessions;
        this.usersList = usersList;
        
        
    //perform the task at a given time interval starting now
        Timer timer = new Timer();
        Calendar now = new GregorianCalendar();
        timer.scheduleAtFixedRate(this, now.getTime(), fONCE_PER_MINUTE);
    }

    /**
     * Implements TimerTask's abstract run method.
     */
    @Override
    public void run() {
        //DO STUFF
        System.out.println("Scheduling...");
        if(usersList!=null)System.out.println("NOT EMPTY!");
        else System.out.println ("EMPTY!");
        Dispatcher dispatcher = new Dispatcher(fSessions,usersList);
        dispatcher.start();
    }

}