package second;

public class BaseCourse extends Course {
public int id = 90;// ��� ������ �� �������!

public BaseCourse() {
 System.out.println("����������� ������ BaseCourse");
 System.out.println(" id=" + getId());
}
public int getId() {
    System.out.println("getId() ������ BaseCourse");
    return id;
}
}

