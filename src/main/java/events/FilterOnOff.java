package events;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class FilterOnOff extends ListenerAdapter{
	public static boolean filterOn = true;
	
	public void onMessageReceived(MessageReceivedEvent OnOff) {
		
		if(OnOff.getMessage().getContentRaw().contentEquals("!togglefilter") && filterOn) {
			OnOff.getChannel().sendMessage("The curse filter has been disables by: " + OnOff.getAuthor().getAsMention()).queue();
			filterOn = false;
		}
		else if(OnOff.getMessage().getContentRaw().contentEquals("!togglefilter") && !filterOn) {
			OnOff.getChannel().sendMessage("The curse filter has been enable by: " + OnOff.getAuthor().getAsMention()).queue();
			filterOn = true;
		}
	}
}