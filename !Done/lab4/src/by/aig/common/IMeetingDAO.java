package by.aig.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Интерфейся <code>IMeetingDAO</code> предоставляет методы для доступа к
 * объектам класс {@link Candidate <code>Candidate</code>}.
 * 
 * @see <a
 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/Remote.html">Remote</a>
 * @author <i>Андреюк Илья, 3 курс, 1 группа</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public interface IMeetingDAO extends Remote {

	/**
	 * Метод <code>selectCandidates</code> используется для выборки
	 * {@link Candidate кандидатов}, подходящих определенным критериям.
	 * 
	 * @param male
	 *            Определяет пол в критерие. Мужчина, если <code>male</code>
	 *            имеет значение <code>true</code>.
	 * @param minAge
	 *            Определяет минимальный возраст.
	 * @param maxAge
	 *            Определяет максимальные возраст.
	 * @param country
	 *            Определяет страну. Если параметр неважен, то он задается как
	 *            <code>null</code>.
	 * @param city
	 *            Определяет город. Если параметр неважен, то он задается как
	 *            <code>null</code>.
	 * @throws RemoteException
	 * @return Возращает список кандидатов, подходящих заданным критериям.
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	List<Candidate> selectCandidates(boolean male, int minAge, int maxAge,
			String country, String city) throws RemoteException;

	/**
	 * Метод <code>insertCandidate</code> используется для создания
	 * {@link Candidate кандидатов} по заданным параметрам.
	 * 
	 * @param male
	 *            Определяет пол. Мужской, если <code>male</code> имеет значение
	 *            <code>true</code>.
	 * @param name
	 *            Определяет имя.
	 * @param age
	 *            Определяет минимальный возраст.
	 * @param country
	 *            Определяет страну. Если параметр неважен, то он задается как
	 *            <code>null</code>.
	 * @param city
	 *            Определяет город. Если параметр неважен, то он задается как
	 *            <code>null</code>.
	 * @param phone
	 *            Определяет номер телефона.
	 * @throws RemoteException
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	void insertCandidate(boolean male, String name, int age, String country,
			String city, String phone) throws RemoteException;

	/**
	 * Метод <code>deleteCandidate</code> используется для удаления
	 * {@link Candidate кандидата} по <code>id</>.
	 * 
	 * @param id
	 *            Идентификатор кандидата, подлежащего удалению.
	 * @throws RemoteException
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	void deleteCandidate(int id) throws RemoteException;

	/**
	 * Метод <code>getCandidate</code> используется доступа к {@link Candidate
	 * кандидату} по <code>id</>.
	 * 
	 * @param id
	 *            Идентификатор кандидата.
	 * @throws RemoteException
	 * @return Возвращает кандидата или <code>null</code>, если кандидат с таким
	 *         <code>id</code> не был найден.
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	Candidate getCandidate(int id) throws RemoteException;

	/**
	 * Метод <code>getCandidate</code> используется обновления данных
	 * {@link Candidate кандидата}.
	 * 
	 * @param candidate
	 *            Объект класса {@link Candidate кандидата} .
	 * @throws RemoteException
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	void updateCandidate(Candidate candidate) throws RemoteException;
}
