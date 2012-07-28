package by.aig.clientrmi;

public class ClientRunner {

	public static void main(String[] args) {
		ClientFrame clientFrame = new ClientFrame("localhost","1099");
	}

}
//create database meetingDatabase character set utf8  COLLATE utf8_general_ci;
//create table partners (id MEDIUMINT NOT NULL AUTO_INCREMENT,age int, name varchar(255), male boolean, country varchar(255), city varchar(255), phone varchar(255), primary key (id)) character set utf8 collate utf8_general_ci ;

/*CREATE USER 'ilya'@'localhost';
*/
/*GRANT ALL PRIVILEGES ON *.* TO ilya@"localhost"IDENTIFIED BY '' WITH GRANT OPTION;*/
//-encoding UTF-8 -docencoding UTF-8 -charset UTF-8
// -linkoffline http://docs.oracle.com/javase/6/docs/api/ http://docs.oracle.com/javase/6/docs/api/