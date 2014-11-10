package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;

/**
 * Manage the events of the physic world. We need that because we can't create, destroy or transform body
 * during a physic step computation. With this manager, the events are thrown in a secure way.
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public class EventManager implements GoalObserver, PlayerObserver {
    
    private static final EventManager manager = new EventManager();
    
    private GoalContactListener goalListener;
    private Set<GoalObserver> goalObservers;
    private List<GoalEvent> goalEvents;
    
    private PlayerContactListener playerListener;
    private Set<PlayerObserver> playerObservers;
    private List<PlayerEvent> playerEvents;
    
    private EventManager() {
        goalListener = new GoalContactListener();
        goalListener.addObserver(this);
        goalObservers = new HashSet<GoalObserver>();
        goalEvents = new ArrayList<GoalEvent>();
        
        playerListener = new PlayerContactListener();
        playerListener.addObserver(this);
        playerObservers = new HashSet<PlayerObserver>();
        playerEvents = new ArrayList<PlayerEvent>();
    }
    
    public static EventManager getEventManager() {
        return manager;
    }
    
    public void initListener(GlobalContactListener listener) {
        listener.addListener(goalListener);
        
        listener.addListener(playerListener);
    }
    
    public void throwEvents() {
        for (GoalEvent event : goalEvents) {
            for (GoalObserver observer : goalObservers) {
                observer.goal(event.getTeam());
            }
        }
        goalEvents.clear();
        
        for (PlayerEvent event : playerEvents) {
        	for (PlayerObserver observer : playerObservers) {
        		observer.setBall(event.getPlayer() ,event.getTeam());
        	}
        }
        playerEvents.clear();
    }
    
    public void addGoalListener(Goal goal) {
        goalListener.addGoal(goal);
    }
    
    public void addGoalObserver(GoalObserver obs) {
        goalObservers.add(obs);
    }
    
    @Override
    public void goal(boolean isTeamOne) {
        goalEvents.add(new GoalEvent(isTeamOne));
    }
    
    //Player
    /**
     * Adds a player to the list of the Player Contact Listener.
     * @param player Player to add.
     */
    public void addPlayerListener(Player player) {
    	playerListener.addPlayer(player);
    }
    
    /**
     * Adds a Player Observer to the player observer list.
     * @param observer Observer to be added.
     */
    public void addPlayerObserver(PlayerObserver observer) {
    	playerObservers.add(observer);
    }
    
	@Override
	public void setBall(Player player, boolean teamFlag) {
		playerEvents.add(new PlayerEvent(player, teamFlag));
	}
    
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
}
