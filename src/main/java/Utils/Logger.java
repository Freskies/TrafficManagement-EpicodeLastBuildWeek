package Utils;

import static Utils.Utils.*;

import java.lang.reflect.Field;

public class Logger {

	public static void log(Object obj) {

		Class<?> clazz = obj.getClass();
		Field[] fields = obj.getClass().getDeclaredFields();

		StringBuilder header = new StringBuilder();
		StringBuilder separator = new StringBuilder();
		StringBuilder row = new StringBuilder();

		for (Field field : fields) {
			try {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object fieldValue = field.get(obj);

				// Formatta l'intestazione
				header.append(String.format(" %-20s |", _T(fieldName, 20)));

				// Formatta il separatore
				separator.append("-".repeat(22)).append("+");

				// Formatta la riga dei valori
				row.append(String.format(" %-20s |", _T(fieldValue != null ? fieldValue.toString() : "null", 20)));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Book book = new Book("Il Signore degli Anelli", "J.R.R. Tolkien", "Fantasy", 1954);
		Logger.log(book);
	}
}
