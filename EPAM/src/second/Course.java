package second;

public class Course {
public int id = 71;

public Course() {
 System.out.println("����������� ������ Course");
 id = getId();//!!!
 System.out.println(" id=" + id);
}
public int getId() {
System.out.println("getId() ������ Course");
return id;
}
}
