package com.mrpowergamerbr.loritta.commands.vanilla.misc

import com.mrpowergamerbr.loritta.LorittaLauncher
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Constants
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.loritta
import com.mrpowergamerbr.loritta.utils.msgFormat
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.entities.Message
import net.dv8tion.jda.core.entities.MessageEmbed
import net.dv8tion.jda.core.entities.PrivateChannel
import net.dv8tion.jda.core.events.message.react.GenericMessageReactionEvent
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.core.exceptions.ErrorResponseException
import java.awt.Color
import java.util.stream.Collectors

class AjudaCommand : CommandBase("ajuda") {
	override fun getDescription(locale: BaseLocale): String {
		return locale["AJUDA_DESCRIPTION"]
	}

	override fun getAliases(): List<String> {
		return listOf("help", "comandos")
	}

	override fun run(context: CommandContext) {
		try {
			val privateChannel = context.userHandle.openPrivateChannel().complete()

			if (!context.isPrivateChannel) {
				context.event.textChannel.sendMessage(context.getAsMention(true) + "${context.locale.AJUDA_SENT_IN_PRIVATE.msgFormat()} \uD83D\uDE09").complete()
			}

			if (true) {
				/* val past = privateChannel.history.retrievePast(100).complete()
				past.filter { it.author.id == Loritta.config.clientId && it.embeds.isNotEmpty() }
					.forEach { it.delete().queue() } */
				var description = context.locale.get(
						"AJUDA_INTRODUCE_MYSELF",
						context.userHandle.asMention,
						"https://discordapp.com/oauth2/authorize?client_id=297153970613387264&scope=bot&permissions=2080374975",
						context?.guild?.name ?: "\uD83E\uDD37"
				)

				var builder = EmbedBuilder()
						.setColor(Color(0, 193, 223))
						.setTitle("💁 ${context.locale.get("AJUDA_MY_HELP")}")
						.setDescription(description)
						.setThumbnail("http://loritta.website/assets/img/loritta_guild_v4.png")

				privateChannel.sendMessage(builder.build()).complete()

				sendInfoBox(context, privateChannel)
				return
			}

			var description = context.locale.get(
					"AJUDA_INTRODUCE_MYSELF",
					context.userHandle.asMention,
					"https://discordapp.com/oauth2/authorize?client_id=297153970613387264&scope=bot&permissions=2080374975",
					context?.guild?.name ?: "\uD83E\uDD37"
			)

			var builder = EmbedBuilder()
					.setColor(Color(0, 193, 223))
					.setTitle("💁 ${context.locale.get("AJUDA_MY_HELP")}")
					.setDescription(description)
					.setThumbnail("http://loritta.website/assets/img/loritta_guild_v4.png")

			privateChannel.sendMessage(builder.build()).complete()

			val disabledCommands = loritta.commandManager.getCommandsDisabledIn(context.config)

			val adminCmds = getCommandsFor(context, disabledCommands, CommandCategory.ADMIN, "http://i.imgur.com/Ql6EiAw.png")
			val socialCmds = getCommandsFor(context, disabledCommands, CommandCategory.SOCIAL, "https://loritta.website/assets/img/social.png")
			val discordCmds = getCommandsFor(context, disabledCommands, CommandCategory.DISCORD, "https://lh3.googleusercontent.com/_4zBNFjA8S9yjNB_ONwqBvxTvyXYdC7Nh1jYZ2x6YEcldBr2fyijdjM2J5EoVdTpnkA=w300")
			val minecraftCmds = getCommandsFor(context, disabledCommands, CommandCategory.MINECRAFT, "https://loritta.website/assets/img/loritta_pudim.png")
			val undertaleCmds = getCommandsFor(context, disabledCommands, CommandCategory.UNDERTALE, "http://vignette2.wikia.nocookie.net/animal-jam-clans-1/images/0/08/Annoying_dog_101.gif/revision/latest?cb=20151231033006")
			val pokemonCmds = getCommandsFor(context, disabledCommands, CommandCategory.POKEMON, "https://loritta.website/assets/img/pokemon.png")
			val robloxCmds = getCommandsFor(context, disabledCommands, CommandCategory.ROBLOX, "https://media.discordapp.net/attachments/297732013006389252/352269723385462787/download.png")
			val musicCmds = getCommandsFor(context, disabledCommands, CommandCategory.MUSIC, "https://loritta.website/assets/img/loritta_headset.png")
			val funCmds = getCommandsFor(context, disabledCommands, CommandCategory.FUN, "https://loritta.website/assets/img/vieirinha.png")
			val imagesCmds = getCommandsFor(context, disabledCommands, CommandCategory.IMAGES, "http://i.imgur.com/ssNe7dx.png")
			val miscCmds = getCommandsFor(context, disabledCommands, CommandCategory.MISC, "http://i.imgur.com/Qs8MyFy.png")
			val utilsCmds = getCommandsFor(context, disabledCommands, CommandCategory.UTILS, "https://loritta.website/assets/img/utils.png")

			val additionalInfoEmbed = EmbedBuilder()
			additionalInfoEmbed.setTitle("Informações Adicionais", null)
					.setColor(Color(0, 193, 223))
			additionalInfoEmbed.setDescription("[Todos os comandos da Loritta](https://loritta.website/comandos)\n"
					+ "[Discord da nossa querida Loritta](https://discord.gg/V7Kbh4z)\n"
					+ "[Adicione a Loritta no seu servidor!](https://loritta.website/auth)\n"
					+ "[Amou a Loritta? Tem dinheirinho de sobra? Então doe!](https://loritta.website/donate)\n"
					+ "[Website do MrPowerGamerBR](https://mrpowergamerbr.com/)")

			if (adminCmds != null) {
				fastEmbedSend(context, adminCmds);
			}
			if (socialCmds != null) {
				fastEmbedSend(context, socialCmds);
			}
			if (discordCmds != null) {
				fastEmbedSend(context, discordCmds);
			}
			if (minecraftCmds != null) {
				fastEmbedSend(context, minecraftCmds);
			}
			if (undertaleCmds != null) {
				fastEmbedSend(context, undertaleCmds);
			}
			if (pokemonCmds != null) {
				fastEmbedSend(context, pokemonCmds);
			}
			if (robloxCmds != null) {
				fastEmbedSend(context, robloxCmds);
			}
			if (musicCmds != null) {
				fastEmbedSend(context, musicCmds);
			}
			if (funCmds != null) {
				fastEmbedSend(context, funCmds);
			}
			if (imagesCmds != null) {
				fastEmbedSend(context, imagesCmds);
			}
			if (miscCmds != null) {
				fastEmbedSend(context, miscCmds);
			}
			if (utilsCmds != null) {
				fastEmbedSend(context, utilsCmds);
			}

			context.sendMessage(additionalInfoEmbed.build())
		} catch (e: ErrorResponseException) {
			if (e.errorResponse.code == 50007) { // Usuário tem as DMs desativadas
				context.event.textChannel.sendMessage(Constants.ERROR + " **|** ${context.getAsMention(true)}" + context.locale["AJUDA_ERROR_WHEN_OPENING_DM"]).complete()
				return
			}
			throw e
		}
	}

