
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Flower implements Comparable<Flower>,
							   Serializable{
		private ImageIcon picture;
		private String name;
		private Integer temperature;
		private String origin;		
		
		public Flower( String pic, String name, Integer t,String o){
			this.setName(name);
			this.setPicture(pic);
			this.setOrigin(o);
			this.setTemperature(t);
		}
		public Flower(){}
		public void setName(String n){this.name=n;}		
		public void setOrigin(String n){this.origin=n;}
		public void setTemperature(Integer n){this.temperature=n;}
		public void setPicture(String n){this.picture=new ImageIcon(n);}
		
		public String getName(){return this.name;}
		public String getOrigin(){return this.origin;}
		public Integer getTemperature(){return this.temperature;}
		public ImageIcon getPicture(){return this.picture;}
		@Override
		public int compareTo(Flower f) {
			return this.getName().compareTo(f.getName());
		}
		
	}
