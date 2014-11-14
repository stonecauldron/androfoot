package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;

/**
 * Manage the events of the physic world. We need that because we can't create, destroy or transform body
 * during a physic step computation. With this manager, the events are thrown in a secure way.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class EventManager {
    
    private static final EventManager manager = new EventManager();

    private Set<GoalObserver> goalObservers;
    private List<GoalEvent> goalEvents;
    
    private Set<PaddleContactObserver> paddleObservers;
    private List<PaddleContactEvent> paddleEvents;

    private Set<PlayerObserver> playerObservers;
    private List<PlayerEvent> playerEvents;
    
    private EventManager() {
        goalObservers = new HashSet<GoalObserver>();
        goalEvents = new ArrayList<GoalEvent>();
        
        paddleObservers = new HashSet<PaddleContactObserver>();
        paddleEvents = new ArrayList<PaddleContactEvent>();

        playerObservers = new HashSet<PlayerObserver>();
        playerEvents = new ArrayList<PlayerEvent>();
    }
    
    public static EventManager getEventManager() {
        return manager;
    }
    
    public void initListener() {
        GlobalContactListener.addListener(GoalContactListener.getInstance());
        GlobalContactListener.addListener(PaddleContactListener.getInstance());
        GlobalContactListener.addListener(PlayerContactListener.getInstance());
    }
    
    public void throwEvents() {
        for (GoalEvent event : goalEvents) {
            for (GoalObserver observer : goalObservers) {
                observer.goal(event.getTeam());
            }
        }
        goalEvents.clear();
        
        for (PaddleContactEvent event : paddleEvents) {
            for (PaddleContactObserver observer : paddleObservers) {
                observer.paddleContact();
            }
        }
        paddleEvents.clear();
        
        for (PlayerEvent event : playerEvents) {
        	for (PlayerObserver observer : playerObservers) {
        		observer.setBall(event.getPlayer() ,event.getTeam());
        	}
        }
        playerEvents.clear();
    }
    
    // Goal
    public void addGoalObserver(GoalObserver obs) {
        goalObservers.add(obs);
    }
    
    //Player
    public void addPaddleContactObserver(PaddleContactObserver obs) {
        paddleObservers.add(obs);
    }
    
    /**
     * Adds a Player Observer to the player observer list.
     * @param observer Observer to be added.
     */
    public void addPlayerObserver(PlayerObserver observer) {
    	playerObservers.add(observer);
    }

    public void addEventGoal(boolean isTeamOne) {
        goalEvents.add(new GoalEvent(isTeamOne));
    }
    
	public void addEventPlayers(Player player, boolean team) {
	    playerEvents.add(new PlayerEvent(player, team));
	}
	
	public void addEventPaddle() {
	    paddleEvents.add(new PaddleContactEvent());
	}
    
	/** 
	 * Event when a goal occured
	 * @author Gaylor
	 *
	 */
    class GoalEvent {
        private boolean isTeamOne;
        
        public GoalEvent(boolean team) {
            isTeamOne = team;
        }
        
        public boolean getTeam() {
            return isTeamOne;
        }
    }
    
    /**
     * Class that defines the player event.
     * @author Matvey
     *
     */
    class PlayerEvent {
    	private boolean teamFlag;
    	private Player player;
    	
    	/**
    	 * Contructor of the PlayerEvent class.
    	 * @param playerOfTheEvent The player which is the object of the event.
    	 * @param team The team of the player.
    	 */
    	public PlayerEvent(Player playerOfTheEvent, boolean team) {
    		teamFlag = team;
    		player = playerOfTheEvent;
    	}
    	
    	/**
    	 * Return the team of the player.
    	 * @return True if on team one, else false.
    	 */
    	public boolean getTeam() {
    		return teamFlag;
    	}
    	
    	/**
    	 * Returns the player involved in the event.
    	 * @return Player of the event.
    	 */
    	public Player getPlayer() {
    		return player;
    	}
    }
    
    class PaddleContactEvent {
        
    }
}
