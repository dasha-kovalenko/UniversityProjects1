package by.kovalenko.periodicals.dao;

import java.util.List;

import by.kovalenko.periodicals.domain.Edition;
import by.kovalenko.periodicals.domain.Subscription;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public interface IPeriodicalsSubscriptionsDAO {

	public Subscription getSubscription(long id) throws PeriodicalsDAOException;

	public List<Subscription> listSubscriptions(long user_id) throws PeriodicalsDAOException;

	public void deleteSubscription(long id) throws PeriodicalsDAOException;

	public void saveSubscription(Subscription subscription) throws PeriodicalsDAOException;

	public void updateSubscription(Subscription subscription) throws PeriodicalsDAOException;
	
	public List<Edition> getEditionsFromSubscription(Long subscriptionId) throws PeriodicalsDAOException;

}
