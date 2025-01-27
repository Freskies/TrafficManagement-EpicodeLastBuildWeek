package Utils;

import java.util.Scanner;

public class Utils {

	public static Object listen (ScannerType type, Scanner scanner) {
		Object risultato = null;
		boolean inputValido = false;

		while (!inputValido) {
			try {
				String input = scanner.nextLine();

				risultato = switch (type) {
					case ScannerType.INT -> Integer.parseInt(input);
					case ScannerType.DOUBLE -> Double.parseDouble(input);
					case ScannerType.STRING -> input;
					case null -> throw new IllegalArgumentException("Invalid type");
				};
				inputValido = true;
			} catch (Exception e) {
				_W("Input non valido, riprova. " + e.getMessage());
			}
		}

		return risultato;
	}

	// Stampa testo
	public static void _W (String text) {
		System.out.println(text);
	}

	// Stampa testo senza ritorno a capo
	public static void _Wn (String text) {
		System.out.print(text);
	}

	// Ripete carattere
	public static void _R (String character, int times) {
		_Wn(character.repeat(times));
		_W("");
	}

	// Ripete carattere senza ritorno a capo
	public static void _Rn (String character, int times) {
		_Wn(character.repeat(times));
	}

	// Cancella schermo
	public static void _C () {
		for (int i = 0; i < 50; i++) {
			_W("");
		}
	}

	// Attende la pressione di un carattere per proseguire
	@SuppressWarnings ("resource")
	public static void _P () {
		_W("Premi INVIO per continuare...");
		new Scanner(System.in).nextLine();
		_W("");
	}

	// Tronca la stringa a n caratteri aggiungendo "..."
	public static String _T (String text, int l) {
		return text.length() > l ? text.substring(0, l - 3) + "..." : text;
	}
}
