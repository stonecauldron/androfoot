package ch.epfl.sweng.androfoot.box2dphysics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.epfl.sweng.androfoot.interfaces.DefaultBall;
import ch.epfl.sweng.androfoot.interfaces.DefaultBorder;
import ch.epfl.sweng.androfoot.interfaces.BorderObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultEventManager;
import ch.epfl.sweng.androfoot.interfaces.DefaultGoal;
import ch.epfl.sweng.androfoot.interfaces.DefaultPowerUp;
import ch.epfl.sweng.androfoot.interfaces.GoalObserver;
import ch.epfl.sweng.androfoot.interfaces.PaddleContactObserver;
import ch.epfl.sweng.androfoot.interfaces.DefaultPlayer;
import ch.epfl.sweng.androfoot.interfaces.PlayerObserver;
import ch.epfl.sweng.androfoot.interfaces.PowerUpObserver;

/**
 * Manage the events of the physic world. We need that because we can't create,
 * destroy or transform body during a physic step computation. With this
 * manager, the events are thrown in a secure way.
 * 
 * @author Gilthoniel (Gaylor Bosson)
 *
 */
public final class EventManager implements DefaultEventManager {

	private static final EventManager INSTANCE = new EventManager();

	private Set<GoalObserver> goalObservers;
	private List<GoalEvent> goalEvents;

	private Set<PaddleContactObserver> paddleObservers;
	private List<PaddleContactEvent> paddleEvents;

	private Set<PlayerObserver> playerObservers;
	private List<PlayerEvent> playerEvents;

	private Set<BorderObserver> borderObservers;
	private List<BorderContactEvent> borderEvents;
	
	private Set<PowerUpObserver> powerUpObservers;
	private List<PowerUpContactEvent> powerUpEvents;

	private EventManager() {
		goalObservers = new HashSet<GoalObserver>();
		goalEvents = new ArrayList<GoalEvent>();

		paddleObservers = new HashSet<PaddleContactObserver>();
		paddleEvents = new ArrayList<PaddleContactEvent>();

		playerObservers = new HashSet<PlayerObserver>();
		playerEvents = new ArrayList<PlayerEvent>();

		borderObservers = new HashSet<BorderObserver>();
		borderEvents = new ArrayList<BorderContactEvent>();
		
		powerUpObservers = new HashSet<PowerUpObserver>();
		powerUpEvents = new ArrayList<PowerUpContactEvent>();

		PaddleContactListener.setEventManager(this);
		GoalContactListener.setEventManager(this);
		BorderContactListener.setEventManager(this);
		PlayerContactListener.setEventManager(this);
		PowerUpContactListener.setEventManager(this);
		
		GlobalContactListener.addListener(GoalContactListener.getInstance());
		GlobalContactListener.addListener(PaddleContactListener.getInstance());
		GlobalContactListener.addListener(PlayerContactListener.getInstance());
		GlobalContactListener.addListener(BorderContactListener.getInstance());
		GlobalContactListener.addListener(PowerUpContactListener.getInstance());
	}

	public static EventManager getEventManager() {
		return INSTANCE;
	}

	public void throwEvents() {
        for (GoalEvent event : goalEvents) {
            Iterator<GoalObserver> it = goalObservers.iterator();
            while (it.hasNext()) {
                it.next().goal(event.getGoal(), event.getBall());
            }
        }
        goalEvents.clear();
        
        for (PaddleContactEvent event : paddleEvents) {
            Iterator<PaddleContactObserver> it = paddleObservers.iterator();
            while (it.hasNext()) {
                it.next().paddleContact(event.getPlayer(), event.getBall());
            }
        }
        paddleEvents.clear();
        
        for (PlayerEvent event : playerEvents) {
            Iterator<PlayerObserver> it = playerObservers.iterator();
        	while (it.hasNext()) {
        		it.next().setBall(event.getPlayer(), event.getTeam());
        	}
        }
        playerEvents.clear();
        
        for (BorderContactEvent event : borderEvents) {
            Iterator<BorderObserver> it = borderObservers.iterator();
            while (it.hasNext()) {
                it.next().borderContact(event.getBorder(), event.getBall());
            }
        }
        borderEvents.clear();
        
        for (PowerUpContactEvent event : powerUpEvents) {
            Iterator<PowerUpObserver> it = powerUpObservers.iterator();
        	while (it.hasNext()) {
        		it.next().applyPowerUp(event.getPowerUp());
        	}
        }
        powerUpEvents.clear();
    }

	/**
	 * Add an observer to the goal event
	 * 
	 * @param obs
	 */
	public void addGoalObserver(GoalObserver obs) {
		goalObservers.add(obs);
	}

