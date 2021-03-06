package com.mrpowergamerbr.loritta.commands.vanilla.minecraft

import com.github.kevinsawicki.http.HttpRequest
import com.github.salomonbrys.kotson.string
import com.mrpowergamerbr.loritta.commands.CommandBase
import com.mrpowergamerbr.loritta.commands.CommandCategory
import com.mrpowergamerbr.loritta.commands.CommandContext
import com.mrpowergamerbr.loritta.utils.Constants
import com.mrpowergamerbr.loritta.utils.LorittaUtils
import com.mrpowergamerbr.loritta.utils.jsonParser
import com.mrpowergamerbr.loritta.utils.locale.BaseLocale
import com.mrpowergamerbr.loritta.utils.msgFormat
import java.util.*

class McUUIDCommand : CommandBase("mcuuid") {
    override fun getDescription(locale: BaseLocale): String {
        return locale.MCUUID_DESCRIPTION.msgFormat();
    }

    override fun getUsage(): String {
        return "nickname"
    }

    override fun getExample(): List<String> {
        return Arrays.asList("Monerk")
    }

    override fun getCategory(): CommandCategory {
        return CommandCategory.MINECRAFT;
    }

    override fun run(context: CommandContext) {
        if (context.args.size > 0) {
            var player = context.args[0];

            var data = HttpRequest.get("https://api.mojang.com/users/profiles/minecraft/" + player).body();


            try {
                var json = jsonParser.parse(data).asJsonObject;

                context.sendMessage(context.getAsMention(true) + context.locale.MCUUID_RESULT.msgFormat(player, LorittaUtils.getUUID(json["id"].string)))
            } catch (e: IllegalStateException) {
                context.sendMessage(Constants.ERROR + " **|** " + context.getAsMention(true) + context.locale.MCUUID_INVALID.msgFormat(player));
            }
        } else {
            this.explain(context);
        }
    }
}