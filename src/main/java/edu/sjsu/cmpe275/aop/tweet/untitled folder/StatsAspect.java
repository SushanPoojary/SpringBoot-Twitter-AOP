package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

@Aspect
@Order(2)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	
	// @After("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	// public void dummyAfterAdvice(JoinPoint joinPoint) {
	// 	System.out.printf("After the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// 	//stats.resetStats();
	// }
	
	// @Before("execution(public void edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	// public void dummyBeforeAdvice(JoinPoint joinPoint) {
	// 	System.out.printf("Before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// }

	@Autowired TweetStatsServiceImpl stats;


	@AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))", returning = "result")
	public void tweetStats(JoinPoint joinPoint, UUID result) {
		String user = joinPoint.getArgs()[0].toString();
		String tweet = joinPoint.getArgs()[1].toString();
		// System.out.println("tweet: " + tweet + " user: " + user);
		Set<String> userFollowers = stats.followMap.get(user);
		Set<String> userBlocked = stats.blockMap.get(user);
		// System.out.println(userFollowers);
		if (userBlocked != null) {
			userFollowers.removeAll(userBlocked);
		}
		if (tweet.length() > stats.lengthOfLongestTweet) {
			stats.lengthOfLongestTweet = tweet.length();
		}
		if (!stats.tweetOwnerDeets.containsKey(tweet)) {
			stats.tweetOwnerDeets.put(tweet, user);
			if (userFollowers != null) {
				stats.tweetRecieversDeets.put(tweet, userFollowers);
			} else {
				stats.tweetRecieversDeets.put(tweet, null);
			}
		} else {
			if (userFollowers != null) {
				if (stats.tweetRecieversDeets.get(tweet) == null) {
					stats.tweetRecieversDeets.put(tweet, userFollowers);
					stats.tweetRecieversDeets.get(tweet).add(user);
				} else {
					stats.tweetRecieversDeets.get(tweet).addAll(userFollowers);
				}
			}
		}
		if (!stats.tweetOwnerId.containsKey((UUID) result)) {
			stats.tweetOwnerId.put((UUID) result, user);
		}
		if (stats.tweetRecieversDeets.get(tweet) != null) {
			if (stats.tweetRecieversDeets.get(tweet).size() >= stats.previousPopularMessage) {
				if (stats.tweetRecieversDeets.get(tweet).size() == stats.previousPopularMessage){
					if(stats.mostPopularMessage.compareTo((UUID)result) >=0 ) {
						stats.mostPopularMessage = (UUID)result;
						stats.prevMostPopularMessage = (UUID)result;
					}
				} else {
					stats.mostPopularMessage = (UUID)result;
					stats.prevMostPopularMessage = (UUID)result;
					stats.previousPopularMessage = stats.tweetRecieversDeets.get(tweet).size();
				}

			}
		}
		if (!stats.tweetDB.containsKey(user)) {
			Set<String> tweets = new HashSet<String>();
			tweets.add(tweet);
			stats.tweetDB.put(user, tweets);
		} else {
			stats.tweetDB.get(user).add(tweet);
		}

		
		if(!stats.replyThreadD.containsKey(result)){
			// if(!stats.replyThread.containsValue(value))
			int rep = 1;
			stats.replyThreadD.put(result, rep);
			// System.out.println("ALALALAAAA"+stats.replyThreadD);
		} else{
			
			stats.replyThreadD.put(result, stats.replyThreadD.get(result)+1);
			
			// System.out.println("DEPTH" + stats.replyThreadD.get(og).intValue()); 
		}

		
		int curRep = stats.replyThreadD.get(result);
		if(curRep > 0){
			if(curRep >= stats.prevLongThread){
				if(curRep == stats.prevLongThread){
				if(stats.longestMessageThread.compareTo((UUID)result) >= 0 ) {
					stats.longestMessageThread = (UUID)result;
					stats.prevLongThread = curRep;
				}
			} else {
				stats.longestMessageThread = (UUID) result;
				stats.prevLongThread = curRep;
			}
			}
		}
		// int totalLengthOfTweets = 0;
		// for (String twts : stats.tweetDB.get(user)) {
		// 	totalLengthOfTweets += twts.length();
		// }
		// if (totalLengthOfTweets >= stats.mostProductiveUserTweetLength) {
		// 	if (totalLengthOfTweets == stats.mostProductiveUserTweetLength) {
		// 		if (stats.mostProductiveReplier == null) {
		// 			stats.mostProductiveReplier = user;
		// 		} else if (stats.mostProductiveReplier.compareTo(user) > 0) {
		// 			stats.mostProductiveReplier = user;
		// 		}
		// 	} else if (totalLengthOfTweets > stats.mostProductiveUserTweetLength) {
		// 		stats.mostProductiveReplier = user;
		// 		stats.mostProductiveUserTweetLength = totalLengthOfTweets;
		// 	}
		// }
	}

	@AfterReturning(pointcut = "execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.reply(..))", returning = "result")
	public void replyStats(JoinPoint joinPoint, UUID result) {
		// System.out.println("Trigger");
		String user = joinPoint.getArgs()[0].toString();
		UUID og = (UUID)joinPoint.getArgs()[1];
		String tweet = joinPoint.getArgs()[2].toString();
		// System.out.println("tweet: " + tweet + " user: " + user);
		Set<String> userFollowers = stats.followMap.get(user);
		Set<String> userBlocked = stats.blockMap.get(user);
		if (userBlocked != null) {
			userFollowers.removeAll(userBlocked);
		}
		if (tweet.length() > stats.lengthOfLongestTweet) {
			stats.lengthOfLongestTweet = tweet.length();
		}
		if (!stats.tweetOwnerDeets.containsKey(tweet)) {
			stats.tweetOwnerDeets.put(tweet, user);
			if (userFollowers != null) {
				stats.tweetRecieversDeets.put(tweet, userFollowers);
			} else {
				stats.tweetRecieversDeets.put(tweet, null);
			}
		} else {
			if (userFollowers != null) {
				if (stats.tweetRecieversDeets.get(tweet) == null) {
					stats.tweetRecieversDeets.put(tweet, userFollowers);
					stats.tweetRecieversDeets.get(tweet).add(user);
				} else {
					stats.tweetRecieversDeets.get(tweet).addAll(userFollowers);
				}
			}
		}
		if (!stats.tweetOwnerId.containsKey((UUID) result)) {
			stats.tweetOwnerId.put((UUID) result, user);
		}
		
		Set<String> currentTweetRecievers = stats.tweetRecieversDeets.get(tweet);
		// System.out.println(currentTweetRecievers.size());
		// System.out.println(stats.previousPopularMessage);
		if (currentTweetRecievers != null) {
			if (currentTweetRecievers.size() >= stats.previousPopularMessage) {
				if (currentTweetRecievers.size() == stats.previousPopularMessage){
					if(stats.mostPopularMessage.compareTo((UUID)og) >=0 ) {
						stats.mostPopularMessage = (UUID)og;
						stats.prevMostPopularMessage = (UUID)og;
					}
				} else {
					stats.mostPopularMessage = (UUID)result;
					stats.prevMostPopularMessage = (UUID)result;
					stats.previousPopularMessage = currentTweetRecievers.size();
					// System.out.println(stats.mostPopularMessage);
					// System.out.println(currentTweetRecievers.size());
				}

			}
		}
		if (!stats.replyDB.containsKey(user)) {
			Set<String> tweets = new HashSet<String>();
			tweets.add(tweet);
			stats.replyDB.put(user, tweets);
		} else {
			stats.replyDB.get(user).add(tweet);
		}
		int totalLengthOfTweets = 0;
		for (String twts : stats.replyDB.get(user)) {
			totalLengthOfTweets += twts.length();
		}
		if (totalLengthOfTweets >= stats.mostProductiveUserTweetLength) {
			if (totalLengthOfTweets == stats.mostProductiveUserTweetLength) {
				// System.out.println(stats.mostProductiveReplier);
				// System.out.println(user);
				// System.out.println("REERER"+stats.mostProductiveReplier.compareTo(user));
				if (stats.mostProductiveReplier == null) {
					stats.mostProductiveReplier = user;
				} else if (stats.mostProductiveReplier.compareTo(user) > 0) {
					stats.mostProductiveReplier = user;
				}
			} else if (totalLengthOfTweets > stats.mostProductiveUserTweetLength) {
				stats.mostProductiveReplier = user;
				stats.mostProductiveUserTweetLength = totalLengthOfTweets;
			}
		}

		
		// System.out.println("YAYAY" + stats.replyThreadD);
		// System.out.println(og);
		// System.out.println(result);
		// System.out.println("AKASKDNJASNDJSAD" + stats.replyThread.get(og));
		
		if(!stats.replyThread.containsKey(og)){
			// if(!stats.replyThread.containsValue(value))
			Set<UUID> rep = new HashSet<>();
			rep.add(result);
			stats.replyThread.put(og, rep);
		} else{
			stats.replyThread.get(og).add(result);
		}

		if(!stats.replyThreadA.containsKey(og)){
			// if(!stats.replyThread.containsValue(value))
			
			stats.replyThreadA.put(og, result);
		} else{
			stats.replyThreadA.put(og, result);
		}

		
		if(!stats.replyThreadD.containsKey(og)){
			// if(!stats.replyThread.containsValue(value))
			// System.out.println("HSH");
			int rep = 1;
			stats.replyThreadD.put(og, rep);
		} else{
			// System.out.println("MAIN YAHA"+stats.replyThreadD.get(og));
			stats.replyThreadD.put(result, stats.replyThreadD.get(og)+1);
			// System.out.println("Se YAHA"+stats.replyThreadD.get(result));
			// System.out.println("DEPTH" + stats.replyThreadD.get(og).intValue()); 
		}

		// System.out.println(stats.replyThreadD.get(og));
		int curRep = stats.replyThreadD.get(result);
		if(curRep > 0){
			if(curRep >= stats.prevLongThread){
				if(curRep == stats.prevLongThread){
				System.out.println("Ya");
				if(stats.longestMessageThread.compareTo((UUID)result) >= 0 ) {
					stats.longestMessageThread = (UUID)result;
					stats.prevLongThread = curRep;
				}
				else{
					stats.prevLongThread = curRep;
				}
			} else {
				stats.longestMessageThread = (UUID) result;
				stats.prevLongThread = curRep;
			}
			}
		}

		// Set<UUID> curRep = stats.replyThread.get(og);
		// System.out.println(stats.replyThread);
		// System.out.println("mau" + stats.prevLongThread);
		// System.out.println("LMT"+ stats.longestMessageThread);
		// if (curRep != null) {
		// 	if (curRep.size() >= stats.prevLongThread) {
		// 		if (curRep.size() == stats.prevLongThread){
		// 			// System.out.println("YAHA");
		// 			if(stats.longestMessageThread.compareTo((UUID)result) >= 0 ) {
		// 				stats.longestMessageThread = (UUID)result;
		// 				stats.prevLongThread = curRep.size();
		// 			}
		// 		} else {
		// 			stats.longestMessageThread = (UUID)result;
		// 			stats.prevLongThread = curRep.size();
		// 			System.out.println(stats.longestMessageThread);
		// 			System.out.println(curRep.size());
		// 		}

		// 	}
		// }

	}

	@AfterReturning("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
	public void followStats(JoinPoint joinPoint) {
		String follower = joinPoint.getArgs()[0].toString();
		String user = joinPoint.getArgs()[1].toString();
		int count = 0;
		if (!stats.followMap.containsKey(user)) {
			count += 1;
			Set<String> followers = new HashSet<String>();
			followers.add(follower);
			count = followers.size();
			stats.followMap.put(user, followers);
		} else {
			if (!stats.followMap.get(user).contains(follower)) {
				Set<String> followers = stats.followMap.get(user);
				followers.add(follower);
				count = followers.size();
			}
		}
		if ((stats.maxFollowCount == count && stats.mostFollowedUser.compareTo(user) > 0)
				|| stats.maxFollowCount < count) {
			stats.maxFollowCount = count;
			stats.mostFollowedUser = user;
		}

	}

	@AfterReturning("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
	public void blockStats(JoinPoint joinPoint) {
		String user = joinPoint.getArgs()[0].toString();
		String follower = joinPoint.getArgs()[1].toString();
		if (!stats.blockMap.containsKey(user)) {
			Set<String> blocked = new HashSet<String>();
			blocked.add(follower);
			stats.blockMap.put(user, blocked);
		} else {
			stats.blockMap.get(user).add(follower);
		}
		if (!stats.blockedByMap.containsKey(follower)) {
			Set<String> blockedBy = new HashSet<String>();
			blockedBy.add(user);
			stats.blockedByMap.put(follower, blockedBy);
		} else {
			stats.blockedByMap.get(follower).add(user);
		}
		// System.out.println(stats.blockedByMap);
		// System.out.println(follower);
		// System.out.println(stats.mostBlockedByCount <= stats.blockedByMap.get(follower).size());
		// System.out.println(stats.mostBlockedByCount == stats.blockedByMap.get(follower).size());
		// System.out.println(stats.mostUnpopularFollower.compareTo(follower));
		if (stats.mostBlockedByCount <= stats.blockedByMap.get(follower).size()) {
			if (stats.mostBlockedByCount == stats.blockedByMap.get(follower).size()){
				if(stats.mostUnpopularFollower.compareTo(follower) >= 0){
					stats.mostUnpopularFollower = follower;
				}
			} else{

			stats.mostUnpopularFollower = follower;
			stats.mostBlockedByCount = stats.blockedByMap.get(follower).size();
			}
		}
	}

	@AfterReturning("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
	public void likeStats(JoinPoint joinPoint) {
		String follower = joinPoint.getArgs()[0].toString();
		UUID msg = (UUID) joinPoint.getArgs()[1];
		int count = 0;
		if (!stats.tweetLiker.containsKey(msg)) {
			Set<String> likers = new HashSet<String>();
			likers.add(follower);
			stats.tweetLiker.put(msg, likers);
			if (stats.mostLikedMessage == null) {
				stats.mostLikedMessage = msg;
				stats.mostLikedMessageCount = 1;
			}
			count = 1;
		} else {
			stats.tweetLiker.get(msg).add(follower);
			count = stats.tweetLiker.get(msg).size();
		}
		if (count >= stats.mostLikedMessageCount) {
			if (count == stats.mostLikedMessageCount) {
				if (stats.mostLikedMessage.compareTo(msg) >= 0) {
					stats.mostLikedMessage = msg;
				}
			} else {
				stats.mostLikedMessage = msg;
				stats.mostLikedMessageCount = count;
			}
		}
	}

	
}
