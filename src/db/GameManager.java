package db;

import java.util.List;
import java.util.logging.Logger;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class GameManager {

	private static SessionFactory factory;
	private Game game;

	public GameManager(Game game) {
		this.game = game;
		Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void addGame() {
		if (!playerExist())
			addPlayer();
		if (!levelExist())
			addLevel();
		Transaction tx = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			session.save(game);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private boolean levelExist() {
		Session session = factory.openSession();
		try {
			Query<Level> query = session.createQuery("from Levels");
			List<Level> list = query.list();
			for (Level level: list)
			{
				if(level.getLevelName().equals(this.game.getLevelName()))
						return true;
			}
			return false;
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return false;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	private boolean playerExist() {
		Session session = factory.openSession();
		try {
			Query<Player> query = session.createQuery("from Players");
			List<Player> list = query.list();
			for (Player player: list)
			{
				if(player.getPlayerName().equals(this.game.getPlayerName()))
						return true;
			}
			return false;
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
		return false;
	}

	private void addLevel() {
		Transaction tx = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			session.save(new Level(this.game.getLevelName()));
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	private void addPlayer() {
		Transaction tx = null;
		Session session = factory.openSession();
		try {
			tx = session.beginTransaction();
			session.save(new Player(this.game.getPlayerName()));
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
