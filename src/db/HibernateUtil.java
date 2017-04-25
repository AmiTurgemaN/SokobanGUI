package db;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("deprecation")
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static
    {
        try
        {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
        }
        catch (Throwable ex)
        {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
    
    @SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getPlayerRecord(String playerName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getPlayerName().equalsIgnoreCase(playerName))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}
    
    @SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getPlayerRecordsContain(String playerName,String levelName) {
		ObservableList<Game> playerRecordList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getPlayerName().toLowerCase().contains(playerName.toLowerCase()) && g.getLevelName().equalsIgnoreCase(levelName))
				playerRecordList.add(g);
		}
		return playerRecordList;
	}

	@SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getLevelRecordsContain(String levelName, String playerName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getLevelName().toLowerCase().contains(levelName.toLowerCase()) && g.getPlayerName().equalsIgnoreCase(playerName))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getLevelWorldWideRecordsContain(String levelName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getLevelName().toLowerCase().contains(levelName.toLowerCase()))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getPlayerWorldWideRecordsContain(String playerName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getPlayerName().toLowerCase().contains(playerName.toLowerCase()))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}

	@SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getWorldWideRecords() {
		ObservableList<Game> worldWideRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			worldWideRecordsList.add(g);
		}
		return worldWideRecordsList;
	}

	@SuppressWarnings({ "unchecked" })
	public static ObservableList<Game> getLevelRecords(String levelName) {
		ObservableList<Game> levelRecordsList = FXCollections.observableArrayList();
		Query<Game> query = HibernateUtil.getSessionFactory().openSession().createQuery("from Games");
		List<Game> gameList = query.list();
		for (Game g : gameList) {
			if (g.getLevelName().equalsIgnoreCase(levelName))
				levelRecordsList.add(g);
		}
		return levelRecordsList;
	}
}
