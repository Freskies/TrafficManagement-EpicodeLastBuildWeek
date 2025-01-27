package Utils;

import java.lang.reflect.Field;
import Utils.Book;

public class Logger {

	public static void log (Object obj) {

		Class<?> clazz = obj.getClass();
		Field[] fields = obj.getClass().getDeclaredFields();

			for (Field field : fields) {
				try {
					field.setAccessible(true); // Permette l'accesso ai campi privati
					String fieldName = field.getName();
					Object fieldValue = field.get(obj);

					System.out.println("Name: " + fieldName +  ", Value: " + fieldValue);

			}catch (IllegalArgumentException | IllegalAccessException e){
				e.printStackTrace();
			}
		}

	}

	public static void main (String[] args) {
			Book book = new Book("Il Signore degli Anelli", "J.R.R. Tolkien", "Fantasy", 1954);
			Logger.log(book);
		}
	}
