package edu.sjsu.cmpe275.aop.tweet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class TweetStatsServiceImpl implements TweetStatsService {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */

	public static int lengthOfLongestTweet = 0;
	public static int maxFollowCount = 0;
	public static String mostFollowedUser = "";
	public static UUID mostPopularMessage = null;
	public static UUID prevMostPopularMessage = null;
	public static String mostProductiveReplier = "";
	public static int mostProductiveUserTweetLength = 0;
	public static UUID mostLikedMessage = null;
	public static int mostLikedMessageCount = 0;
	public static int mostBlockedByCount = 0;
	public static String mostUnpopularFollower = "";
	public static UUID longestMessageThread = null;
	public static int previousPopularMessage = 0;
	public static int prevLongThread = 0;
	public static HashMap<String, Set<String>> followMap = new HashMap<String, Set<String>>();
	public static HashMap<String, String> tweetOwnerDeets = new HashMap<String, String>();
	public static HashMap<String, Set<String>> blockMap = new HashMap<String, Set<String>>();
	public static HashMap<String, Set<String>> tweetRecieversDeets = new HashMap<String, Set<String>>();
	public static HashMap<String, Set<String>> blockedByMap = new HashMap<String, Set<String>>();
	public static HashMap<String, Set<String>> tweetDB = new HashMap<String, Set<String>>();
	public static HashMap<String, Set<String>> replyDB = new HashMap<String, Set<String>>();
	public static HashMap<UUID, String> tweetOwnerId = new HashMap<UUID, String>();
	public static HashMap<UUID, String> tweetReplierId = new HashMap<UUID, String>();
	public static HashMap<UUID, Set<String>> tweetLiker = new HashMap<UUID, Set<String>>();
	public static HashMap<UUID, Set<UUID>> replyThread = new HashMap<UUID, Set<UUID>>();
	public static HashMap<UUID, UUID> replyThreadA = new HashMap<UUID, UUID>();
	public static HashMap<UUID, Integer> replyThreadD = new HashMap<UUID, Integer>();

	

	@Override
	public void resetStatsAndSystem() {
		// TODO Auto-generated method stub
	lengthOfLongestTweet = 0;
	maxFollowCount = 0;
	mostFollowedUser = "";
	mostPopularMessage = null;
	prevMostPopularMessage = null;
	mostProductiveReplier = "";
	mostProductiveUserTweetLength = 0;
	mostLikedMessage = null;
	mostLikedMessageCount = 0;
	mostBlockedByCount = 0;
	mostUnpopularFollower = "";
	longestMessageThread = null;
	previousPopularMessage = 0;
	prevLongThread = 0;
	followMap.clear();
	tweetOwnerDeets.clear();
	blockMap.clear();
	tweetRecieversDeets.clear();
	blockedByMap.clear();
	tweetDB.clear();
	replyDB.clear();
	tweetOwnerId.clear();
	tweetReplierId.clear();
	tweetLiker.clear();
	replyThread.clear();
	replyThreadA.clear();
	replyThreadD.clear();
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		// TODO Auto-generated method stub
		return lengthOfLongestTweet;
	}

	@Override
	public String getMostFollowedUser() {
		// TODO Auto-generated method stub
		if(mostFollowedUser == ""){
			return null;
		}
		return mostFollowedUser;
	}

	@Override
	public UUID getMostPopularMessage() {
		// TODO Auto-generated method stub
		return mostPopularMessage;
	}
	
	@Override
	public String getMostProductiveReplier() {
		// TODO Auto-generated method stub
		if(mostProductiveReplier == ""){
			return null;
		}
		return mostProductiveReplier;
	}

	@Override
	public UUID getMostLikedMessage() {
		// TODO Auto-generated method stub
		return mostLikedMessage;
	}

	@Override
	public String getMostUnpopularFollower() {
		// TODO Auto-generated method stub
		if(mostUnpopularFollower == ""){
			return null;
		}
		return mostUnpopularFollower;
	}

	@Override
	public UUID getLongestMessageThread() {
		// TODO Auto-generated method stub
		return longestMessageThread;
	}

}



