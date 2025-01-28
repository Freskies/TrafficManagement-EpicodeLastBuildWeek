package Utils;

import static Utils.Utils.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class Logger {

	private int columnWidth;
	private int headerFrequency;

	public Logger() {
		this.columnWidth = 20;
		this.headerFrequency = 50;
	}

	public Logger(int columnWidth, int headerFrequency) {
		this.columnWidth = columnWidth;
		this.headerFrequency = headerFrequency;
	}

	private <A extends Annotation> A isAnnotated(Field field, Class<A> annotationClass) {
		return field.isAnnotationPresent(annotationClass) ? field.getAnnotation(annotationClass) : null;
	}

	private String formatRow(Object obj) {
		StringBuilder row = new StringBuilder();
		Field[] fields = obj.getClass().getDeclaredFields();

		row.append("|");
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if (isAnnotated(field, jakarta.persistence.OneToMany.class) != null
						|| isAnnotated(field, jakarta.persistence.ManyToOne.class) != null
						|| isAnnotated(field, jakarta.persistence.OneToOne.class) != null
						|| isAnnotated(field, jakarta.persistence.ManyToMany.class) != null) {
					continue;
				}
				Object fieldValue = field.get(obj);
				row.append(String.format(" %-" + columnWidth + "s |",
						_T(fieldValue != null ? fieldValue.toString() : "null", columnWidth)));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return row.toString();
	}

	private String[] buildHeaderAndSeparator(Class<?> clazz) {
		StringBuilder header = new StringBuilder();
		StringBuilder separator = new StringBuilder();
		Field[] fields = clazz.getDeclaredFields();

		header.append("|");
		separator.append("+");
		for (Field field : fields) {
			if (isAnnotated(field, jakarta.persistence.OneToMany.class) != null
					|| isAnnotated(field, jakarta.persistence.ManyToOne.class) != null
					|| isAnnotated(field, jakarta.persistence.OneToOne.class) != null
					|| isAnnotated(field, jakarta.persistence.ManyToMany.class) != null) {
				continue;
			}
			String fieldName = field.getName();
			header.append(String.format(" %-" + columnWidth + "s |", _T(fieldName, columnWidth)));
			separator.append("-".repeat(columnWidth + 2)).append("+");
		}

		return new String[] { header.toString(), separator.toString() };
	}

	public void log(List<?> list) {
		if (list == null || list.isEmpty()) {
			System.out.println("La lista è vuota o nulla.");
			return;
		}

		int recordCount = 0;
		String[] headerAndSeparator = null;

		for (Object obj : list) {
			if (headerAndSeparator == null || recordCount % headerFrequency == 0) {
				if (recordCount > 0) {
					System.out.println("");
				}
				headerAndSeparator = buildHeaderAndSeparator(obj.getClass());
				System.out.println(headerAndSeparator[1]);
				System.out.println(headerAndSeparator[0]);
				System.out.println(headerAndSeparator[1]);
			}
			System.out.println(formatRow(obj));
			recordCount++;
		}

		System.out.println(headerAndSeparator[1] + "\n");
	}
}
