package SD.Discord.Bot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.esotericsoftware.yamlbeans.YamlReader;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CustomCommandListener extends ListenerAdapter{
	
	@SuppressWarnings("unchecked")
	public void onMessageReceived(MessageReceivedEvent e)
	{
		if ( e.getAuthor().isBot()) return; //ignore bot messages; prevents infinite loop
		if (!(e.getChannel() instanceof TextChannel)) return;
		
		String prefix = Variables.getPrefix();
		ConfigControl configControl = null;
		try {
			configControl = new ConfigControl(new YamlReader(new FileReader("config.yml")), null);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		if (configControl == null) {
			System.out.println("Big error... yikes.");
		}
		List<Object> configObjects = configControl.getConfigObjects();
		List<String> commandList = new ArrayList<String>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		for (Object o : configObjects) {
			if (o instanceof HashMap<?, ?>) {
				map = (HashMap<String, Object>) o;
				for (String key : map.keySet()) {
					if (key.equalsIgnoreCase("commands02779")) {
						commandList = (List<String>) map.get(key);
					}
				}
			}
		}
		for (String command : commandList) {
			if (e.getMessage().getContentRaw().equalsIgnoreCase(prefix + command)) {
				String toSendRaw = (String) map.get(command);
				MessageChannel c = e.getChannel();
				if (toSendRaw.startsWith("##DM##")) {
					c = e.getAuthor().openPrivateChannel().complete();
					toSendRaw = toSendRaw.substring(6);
				}
				String[] toSendLines = toSendRaw.split("&n;");
				for (String toSend : toSendLines) {
					toSend = Variables.replaceWithRoles(toSend, e.getGuild());
					c.sendMessage(toSend).queue();
				}
			}
		}
	}
	
}
