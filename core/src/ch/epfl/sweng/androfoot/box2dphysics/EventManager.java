package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;

public class EventManager implements GoalObserver, PlayerObserver {
    
    private static final EventManager manager = new EventManager();
    
    private GoalContactListener goalListener;
    private Set<GoalObserver> goalObservers;
    private List<GoalEvent> goalEvents;
    
    private PlayersContactListener playerListener;
    private Set<PlayerObserver> playerObservers;
    private List<PlayerEvent> playerEvents;
    
    private EventManager() {
        goalListener = new GoalContactListener();
        goalListener.addObserver(this);
        goalObservers = new HashSet<GoalObserver>();
        goalEvents = new ArrayList<GoalEvent>();
        
        playerListener = new PlayersContactListener();
        playerListener.addObserver(this);
        playerObservers = new HashSet<PlayerObserver>();
        playerEvents = new ArrayList<PlayerEvent>();
    }
    
    public static EventManager getEventManager() {
        return manager;
    }
    
    public void initListener(World world) {
        world.setContactListener(goalListener);
        
        world.setContactListener(playerListener);
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
        		observer.setBall(event.getTeam());
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
    
    //Player
    public void addPlayerListener(Player player) {
    	playerListener.addPlayer(player);
    }
    
    public void addPlayerObserver(PlayerObserver observer) {
    	playerObservers.add(observer);
    }

    @Override
    public void goal(boolean isTeamOne) {
        goalEvents.add(new GoalEvent(isTeamOne));
    }
    
    
    
    @Override
	public void setBall(boolean teamFlag) {
		playerEvents.add(new PlayerEvent(teamFlag));
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
    
    class PlayerEvent {
    	private boolean teamFlag;
    	
    	public PlayerEvent(boolean team) {
    		teamFlag = team;
    	}
    	
    	public boolean getTeam() {
    		return teamFlag;
    	}
    }
}