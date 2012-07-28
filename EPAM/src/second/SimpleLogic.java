package second;

class Faculty {
	private int id;
	private  Department[ ]   arrayDep;
	Faculty (int  id,   String[ ]   idDep)    { 
		// инициализация
	}
	Faculty(){}
	// внутренний класс 
   class   Department   {
     private  int idDep;
     private  String  name;
     public  int getIdDep()    { 
     return  idDep;
   }
	}//конец объявления внутреннего класса 
   Department [ ]   getDep(){
	 //реализация
	   return arrayDep;
   } 
 }

public class SimpleLogic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[ ]  s  =   {"ВМ",  "ЧМ"}; 
		Faculty  ob = new Faculty(71,   s); 
		  //Department content = new Department(); // недоступен 
		Faculty.Department d [ ]=  ob.getDep(); 
		Faculty.Department dpt= new Faculty().new Department();
	} 


}
