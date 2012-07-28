package by.kovalenko.periodicals.exceptions;

public class PeriodicalsDAOException extends Exception {

	private static final long serialVersionUID = 1L;

	protected Exception exception;
	protected String message;

	public PeriodicalsDAOException(Exception ex) {
		this.exception = ex;
	}

	public PeriodicalsDAOException(String er) {
		super(er);
		this.message = er;
	}

	public PeriodicalsDAOException(String er, Exception ex) {
		super(er);
		this.exception = ex;
		this.message = er;
	}

	public Exception getException() {
		return this.exception;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
