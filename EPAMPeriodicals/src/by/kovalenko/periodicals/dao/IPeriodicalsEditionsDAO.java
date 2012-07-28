package by.kovalenko.periodicals.dao;

import java.util.List;

import by.kovalenko.periodicals.entities.Edition;
import by.kovalenko.periodicals.exceptions.PeriodicalsDAOException;

public interface IPeriodicalsEditionsDAO extends IPeriodicalsDAO {

	public Edition getEdition(long id) throws PeriodicalsDAOException;

	public List<Edition> listEditions() throws PeriodicalsDAOException;

	public void deleteEdition(long id) throws PeriodicalsDAOException;

	public void saveEdition(Edition edition) throws PeriodicalsDAOException;

	public void addEditionToCart(long cartId, long editionId)
			throws PeriodicalsDAOException;

	public void deleteEditionFromCart(long cartId, long editionId)
			throws PeriodicalsDAOException;

	public void updateEdition(Edition edition) throws PeriodicalsDAOException;
}
