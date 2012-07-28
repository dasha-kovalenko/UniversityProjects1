//package by.aig.common;

import java.io.Serializable;

/**
 * РљР»Р°СЃСЃ <code>Candidate</code> РїСЂРµРґСЃС‚Р°РІР»СЏРµС‚ СЃРѕР±РѕР№ РєР»Р°СЃСЃ РґР»СЏ С…СЂР°РЅРµРЅРёСЏ РєР°РЅРґРёРґР°С‚РѕРІ
 * РІ Р±Р°Р·Рµ РґР°РЅРЅС‹С….
 * 
 * @author <i>РђРЅРґСЂРµСЋРє РР»СЊСЏ, 3 РєСѓСЂСЃ, 1 РіСЂСѓРїРїР°</i>
 * @version <b>1.0</b>
 * @since <b>1.0</b>
 */
public class Candidate implements Serializable {

	private int id;
	private int age;
	private String name;
	private boolean male;
	private String country;
	private String city;
	private String phone;

	public Candidate() {
		super();
	}

	public Candidate(int id, int age, String name, boolean male,
			String country, String city, String phone) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
		this.male = male;
		this.country = country;
		this.city = city;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "РРјСЏ: " + name + ", РІРѕР·СЂР°СЃС‚: " + age + ", С‚РµР»РµС„РѕРЅ: " + phone
				+ (male ? ", РїР°СЂРµРЅСЊ" : ", РґРµРІСѓС€РєР°")
				+ (country != null ? ", СЃС‚СЂР°РЅР°: " + country : "")
				+ (city != null ? ", РіРѕСЂРѕРґ: " + city : "") + ", id: " + id;
	}

}
