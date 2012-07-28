//package by.aig.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * РРЅС‚РµСЂС„РµР№СЃСЏ <code>IMeetingDAO</code> РїСЂРµРґРѕСЃС‚Р°РІР»СЏРµС‚ РјРµС‚РѕРґС‹ РґР»СЏ РґРѕСЃС‚СѓРїР° Рє
 * РѕР±СЉРµРєС‚Р°Рј РєР»Р°СЃСЃ {@link Candidate <code>Candidate</code>}.
 * 
 * @see <a
 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/Remote.html">Remote</a>
 * @author <i>РђРЅРґСЂРµСЋРє РР»СЊСЏ, 3 РєСѓСЂСЃ, 1 РіСЂСѓРїРїР°</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public interface IMeetingDAO extends Remote {

	/**
	 * РњРµС‚РѕРґ <code>selectCandidates</code> РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РґР»СЏ РІС‹Р±РѕСЂРєРё
	 * {@link Candidate РєР°РЅРґРёРґР°С‚РѕРІ}, РїРѕРґС…РѕРґСЏС‰РёС… РѕРїСЂРµРґРµР»РµРЅРЅС‹Рј РєСЂРёС‚РµСЂРёСЏРј.
	 * 
	 * @param male
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РїРѕР» РІ РєСЂРёС‚РµСЂРёРµ. РњСѓР¶С‡РёРЅР°, РµСЃР»Рё <code>male</code>
	 *            РёРјРµРµС‚ Р·РЅР°С‡РµРЅРёРµ <code>true</code>.
	 * @param minAge
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РјРёРЅРёРјР°Р»СЊРЅС‹Р№ РІРѕР·СЂР°СЃС‚.
	 * @param maxAge
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РјР°РєСЃРёРјР°Р»СЊРЅС‹Рµ РІРѕР·СЂР°СЃС‚.
	 * @param country
	 *            РћРїСЂРµРґРµР»СЏРµС‚ СЃС‚СЂР°РЅСѓ. Р•СЃР»Рё РїР°СЂР°РјРµС‚СЂ РЅРµРІР°Р¶РµРЅ, С‚Рѕ РѕРЅ Р·Р°РґР°РµС‚СЃСЏ РєР°Рє
	 *            <code>null</code>.
	 * @param city
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РіРѕСЂРѕРґ. Р•СЃР»Рё РїР°СЂР°РјРµС‚СЂ РЅРµРІР°Р¶РµРЅ, С‚Рѕ РѕРЅ Р·Р°РґР°РµС‚СЃСЏ РєР°Рє
	 *            <code>null</code>.
	 * @throws RemoteException
	 * @return Р’РѕР·СЂР°С‰Р°РµС‚ СЃРїРёСЃРѕРє РєР°РЅРґРёРґР°С‚РѕРІ, РїРѕРґС…РѕРґСЏС‰РёС… Р·Р°РґР°РЅРЅС‹Рј РєСЂРёС‚РµСЂРёСЏРј.
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	List<Candidate> selectCandidates(boolean male, int minAge, int maxAge,
			String country, String city) throws RemoteException;

	/**
	 * РњРµС‚РѕРґ <code>insertCandidate</code> РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РґР»СЏ СЃРѕР·РґР°РЅРёСЏ
	 * {@link Candidate РєР°РЅРґРёРґР°С‚РѕРІ} РїРѕ Р·Р°РґР°РЅРЅС‹Рј РїР°СЂР°РјРµС‚СЂР°Рј.
	 * 
	 * @param male
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РїРѕР». РњСѓР¶СЃРєРѕР№, РµСЃР»Рё <code>male</code> РёРјРµРµС‚ Р·РЅР°С‡РµРЅРёРµ
	 *            <code>true</code>.
	 * @param name
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РёРјСЏ.
	 * @param age
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РјРёРЅРёРјР°Р»СЊРЅС‹Р№ РІРѕР·СЂР°СЃС‚.
	 * @param country
	 *            РћРїСЂРµРґРµР»СЏРµС‚ СЃС‚СЂР°РЅСѓ. Р•СЃР»Рё РїР°СЂР°РјРµС‚СЂ РЅРµРІР°Р¶РµРЅ, С‚Рѕ РѕРЅ Р·Р°РґР°РµС‚СЃСЏ РєР°Рє
	 *            <code>null</code>.
	 * @param city
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РіРѕСЂРѕРґ. Р•СЃР»Рё РїР°СЂР°РјРµС‚СЂ РЅРµРІР°Р¶РµРЅ, С‚Рѕ РѕРЅ Р·Р°РґР°РµС‚СЃСЏ РєР°Рє
	 *            <code>null</code>.
	 * @param phone
	 *            РћРїСЂРµРґРµР»СЏРµС‚ РЅРѕРјРµСЂ С‚РµР»РµС„РѕРЅР°.
	 * @throws RemoteException
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	void insertCandidate(boolean male, String name, int age, String country,
			String city, String phone) throws RemoteException;

	/**
	 * РњРµС‚РѕРґ <code>deleteCandidate</code> РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РґР»СЏ СѓРґР°Р»РµРЅРёСЏ
	 * {@link Candidate РєР°РЅРґРёРґР°С‚Р°} РїРѕ <code>id</>.
	 * 
	 * @param id
	 *            РРґРµРЅС‚РёС„РёРєР°С‚РѕСЂ РєР°РЅРґРёРґР°С‚Р°, РїРѕРґР»РµР¶Р°С‰РµРіРѕ СѓРґР°Р»РµРЅРёСЋ.
	 * @throws RemoteException
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	void deleteCandidate(int id) throws RemoteException;

	/**
	 * РњРµС‚РѕРґ <code>getCandidate</code> РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РґРѕСЃС‚СѓРїР° Рє {@link Candidate
	 * РєР°РЅРґРёРґР°С‚Сѓ} РїРѕ <code>id</>.
	 * 
	 * @param id
	 *            РРґРµРЅС‚РёС„РёРєР°С‚РѕСЂ РєР°РЅРґРёРґР°С‚Р°.
	 * @throws RemoteException
	 * @return Р’РѕР·РІСЂР°С‰Р°РµС‚ РєР°РЅРґРёРґР°С‚Р° РёР»Рё <code>null</code>, РµСЃР»Рё РєР°РЅРґРёРґР°С‚ СЃ С‚Р°РєРёРј
	 *         <code>id</code> РЅРµ Р±С‹Р» РЅР°Р№РґРµРЅ.
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	Candidate getCandidate(int id) throws RemoteException;

	/**
	 * РњРµС‚РѕРґ <code>getCandidate</code> РёСЃРїРѕР»СЊР·СѓРµС‚СЃСЏ РѕР±РЅРѕРІР»РµРЅРёСЏ РґР°РЅРЅС‹С…
	 * {@link Candidate РєР°РЅРґРёРґР°С‚Р°}.
	 * 
	 * @param candidate
	 *            РћР±СЉРµРєС‚ РєР»Р°СЃСЃР° {@link Candidate РєР°РЅРґРёРґР°С‚Р°} .
	 * @throws RemoteException
	 * @see <a
	 *      href="http://docs.oracle.com/javase/1.4.2/docs/api/java/rmi/RemoteException.html">RemoteException</a>
	 * @since <b>1.0</b>
	 */
	void updateCandidate(Candidate candidate) throws RemoteException;
}
