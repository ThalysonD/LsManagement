package LS.Management.Ls.Management.CustomExceptions;

public class CustomExceptions  extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public static class EmailDuplicadoException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public EmailDuplicadoException(String mensagem) {
            super(mensagem);
        }
    }

	public static class SenhaMinimaException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public SenhaMinimaException(String mensagem) {
            super(mensagem);
        }
	}

    public static class TelefoneMinimoException extends RuntimeException {
        private static final long serialVersionUID = 1L;
    
        public TelefoneMinimoException(String mensagem) {
            super(mensagem);
        }
    }
}