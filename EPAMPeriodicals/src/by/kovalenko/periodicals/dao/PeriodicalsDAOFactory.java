package by.kovalenko.periodicals.dao;

import java.util.HashMap;

import by.kovalenko.periodicals.managers.TablesNamesSQLManager;

public class PeriodicalsDAOFactory {
	private static PeriodicalsDAOFactory periodicalsDAOFactory;

	private static final HashMap<String, IPeriodicalsDAO> DAO_MAP;

	static {
		DAO_MAP = new HashMap<String, IPeriodicalsDAO>();
		DAO_MAP.put(
				TablesNamesSQLManager.getInstance().getValue(
						TablesNamesSQLManager.EDITIONS),
				new PeriodicalsEditionsDAO());
		DAO_MAP.put(
				TablesNamesSQLManager.getInstance().getValue(
						TablesNamesSQLManager.SUBSCRIPTIONS),
				new PeriodicalsSubscriptionsDAO());
		DAO_MAP.put(
				TablesNamesSQLManager.getInstance().getValue(
						TablesNamesSQLManager.USERS), new PeriodicalsUsersDAO());
	}

	public static PeriodicalsDAOFactory getInstance() {
		if (periodicalsDAOFactory == null)
			periodicalsDAOFactory = new PeriodicalsDAOFactory();
		return periodicalsDAOFactory;
	}

	public IPeriodicalsDAO getDAO(String string) {
		IPeriodicalsDAO pDAO = null;
		/*
		 * получение объекта, соответствующего DAO
		 */
		pDAO = DAO_MAP.get(string);
		if (pDAO == null)
			pDAO = new PeriodicalsDAO();
		return pDAO;
	}
}
