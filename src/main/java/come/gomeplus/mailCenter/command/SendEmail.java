package come.gomeplus.mailCenter.command;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import come.gomeplus.mailCenter.tool.EmailTool;

public class SendEmail {
	
	private static Logger log = LogManager.getLogger(SendEmail.class);

	public static void main(String[] args) {
		Options opts = new Options();
		opts.addOption("proto", true, "protocal. e.g. smtp");
		opts.addOption("host", true, "host");
		opts.addOption("user", true, "username");
		opts.addOption("pwd", true, "password");
		opts.addOption("from", true, "from email");
		opts.addOption("to", true, "comma seperated email list to send");
		opts.addOption("cc", true, "comma seperated email list to cc");
		opts.addOption("bcc", true, "comma seperated email list to bcc");
		opts.addOption("subject", true, "email subject");
		opts.addOption("content", true, "email content");
		opts.addOption("attach", true, "comma seperated attachement list to send");
		
		opts.addOption("h", "help",  false, "print help for the command.");
		
		String format = "email [-proto][-host][-user][-pwd][-from][-to][-cc][-bcc][-attach][-subject][-content]";
		HelpFormatter formatter = new HelpFormatter();
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cli = null;
		
		try {
			cli = parser.parse(opts, args);
		} catch (ParseException e) {
			formatter.printHelp(format, opts);
		}
		
		if(cli.hasOption("h")){
			formatter.printHelp(format, opts);
			return ;
		}
		
		String proto = null;
		String host = null;
		String user = null;
		String pwd = null;
		String from = null;
		String to = null;
		String cc = null;
		String bcc = null;
		String attach = null;
		String subject = null;
		String content = null;
		
		if(cli.hasOption("proto")){
			proto = cli.getOptionValue("proto");
		}
		
		if(cli.hasOption("host")){
			host = cli.getOptionValue("host");
		}
		
		if(cli.hasOption("user")){
			user = cli.getOptionValue("user");
		}
		
		if(cli.hasOption("pwd")){
			pwd = cli.getOptionValue("pwd");
		}
		
		if(cli.hasOption("from")){
			from = cli.getOptionValue("from");
		}
		
		if(cli.hasOption("to")){
			to = cli.getOptionValue("to");
		}
		
		if(cli.hasOption("cc")){
			cc = cli.getOptionValue("cc");
		}
		
		if(cli.hasOption("bcc")){
			bcc = cli.getOptionValue("bcc");
		}
		
		if(cli.hasOption("attach")){
			attach = cli.getOptionValue("attach");
		}
		
		if(cli.hasOption("subject")){
			subject = cli.getOptionValue("subject");
		}
		
		if(cli.hasOption("content")){
			content = cli.getOptionValue("content");
		}
		
		String[] toList = null;
		if(to != null && !to.trim().equals("")){
			toList = to.split(",");
		}
		
		String[] ccList = null;
		if(cc != null && !cc.trim().equals("")){
			ccList = cc.split(",");
		}
		
		String[] bccList = null;
		if(bcc != null && !bcc.trim().equals("")){
			bccList = bcc.split(",");
		}
		
		String[] attList = null;
		if(attach != null && !attach.trim().equals("")){
			attList = attach.split(",");
		}
		
		boolean result = EmailTool.sendEmail(
				proto, 
				host, 
				user, 
				pwd, 
				from, 
				toList, 
				ccList, 
				bccList, 
				subject, 
				content, 
				attList
		);
		if(result){
			log.info("Email send success.");
		}else{
			log.info("Email send failure.");
		}
	}
	
}
