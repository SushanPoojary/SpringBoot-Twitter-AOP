package edu.sjsu.cmpe275.aop.tweet;

import java.util.Random;
import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	public static void main(String[] args) {
		/***
		 * Following is a dummy implementation of App to demonstrate bean creation with
		 * Application context. You may make changes to suit your need, but this file is
		 * NOT part of the submission.
		 */

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		TweetService tweeter = (TweetService) ctx.getBean("tweetService");
		TweetStatsService stats = (TweetStatsService) ctx.getBean("tweetStatsService");

		try {
			tweeter.tweet("Alice", "TELELELELLELELELE");
			// tweeter.follow("alice", "bob");
			// tweeter.follow("carl", "bob");
			// tweeter.follow("bob", "alice");
			// // // tweeter.follow("bob", "carl");
			// tweeter.follow("abc", "alice");
			// tweeter.follow("carl", "alice");
			// UUID m = tweeter.tweet("alice", "HHHSAHAHSHAHSAHASH");
			// UUID mm = tweeter.tweet("bob", "Ahahahhahahaahahahahahahhahaha");
			// UUID mmm = tweeter.tweet("abc", "TELELELELLELELELE");
			// UUID R = tweeter.reply("carl", mm, "Yuh");
			// tweeter.tweet("foo","barbar");

			// UUID RR = tweeter.reply("bob", R, "yaya");
			// UUID RRR = tweeter.reply("alice", RR, "iukk");
			// UUID RR = tweeter.reply("bob", R, "Yuh");
			// UUID RR = tweeter.reply("bob", m, "WUH");
			// UUID RRR = tweeter.reply("bob", R, "O");
			// tweeter.reply("abc", mm, "yaya");
			// UUID RRR = tweeter.reply("bob", R, "2");
			// UUID RRRR = tweeter.reply("alice", RRR, "LALAL");
			// UUID RRR = tweeter.reply("carl", mm, "LALALALALA");
			// UUID RRRR = tweeter.reply("bob", R, "LA");
			// UUID RRRRR = tweeter.reply("bob", R, "LALALA");

			// tweeter.like("alice", mm);
			// tweeter.like("carl", mm);
			// tweeter.like("alice", RR);
			// tweeter.like("alice", RR);
			// tweeter.like("alice", RRR);
			// tweeter.like("alice", RR);
			// tweeter.like("carl", mm);
			// tweeter.like("carl", RRRRR);
			// tweeter.block("alice", "abc");
			// tweeter.block("alice", "carl");
			// // tweeter.block("bob", "abc");
			// tweeter.block("bob", "carl");
			// tweeter.reply("bob", R, "aja");
			// tweeter.reply("abc", mm, "yaya");
			// tweeter.like("")
			// tweeter.like("Carl", mm);
			// tweeter.follow("XYZ", "bob");
			// tweeter.follow("ABC", "alice");
			// tweeter.follow("alice", "bob");
			
			// UUID msg = tweeter.tweet("alice", "first tweet");
			// UUID msg2 = tweeter.tweet("bob", "YAYAYA");
			// tweeter.reply("XYZ", msg, "Block ho");
			// tweeter.reply("XYZ", msg, "Sachi!");
			// tweeter.block("bob", "XYZ");
			// tweeter.reply("XYZ", msg, "Sachisssss s s s s s s s ads da sd a sd as d asd a s!");
			// UUID reply1 = tweeter.reply("alice", msg, "Yay");
			// tweeter.reply("alice", msg , "");
			// UUID msg1 = tweeter.tweet("alice", "");
			// UUID msg2 = tweeter.tweet("alice", "123hasbdahjbdhajsbdhasjbdhjasbdhjasbdhjasbdhajsbdhjasbdhjasbdhjasbdhjasbdhjasbdjhasbdjhasbdjhasbdjhasbdjhasbdhjasbdjhasbdhjasbdhjasbdhjasbdhjasbdjhasbdjhasbdjhasbdjhasbdhjasbdjhasbdjhasbdjhasbdhjavsdhjgavshjdbvashjdbajshdvbahjsvdajhsvbdjhasvbdjhasvdhjasvbdjhabdhasbdjhabdjhasvdhjasvdhjasvdjhasvdhjasvdjhasbvdjhasvdjhasd");
			// tweeter.block("bob", "alice");
			// UUID reply=tweeter.reply("bob", msg, "that was brilliant");
			
			// UUID reply5=tweeter.reply("alice", msg, "post b");
			// UUID R = tweeter.reply("XYZ", msg2, "dasdasda");
			// UUID reply3=tweeter.reply("bob", R, "dsa");

			// tweeter.reply("XYZ", reply, "Block mat ho");
			
			// tweeter.reply("XYZ", reply, "Sachi!");
			// UUID msg1 = tweeter.tweet("ABC", "Second First Tweet");
			
			// tweeter.like("bob", msg);
			// tweeter.like("bob", msg);
			// tweeter.like("XYZ", msg);
			// tweeter.like("bob", msg);
			// tweeter.like("a", msg);
			// UUID msg1 = tweeter.tweet("ABC", "Pehla");
			

			// tweeter.like("b", msg1);
			// UUID ms = tweeter.tweet("XYZ", "allalalalallalalalallalalalallallalalala");
			// tweeter.reply("ABC", ms, "YAYAYAYAYAYAYYAYAYAYAYAYYAYAYAYAYAYAYAYYAYAYAYAYAYAYAYAY");
			// UUID reply2 = tweeter.reply("alice", reply, "no comments!");
			// UUID reply1 = tweeter.reply("XYZ", msg1, "Mast");
			
			// UUID s = tweeter.tweet("bob", "Unblock?");
			// tweeter.block("bob", "XYZ");
			// tweeter.like("XYZ", msg);
			// tweeter.reply("A", msg, "NP!");
			// tweeter.block("alice", "bob");
			// tweeter.block("XYZ", "ABC");
			// tweeter.block("alice", "ABC");
			// UUID msg2 = tweeter.tweet("alice", "second tweet");
			// UUID reply3 = tweeter.reply("bob", reply2, "Post block");
			// tweeter.reply("ABC", msg1, "Block Reply");
			// tweeter.tweet("ABC", "SS SS Tweet");
			// UUID reply11 = tweeter.reply("ABC", reply1, "Lalalal");
			// UUID reply12 = tweeter.reply("XYZ", reply11, "YAYAAYYA");
			// tweeter.reply("ABC", reply12, "bas");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Most productive user: " + stats.getMostProductiveReplier());
		System.out.println("Most popular user: " + stats.getMostFollowedUser());
		System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
		System.out.println("Most popular message: " + stats.getMostPopularMessage());
		System.out.println("Most liked message: " + stats.getMostLikedMessage());
		System.out.println("Most most message: " + stats.getMostPopularMessage());
		System.out.println("Most unpopular follower: " + stats.getMostUnpopularFollower());
		System.out.println("Longest message thread: " + stats.getLongestMessageThread());
		ctx.close();
	}
}