	/**
	 * Add an observer to the paddle contact event
	 * 
	 * @param obs
	 */
	public void addPaddleContactObserver(PaddleContactObserver obs) {
		paddleObservers.add(obs);
	}

	/**
	 * Adds a Player Observer to the player observer list.
	 * 
	 * @param observer
	 *            Observer to be added.
	 */
	public void addPlayerObserver(PlayerObserver observer) {
		playerObservers.add(observer);
	}

	/**
	 * Add an observer to the border contact event
	 * 
	 * @param observer
	 */
	public void addBorderContactObserver(BorderObserver observer) {
		borderObservers.add(observer);
	}
	
	/**
	 * Adds a power up observer to the observer list.
	 * @param observer Observer to add.
	 */
	public void addPowerUpContactObserver(PowerUpObserver observer) {
		powerUpObservers.add(observer);
	}

	public void addEventGoal(DefaultGoal goal, DefaultBall ball) {
	    goal = goal.getStates();
	    ball = ball.getStates();
	    
		goalEvents.add(new GoalEvent(ball, goal));
	}

	public void addEventPlayers(Player player, boolean team) {
		playerEvents.add(new PlayerEvent(player, team));
	}

	public void addEventPaddle(DefaultPlayer player, DefaultBall ball) {
	    player = player.getStates();
	    ball = ball.getStates();
	    
		paddleEvents.add(new PaddleContactEvent(player, ball));
	}

	public void addEventBorder(DefaultBorder border, DefaultBall ball) {
	    border = border.getStates();
	    ball = ball.getStates();
	    
		borderEvents.add(new BorderContactEvent(border, ball));
	}
	
	public void addEventPowerUp(DefaultPowerUp powerUp) {
		powerUpEvents.add(new PowerUpContactEvent(powerUp));
	}

	/**
	 * Event when a goal occured
	 * 
	 * @author Gaylor
	 *
	 */
	class GoalEvent {
		private DefaultGoal goal;
		private DefaultBall ball;

		public GoalEvent(DefaultBall b, DefaultGoal g) {
			goal = g;
			ball = b;
		}

		public DefaultGoal getGoal() {
			return goal;
		}
		
		public DefaultBall getBall() {
		    return ball;
		}
	}

	/**
	 * Class that defines the player event.
	 * 
	 * @author Matvey
	 *
	 */
	class PlayerEvent {
		private boolean teamFlag;
		private Player player;

		/**
		 * Contructor of the PlayerEvent class.
		 * 
		 * @param playerOfTheEvent
		 *            The player which is the object of the event.
		 * @param team
		 *            The team of the player.
		 */
		public PlayerEvent(Player playerOfTheEvent, boolean team) {
			teamFlag = team;
			player = playerOfTheEvent;
		}

		/**
		 * Return the team of the player.
		 * 
		 * @return True if on team one, else false.
		 */
		public boolean getTeam() {
			return teamFlag;
		}

		/**
		 * Returns the player involved in the event.
		 * 
		 * @return Player of the event.
		 */
		public Player getPlayer() {
			return player;
		}

	}

	/**
	 * When a ball touch a player
	 * 
	 * @author Gaylor
	 *
	 */
	class PaddleContactEvent {
	    private DefaultPlayer player;
	    private DefaultBall ball;
	    
	    public PaddleContactEvent(DefaultPlayer p, DefaultBall b) {
	        player = p;
	        ball = b;
	    }
	    
	    public DefaultPlayer getPlayer() {
	        return player;
	    }
	    
	    public DefaultBall getBall() {
	        return ball;
	    }
	}

	/**
	 * When a ball touch a border
	 * 
	 * @author Gaylor
	 *
	 */
	class BorderContactEvent {
		private DefaultBorder border;
		private DefaultBall ball;

		public BorderContactEvent(DefaultBorder b, DefaultBall ba) {
			border = b;
			ball = ba;
		}

		public DefaultBorder getBorder() {
		    return border;
		}
		
		public DefaultBall getBall() {
		    return ball;
		}
	}
	
	/**
	 * Class that defines the power up contact event.
	 * @author Matvey
	 *
	 */
	class PowerUpContactEvent {
		private DefaultPowerUp powerUp;
		
		/**
		 * Constructor of the power up event class.
		 * @param powerUpOfTheEvent Power Up object of the event.
		 */
		public PowerUpContactEvent(DefaultPowerUp powerUpOfTheEvent) {
			powerUp = powerUpOfTheEvent;
		}
		
		/**
		 * Returns the power up object of the event.
		 * @return
		 */
		public DefaultPowerUp getPowerUp() {
			return powerUp;
		}
	}
}
