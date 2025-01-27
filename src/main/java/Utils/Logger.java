package Utils;

import static Utils.Utils.*;


import java.lang.reflect.Field;
import java.util.List;

public class Logger {

	private int columnWidth; // Larghezza della colonna
	private int headerFrequency; // Frequenza di ristampa dell'header

	// Costruttore con valori predefiniti
	public Logger () {
		this.columnWidth = 20; // Larghezza predefinita della colonna
		this.headerFrequency = 50; // Frequenza predefinita di ristampa dell'header
	}

	// Costruttore personalizzato
	public Logger (int columnWidth, int headerFrequency) {
		this.columnWidth = columnWidth;
		this.headerFrequency = headerFrequency;
	}

	/**
	 * Metodo per formattare una riga di valori.
	 *
	 * @param obj L'oggetto da formattare.
	 * @return La riga formattata.
	 */
	private String formatRow (Object obj) {
		StringBuilder row = new StringBuilder();
		Field[] fields = obj.getClass().getDeclaredFields();

		for (Field field : fields) {
			try {
				field.setAccessible(true);
				Object fieldValue = field.get(obj);
				row.append(String.format(" %-" + columnWidth + "s |",
					_T(fieldValue != null ? fieldValue.toString() : "null", columnWidth)));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return row.toString();
	}

	/**
	 * Metodo per costruire l'header e il separatore.
	 *
	 * @param clazz La classe dell'oggetto.
	 * @return Un array contenente l'header e il separatore.
	 */
	private String[] buildHeaderAndSeparator (Class<?> clazz) {
		StringBuilder header = new StringBuilder();
		StringBuilder separator = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			String fieldName = field.getName();
			header.append(String.format(" %-" + columnWidth + "s |", _T(fieldName, columnWidth)));
			separator.append("-".repeat(columnWidth + 2)).append("+");
		}

		return new String[] {header.toString(), separator.toString()};
	}

	/**
	 * Metodo per stampare una lista di oggetti.
	 *
	 * @param list La lista di oggetti da stampare.
	 */
	public void log (List<?> list) {
		if (list == null || list.isEmpty()) {
			_W("La lista è vuota o nulla.");
			return;
		}

		int recordCount = 0;
		String[] headerAndSeparator = null;

		for (Object obj : list) {
			// Stampa l'header e il separatore all'inizio e ogni N record
			if (headerAndSeparator == null || recordCount % headerFrequency == 0) {
				if (recordCount > 0) {
					_W(""); // Aggiunge una riga vuota tra i blocchi
				}
				headerAndSeparator = buildHeaderAndSeparator(obj.getClass());
				_W(headerAndSeparator[0]); // Stampa l'header
				_W(headerAndSeparator[1]); // Stampa il separatore
			}

			// Stampa la riga dei valori
			_W(formatRow(obj));
			recordCount++;
		}
	}
}