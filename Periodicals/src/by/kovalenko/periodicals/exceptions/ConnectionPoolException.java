package by.kovalenko.periodicals.exceptions;

public class ConnectionPoolException extends PeriodicalsDAOException {

	private static final long serialVersionUID = 1L;

	public ConnectionPoolException(Exception ex) {
		super(ex);
	}

	public ConnectionPoolException(String er) {
		super(er);
		this.message = er;
	}

	public ConnectionPoolException(String er, Exception ex) {
		super(er);
		this.exception = ex;
		this.message = er;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
