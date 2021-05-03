package br.com.joaomaria.utils;

public class UtilString {
	public static String removeMascara(String string) {
		return string.replaceAll("\\D", ""); 
	}
}
