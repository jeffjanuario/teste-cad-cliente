package br.com.joaomaria.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UtilData {
	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
}