	/**
	 * Envia uma embed com imagens de uma maneira mais rápido
	 *
	 * Para fazer isto, nós enviamos uma embed sem imagens e depois editamos com as imagens, já que o Discord "escaneia" as
	 * imagens antes de enviar para o destinatário... usando o "truque" o usuário irá receber sem as imagens e depois irá receber
	 * a versão editada com imagens, economizando tempo ao tentar enviar várias embeds de uma só vez
	 */
	fun fastEmbedSend(context: CommandContext, embeds: List<MessageEmbed>): List<Message> {
		var messages = ArrayList<Message>();
		for (embed in embeds) {
			var clone = EmbedBuilder(embed)
			clone.setImage(null)
			clone.setThumbnail(null)
			var sentMsg = context.sendMessage(clone.build())
			sentMsg.editMessage(embed).queue(); // Vamos enviar em uma queue para não atrasar o envio
			messages.add(sentMsg);
		}
		return messages;
	}

	fun getCommandsFor(context: CommandContext, availableCommands: List<CommandBase>, cat: CommandCategory, image: String): MutableList<MessageEmbed> {
		val embeds = ArrayList<MessageEmbed>();
		var embed = EmbedBuilder()
		embed.setTitle(cat.fancyTitle, null)
		embed.setThumbnail(image)
		val conf = context.config

		var color = when (cat) {
			CommandCategory.DISCORD -> Color(121, 141, 207)
			CommandCategory.SOCIAL -> Color(231, 150, 90)
			CommandCategory.UNDERTALE -> Color(250, 250, 250)
			CommandCategory.POKEMON -> Color(255, 13, 0)
			CommandCategory.MINECRAFT -> Color(50, 141, 145)
			CommandCategory.ROBLOX -> Color(226, 35, 26)
			CommandCategory.MISC -> Color(255, 176, 0)
			CommandCategory.UTILS -> Color(176, 146, 209)
			CommandCategory.MUSIC -> Color(124, 91, 197)
			else -> Color(186, 0, 239)
		}

		embed.setColor(color)

		var description = "*" + cat.description + "*\n\n";
		val categoryCmds = LorittaLauncher.getInstance().commandManager.commandMap.stream().filter { cmd -> cmd.getCategory() == cat }.collect(Collectors.toList<CommandBase>())

		if (!categoryCmds.isEmpty()) {
			for (cmd in categoryCmds) {
				if (!conf.disabledCommands.contains(cmd.javaClass.simpleName)) {
					var toBeAdded = "[" + conf.commandPrefix + cmd.label + "]()" + (if (cmd.getUsage() != null) " `" + cmd.getUsage() + "`" else "") + " - " + cmd.getDescription(context) + "\n";
					if ((description + toBeAdded).length > 2048) {
						embed.setDescription(description);
						embeds.add(embed.build());
						embed = EmbedBuilder();
						embed.setColor(color)
						description = "";
					}
					description += "[" + conf.commandPrefix + cmd.label + "]()" + (if (cmd.getUsage() != null) " `" + cmd.getUsage() + "`" else "") + " - " + cmd.getDescription(context) + "\n";
				}
			}
			embed.setDescription(description)
			embeds.add(embed.build());
			return embeds
		} else {
			return embeds
		}
	}

