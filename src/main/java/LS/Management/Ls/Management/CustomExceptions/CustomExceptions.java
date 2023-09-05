package LS.Management.Ls.Management.CustomExceptions;

public class CustomExceptions  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static class EmailDuplicadoException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public EmailDuplicadoException(String mensagem) {
            super(mensagem);
        }
    }
}