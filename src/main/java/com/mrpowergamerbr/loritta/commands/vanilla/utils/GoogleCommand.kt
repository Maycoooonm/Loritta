package com.mrpowergamerbr.loritta.commands.vanilla.utils

import com.github.kevinsawicki.http.HttpRequest
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Constants
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import net.dv8tion.jda.core.EmbedBuilder
import org.jsoup.Jsoup
import java.awt.Color
import java.io.File
import java.net.URLEncoder


class GoogleCommand : CommandBase("google") {
	override fun getAliases(): List<String> {
		return listOf("g", "search", "procurar", "pesquisar")
	}

	override fun getUsage(): String {
		return "pesquisa"
	}

	override fun getDescription(locale: BaseLocale): String {
		return locale["GOOGLE_Description"]
	}

	override fun getExample(): List<String> {
		return listOf("Loritta");
	}

	override fun getCategory(): CommandCategory {
		return CommandCategory.UTILS;
	}

	override fun run(context: CommandContext) {
		if (context.args.isNotEmpty()) {
			val query = context.args.joinToString(" ");

			val safeSearch = "on"

			val httpRequest = HttpRequest.get("https://www.google.com/search?q=${URLEncoder.encode(query, "UTF-8")}&safe=$safeSearch&lr=lang_en&hl=en&ie=utf-8&oe=utf-8&client=firefox-b&gws_rd=cr&dcr=0&ei=lH4EWvLKAoO8wAS64aho")
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")

			val body = httpRequest.body()

			File("teste.txt").writeText(body)
			val document = Jsoup.parse(body)
			val resultStats = document.getElementById("resultStats").text()
			val elements = document.getElementsByClass("rc")

			val embed = EmbedBuilder().apply {
				setTitle("<:google:378210839171170305> ${context.locale["YOUTUBE_RESULTS_FOR", query]}")
				setColor(Color(21, 101, 192))
				setFooter(resultStats, null)
			}

			for ((idx, el) in elements.withIndex()) {
				if (idx > 4)
					break

				// context.sendMessage("wow")
				val title = el.getElementsByTag("h3").text()
				val url = el.getElementsByTag("h3")[0].child(0).attr("href")
				val description = el.getElementsByClass("st").text()

				embed.appendDescription("${Constants.INDEXES[idx]} [$title]($url)\n◾ $description\n")
			}

			context.sendMessage(context.getAsMention(true), embed.build())
		} else {
			this.explain(context)
		}
	}
}