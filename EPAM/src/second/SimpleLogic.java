package second;

class Faculty {
	private int id;
	private  Department[ ]   arrayDep;
	Faculty (int  id,   String[ ]   idDep)    { 
		// �������������
	}
	Faculty(){}
	// ���������� ����� 
   class   Department   {
     private  int idDep;
     private  String  name;
     public  int getIdDep()    { 
     return  idDep;
   }
	}//����� ���������� ����������� ������ 
   Department [ ]   getDep(){
	 //����������
	   return arrayDep;
   } 
 }

public class SimpleLogic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[ ]  s  =   {"��",  "��"}; 
		Faculty  ob = new Faculty(71,   s); 
		  //Department content = new Department(); // ���������� 
		Faculty.Department d [ ]=  ob.getDep(); 
		Faculty.Department dpt= new Faculty().new Department();
	} 


}
