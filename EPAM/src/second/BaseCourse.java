package second;

public class BaseCourse extends Course {
public int id = 90;// так делать не следует!

public BaseCourse() {
 System.out.println("конструктор класса BaseCourse");
 System.out.println(" id=" + getId());
}
public int getId() {
    System.out.println("getId() класса BaseCourse");
    return id;
}
}