	fun _getCommandsFor(context: CommandContext, cat: CommandCategory): MutableList<MessageEmbed> {
		val embeds = ArrayList<MessageEmbed>();
		var embed = EmbedBuilder()
		embed.setTitle(cat.fancyTitle, null)
		val conf = context.config

		var color = when (cat) {
			CommandCategory.DISCORD -> Color(121, 141, 207)
			CommandCategory.SOCIAL -> Color(231, 150, 90)
			CommandCategory.UNDERTALE -> Color(250, 250, 250)
			CommandCategory.POKEMON -> Color(255, 13, 0)
			CommandCategory.MINECRAFT -> Color(50, 141, 145)
			CommandCategory.ROBLOX -> Color(226, 35, 26)
			CommandCategory.MISC -> Color(255, 176, 0)
			CommandCategory.UTILS -> Color(176, 146, 209)
			CommandCategory.MUSIC -> Color(124, 91, 197)
			else -> Color(186, 0, 239)
		}

		var image = when (cat) {
			// CommandCategory.DISCORD -> "http://loritta.website/assets/img/loritta_guild_v4.png"
			CommandCategory.SOCIAL -> "https://loritta.website/assets/img/social.png"
			// CommandCategory.UNDERTALE -> "http://loritta.website/assets/img/loritta_guild_v4.png"
			CommandCategory.POKEMON -> "https://loritta.website/assets/img/pokemon.png"
			CommandCategory.MINECRAFT -> "https://loritta.website/assets/img/loritta_pudim.png"
			CommandCategory.FUN -> "https://loritta.website/assets/img/vieirinha.png"
			// CommandCategory.ROBLOX -> "http://loritta.website/assets/img/loritta_guild_v4.png"
			// CommandCategory.MISC -> "http://loritta.website/assets/img/loritta_guild_v4.png"
			CommandCategory.UTILS -> "https://loritta.website/assets/img/utils.png"
			CommandCategory.MUSIC -> "https://loritta.website/assets/img/loritta_headset.png"
			else -> "http://loritta.website/assets/img/loritta_guild_v4.png"
		}

		embed.setColor(color)
		embed.setThumbnail(image)

		var description = "*" + cat.description + "*\n\n";
		val categoryCmds = LorittaLauncher.getInstance().commandManager.commandMap.stream().filter { cmd -> cmd.getCategory() == cat }.collect(Collectors.toList<CommandBase>())

		if (!categoryCmds.isEmpty()) {
			for (cmd in categoryCmds) {
				if (!conf.disabledCommands.contains(cmd.javaClass.simpleName)) {
					var toBeAdded = "[" + conf.commandPrefix + cmd.label + "]()" + (if (cmd.getUsage() != null) " `" + cmd.getUsage() + "`" else "") + " - " + cmd.getDescription(context) + "\n";
					if ((description + toBeAdded).length > 2048) {
						embed.setDescription(description);
						embeds.add(embed.build());
						embed = EmbedBuilder();
						embed.setColor(color)
						description = "";
					}
					description += "[" + conf.commandPrefix + cmd.label + "]()" + (if (cmd.getUsage() != null) " `" + cmd.getUsage() + "`" else "") + " - " + cmd.getDescription(context) + "\n";
				}
			}
			embed.setDescription(description)
			embeds.add(embed.build());
			return embeds
		} else {
			return embeds
		}
	}

