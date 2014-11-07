package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.physics.box2d.World;

import ch.epfl.sweng.androfoot.interfaces.GoalObserver;

public class EventManager implements GoalObserver {
    
    private static final EventManager manager = new EventManager();
    
    private GoalContactListener goalListener;
    private Set<GoalObserver> goalObservers;
    private List<GoalEvent> goalEvents;
    
    private EventManager() {
        goalListener = new GoalContactListener();
        goalListener.addObserver(this);
        goalObservers = new HashSet<GoalObserver>();
        goalEvents = new ArrayList<GoalEvent>();
    }
    
    public static EventManager getEventManager() {
        return manager;
    }
    
    public void initListener(World world) {
        world.setContactListener(goalListener);
    }
    
    public void throwEvents() {
        for (GoalEvent event : goalEvents) {
            for (GoalObserver observer : goalObservers) {
                observer.goal(event.getTeam());
            }
        }
        goalEvents.clear();
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
    
    class GoalEvent {
        private boolean isTeamOne;
        
        public GoalEvent(boolean team) {
            isTeamOne = team;
        }
        
        public boolean getTeam() {
            return isTeamOne;
        }
    }
}
