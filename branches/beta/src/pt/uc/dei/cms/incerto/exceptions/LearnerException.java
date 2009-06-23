package pt.uc.dei.cms.incerto.exceptions;

public class LearnerException extends IncertoException {
	private static final long serialVersionUID = -2900303007007153679L;

	public LearnerException(String message, Throwable cause)
	{
		super(message,cause);
	}
	
	public LearnerException(String message) 
	{
		super(message);
	}

}
