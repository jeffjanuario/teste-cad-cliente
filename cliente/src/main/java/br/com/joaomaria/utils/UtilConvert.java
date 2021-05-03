package br.com.joaomaria.utils;

public class UtilConvert {

	public static Long objToLongZeroSeNull(Object value) {
		if (value == null) {
			return new Long(0);
		}
		return Long.getLong(value.toString());
	}

	public static String objToStringVazioSeNull(Object value) {
		if (value == null) {
			return "";
		}
		return String.valueOf(value);
	}

	public static String formatTelefone(String telefone) {
		String codigoArea = telefone.substring(0, 2);
		String cincoPrimeiros = telefone.substring(2, 7);
		String resto = telefone.substring(7);
		return "(" + codigoArea + ") " + cincoPrimeiros + "-" + resto;
	}
}
