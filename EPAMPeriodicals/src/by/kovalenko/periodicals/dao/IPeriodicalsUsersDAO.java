package by.kovalenko.periodicals.dao;

import java.util.List;

import by.kovalenko.periodicals.entities.Subscription;
import by.kovalenko.periodicals.entities.User;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public interface IPeriodicalsUsersDAO extends IPeriodicalsDAO {

	public User getUser(long id) throws PeriodicalsDAOException;

	public User getUser(String username, String password)
			throws PeriodicalsDAOException;

	public User getUser(String username) throws PeriodicalsDAOException;

	public List<User> listUsers() throws PeriodicalsDAOException;

	public void deleteUser(long id) throws PeriodicalsDAOException;

	public void saveUser(User user) throws PeriodicalsDAOException;

	public void addCartToSubscriptionAndDelete(long cartId)
			throws PeriodicalsDAOException;

	public void createSubscription(Long userId, Subscription subscription)
			throws PeriodicalsDAOException;

	public void clearCart(Long cartId) throws PeriodicalsDAOException;

	public void updateUser(User user) throws PeriodicalsDAOException;

	public void addEditionsToSubscriptionFromCart(Long subscriptionId,
			Long cartId) throws PeriodicalsDAOException;

	public Long getLastSubscriptionFromUser(long userId)
			throws PeriodicalsDAOException;

	public double getCartPrice(Long cartId) throws PeriodicalsDAOException;

}
