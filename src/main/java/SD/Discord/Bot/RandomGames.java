package SD.Discord.Bot;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RandomGames extends ListenerAdapter{
	
	List<String> topicList = new ArrayList<String>();

	public void onMessageReceived(MessageReceivedEvent e)
	{
		//bot messages are ignored
		
		if ( e.getAuthor().isBot()) return;
		System.out.println(e.getMessage().getContentRaw());
		
		Random r = new Random();
		
		if ( e.getMessage().getContentRaw().contains("!dice"))
		{
			e.getChannel().sendMessage("The dice value was " + (r.nextInt(6) + 1)).queue();
		}
		
		if ( e.getMessage().getContentRaw().contains("!coin"))
		{	
			if ( r.nextInt(2) == 0)
			{
				e.getChannel().sendMessage("The coin was heads").queue();
			}
			else
			{
				e.getChannel().sendMessage("The coin was tails").queue();
			}
			
		}
		
		if ( e.getMessage().getContentRaw().contains("!topic"))
		{
			readFile("SwagDragonsTopicList.txt");
			System.out.println(topicList.toString());
		}
	}
	
	/**
	 * Open and read a file, and return the lines in the file as a list
	 * of Strings.
	 * (Demonstrates Java FileReader, BufferedReader, and Java5.)
	 */
	private List<String> readFile(String fileName)
	{
	  List<String> topicList = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(fileName));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      topicList.add(line);
	    }
	    reader.close();
	    return topicList;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", fileName);
	    e.printStackTrace();
	    return null;
	  }
	}
	
}







	
	