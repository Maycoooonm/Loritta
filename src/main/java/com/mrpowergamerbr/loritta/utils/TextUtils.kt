package com.mrpowergamerbr.loritta.utils

import java.net.URLEncoder
import java.text.MessageFormat

val morseValues = mapOf(
		// ALFABETO
		'A' to ".-",
		'B' to "-...",
		'C' to "-.-.",
		'D' to "-..",
		'E' to ".",
		'F' to "..-.",
		'G' to "--.",
		'H' to "....",
		'I' to "..",
		'J' to ".---",
		'K' to "-.-",
		'L' to ".-..",
		'M' to "--",
		'N' to "-.",
		'O' to "---",
		'P' to ".--.",
		'Q' to "--.-",
		'R' to ".-.",
		'S' to "...",
		'T' to "-",
		'U' to "..-",
		'V' to "...-",
		'W' to ".--",
		'X' to "-..-",
		'Y' to "-.--",
		'Z' to "--..",

		// NÚMEROS
		'1' to "·----",
		'2' to "··---",
		'3' to "···--",
		'4' to "····-",
		'5' to "·····",
		'6' to "-····",
		'7' to "--···",
		'8' to "---··",
		'9' to "----·",
		'0' to "-----",

		// PONTUAÇÕES COMUNS
		'.' to "·-·-·-",
		',' to "--··--",
		'?' to "··--··",
		'\'' to "·----·",
		'!' to "-·-·--",
		'/' to "-··-·",
		'(' to "-·--·",
		')' to "-·--·-",
		'&' to "·-···",
		':' to "---···",
		';' to "-·-·-·",
		'=' to "-···-",
		'-' to "-····-",
		'_' to "··--·-",
		'"' to "·-··-·",
		'$' to "···-··-",
		'@' to "·--·-·",
		' ' to "/",

		// OUTROS CARACTERES
		'ä' to "·-·-",
		'à' to "·--·-",
		'ç' to "-·-··",
		'ð' to "··--·",
		'è' to "·-··-",
		'é' to "··-··",
		'ĝ' to "--·-·",
		'ĥ' to "-·--·",
		'ĵ' to "·---·",
		'ñ' to "--·--",
		'ö' to "---·",
		'ŝ' to "···-·",
		'þ' to "·--··",
		'ü' to "··--"
)

fun String.stripNewLines(): String {
	return this.replace(Regex("\\r|\\n"), "")
}

fun String.encodeToUrl(enc: String = "UTF-8"): String {
	return URLEncoder.encode(this, enc)
}

fun String.stripCodeMarks(): String {
	return this.replace("`", "")
}

fun String.msgFormat(vararg arguments: Any?): String {
	return MessageFormat.format(this, *arguments);
}

fun String.f(vararg arguments: Any?): String {
	return msgFormat(*arguments)
}

fun String.substringIfNeeded(range: IntRange = 0 until 2000, suffix: String = "..."): String {
	if (this.isEmpty()) {
		return this
	}

	if (this.length - 1 in range)
		return this

	return this.substring(range.start .. range.last - 3) + suffix
}

fun String.convertToSpan(): Long {
	var time = this
	var diff = 0L

	val yearsMatcher = "([0-9]+) ?(y|a)".toPattern().matcher(time)
	if (yearsMatcher.find()) {
		var addYears = yearsMatcher.group(1).toInt()
		diff += (31536000 * addYears)
	}
	val monthMatcher = "([0-9]+) ?(month(s)?|m(e|ê)s(es?))".toPattern().matcher(time)
	if (monthMatcher.find()) {
		var addMonths = monthMatcher.group(1).toInt()
		diff += ((86400 * 30) * addMonths)
	}
	val weekMatcher = "([0-9]+) ?(w)".toPattern().matcher(time)
	if (weekMatcher.find()) {
		var addWeeks = weekMatcher.group(1).toInt()
		diff += (604800 * addWeeks)
	}
	val dayMatcher = "([0-9]+) ?(d)".toPattern().matcher(time)
	if (dayMatcher.find()) {
		var addDays = dayMatcher.group(1).toInt()
		diff += (86400 * addDays)
	}
	val hourMatcher = "([0-9]+) ?(h)".toPattern().matcher(time)
	if (hourMatcher.find()) {
		var addHours = hourMatcher.group(1).toInt()
		diff += (3600 * addHours)
	}
	val minuteMatcher = "([0-9]+) ?(m)".toPattern().matcher(time)
	if (minuteMatcher.find()) {
		var addMinutes = minuteMatcher.group(1).toInt()
		diff += (60 * addMinutes)
	}
	val secondsMatcher = "([0-9]+) ?(s)".toPattern().matcher(time)
	if (secondsMatcher.find()) {
		var addSeconds = secondsMatcher.group(1).toInt()
		diff += (1 * addSeconds)
	}

	return diff
}

fun String.escapeMentions(): String {
	return this.replace(Regex("\\\\+@"), "@").replace("@", "@\u200B")
}

fun String.fromMorse(): String {
	// Criar uma string vazia para guardar a nossa mensagem em texto comum
	var text = "";

	// Separar nossa string em morse em espaços para fazer um for nela
	this.split(" ").forEach { inMorse ->
		// Pegar o valor do char em morse
		val inTextEntry = morseValues.entries.firstOrNull { it.value.equals(inMorse) }

		if (inTextEntry != null) { // E, caso seja diferente de null...
			text += inTextEntry.key // Pegar o nosso valor e colocar na nossa string!
		}
	}
	return text
}

fun String.toMorse(): String {
	// Criar uma string vazia para guardar a nossa mensagem em morse
	var morse = "";

	// Fazer um for na nossa mensagem
	this.toCharArray().forEach { char ->
		// Pegar o valor do char em morse
		val inMorse = morseValues[char]

		if (inMorse != null) { // E, caso seja diferente de null...
			morse += "$inMorse " // Pegar o nosso valor e colocar na nossa string!
		}
	}
	return morse
}