	fun sendInfoBox(context: CommandContext, privateChannel: PrivateChannel) {
		val disabledCommands = loritta.commandManager.getCommandsDisabledIn(context.config)
		var description = "Escolha uma categoria...\n\n"

		val categories = CommandCategory.values().filter { it != CommandCategory.MAGIC }
		val reactionEmotes = mapOf<CommandCategory, String>(
				CommandCategory.DISCORD to "discord:375448103517552642",
				CommandCategory.ROBLOX to "roblox:375313891925688331",
				CommandCategory.UNDERTALE to "undertale_heart:343839169719697408",
				CommandCategory.POKEMON to "pokeball:343837491905691648",
				CommandCategory.MINECRAFT to "grass:330435576392318978",
				CommandCategory.SOCIAL to "blobBlush2:375602225940267018",
				CommandCategory.FUN to "vieirinha:339905091425271820",
				CommandCategory.ADMIN to "\uD83D\uDC6E",
				CommandCategory.IMAGES to "\uD83C\uDFA8",
				CommandCategory.MUSIC to "\uD83C\uDFA7",
				CommandCategory.UTILS to "\uD83D\uDD27",
				CommandCategory.MISC to "\uD83D\uDDC3"
		)

		for (category in categories) {
			val cmdCountInCategory = loritta.commandManager.commandMap.filter { it.getCategory() == category && !disabledCommands.contains(it) }.count()
			val reactionEmote = reactionEmotes.getOrDefault(category, "loritta:331179879582269451")
			val emoji = if (reactionEmote.contains(":")) { "<:$reactionEmote>" } else { reactionEmote }
			val commands = if (cmdCountInCategory == 1) "comando" else "comandos"
			description += "$emoji **" + category.fancyTitle + "** ($cmdCountInCategory $commands)\n"
		}

		val embed = EmbedBuilder().apply {
			setDescription(description)
		}

		val message = privateChannel.sendMessage(embed.build()).complete()
		if (!context.metadata.containsKey("guildId") && !context.isPrivateChannel) {
			context.metadata["guildId"] = context.guild.id
		}
		loritta.messageContextCache[message.id] = context

		for (category in categories) {
			val reactionEmote = reactionEmotes.getOrDefault(category, "loritta:331179879582269451")
			message.addReaction(reactionEmote).complete()
		}
	}

	override fun onCommandReactionFeedback(context: CommandContext, e: GenericMessageReactionEvent, msg: Message) {
		if (e.user.id != context.userHandle.id)
			return

		if (e !is MessageReactionAddEvent)
			return

		println("Processing reaction for " + e.user.name + "...")
		msg.delete().complete()

		if (e.reactionEmote.name == "\uD83D\uDD19") {
			sendInfoBox(context, msg.privateChannel)
		}

		val reactionEmotes = mapOf<CommandCategory, String>(
				CommandCategory.DISCORD to "discord",
				CommandCategory.ROBLOX to "roblox",
				CommandCategory.UNDERTALE to "undertale_heart",
				CommandCategory.POKEMON to "pokeball",
				CommandCategory.MINECRAFT to "grass",
				CommandCategory.SOCIAL to "blobBlush2",
				CommandCategory.FUN to "vieirinha",
				CommandCategory.ADMIN to "\uD83D\uDC6E",
				CommandCategory.IMAGES to "\uD83C\uDFA8",
				CommandCategory.MUSIC to "\uD83C\uDFA7",
				CommandCategory.UTILS to "\uD83D\uDD27",
				CommandCategory.MISC to "\uD83D\uDDC3"
		)

		val entry = reactionEmotes.entries.firstOrNull { it.value ==  e.reactionEmote.name }
		if (entry != null) {
			val embeds = _getCommandsFor(context, entry.key)[0]
			val message = context.sendMessage(embeds)
			message.addReaction("\uD83D\uDD19").complete()
			loritta.messageContextCache[message.id] = context
		}
	}

	companion object {
		val SEND_IN_PRIVATE = "enviarNoPrivado"
		val TELL_SENT_IN_PRIVATE = "avisarQueFoiEnviadoNoPrivado"
	}
}