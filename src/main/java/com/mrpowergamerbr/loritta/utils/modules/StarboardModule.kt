package com.mrpowergamerbr.loritta.utils.modules

import com.mrpowergamerbr.loritta.userdata.ServerConfig
import com.mrpowergamerbr.loritta.utils.humanize
import com.mrpowergamerbr.loritta.utils.loritta
import com.mrpowergamerbr.loritta.utils.save
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent
import java.awt.Color

object StarboardModule {
	fun handleStarboardReaction(e: GenericMessageReactionEvent, serverConfig: ServerConfig) {
		val guild = e.guild
		val starboardConfig = serverConfig.starboardConfig

		if (e.reactionEmote.name == "⭐") {
			val msg = e.textChannel.getMessageById(e.messageId).complete()
			if (msg != null) {
				val textChannel = guild.getTextChannelById(starboardConfig.starboardId)

				if (textChannel != null && msg.textChannel != textChannel) { // Verificar se não é null e verificar se a reaction não foi na starboard
					var starboardMessageId = serverConfig.starboardEmbeds[e.messageId]
					var starboardMessage: Message? = null
					if (starboardMessageId != null) {
						starboardMessage = textChannel.getMessageById(starboardMessageId).complete()
					}

					val embed = EmbedBuilder()
					val count = e.reaction.users.complete().size
					var content = msg.rawContent
					embed.setAuthor(msg.author.name, null, msg.author.effectiveAvatarUrl)
					embed.setFooter(msg.creationTime.humanize(), null)
					embed.setColor(Color(255, 255, Math.max(200 - (count * 20), 0)))

					var emoji = "⭐"

					if (count >= 5) {
						emoji = "\uD83C\uDF1F"
					}
					if (count >= 10) {
						emoji = "\uD83C\uDF20"
					}
					if (count >= 15) {
						emoji = "\uD83D\uDCAB"
					}
					if (count >= 20) {
						emoji = "\uD83C\uDF0C"
					}

					var hasImage = false
					if (msg.attachments.isNotEmpty()) { // Se tem attachments...
						content += "\n**Arquivos:**\n"
						for (attach in msg.attachments) {
							if (attach.isImage && !hasImage) { // Se é uma imagem...
								embed.setImage(attach.url) // Então coloque isso como a imagem no embed!
								hasImage = true
							}
							content += attach.url + "\n"
						}
					}

					embed.setDescription(content)

					val starCountMessage = MessageBuilder()
					starCountMessage.append("$emoji **${count}** ${e.textChannel.asMention}")
					starCountMessage.setEmbed(embed.build())

					if (starboardMessage != null) {
						if (starboardConfig.requiredStars > count) { // Remover embed já que o número de stars é menos que 0
							starboardMessage.delete().complete()
							serverConfig.starboardEmbeds.remove(msg.id)
							loritta save serverConfig
							return
						}
						starboardMessage.editMessage(starCountMessage.build()).complete()
					} else if (count >= starboardConfig.requiredStars) {
						starboardMessage = textChannel.sendMessage(starCountMessage.build()).complete()
					}
					serverConfig.starboardEmbeds.put(msg.id, starboardMessage?.id)
					loritta save serverConfig
				}
			}
		}
	}
}