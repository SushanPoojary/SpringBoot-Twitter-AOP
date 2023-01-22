package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;

@Aspect
@Order(0)
public class AccessControlAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     * @throws Throwable 
     */

	// @Around("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.*(..))")
	// public int dummyAdviceOne(ProceedingJoinPoint joinPoint) throws Throwable {
	// 	System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// 	Integer result = null;
	// 	try {
	// 		result = (Integer) joinPoint.proceed();
	// 		System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
	// 	} catch (Throwable e) {
	// 		e.printStackTrace();
	// 		System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// 		throw e;
	// 	}
	// 	return result.intValue();
	// }
	
	@Around("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.reply(..))")
	public Object replyAccess(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Object result = null;
		Object[] args = joinPoint.getArgs();
		String user = (String)args[0];
		// final UUID op = (UUID)args[1];
		UUID og = (UUID)args[1];
		String msg = (String)args[2];
		// System.out.println(TweetStatsServiceImpl.followMap);
		ArrayList<String> blockUser = new ArrayList<>();
		for(String key : TweetStatsServiceImpl.blockedByMap.keySet()){
			// System.out.println(key);
			if(key == user){
				// System.out.println(user);
				for(String values: TweetStatsServiceImpl.blockedByMap.get(user)){
					// System.out.println(values);
					blockUser.add(values);
				}
			}
			}

		ArrayList<String> shareUser = new ArrayList<>();
		// System.out.println(TweetStatsServiceImpl.tweetOwnerId.get(og));
		String fol = TweetStatsServiceImpl.tweetOwnerId.get(og);
		for(String key : TweetStatsServiceImpl.followMap.keySet()){
			// System.out.println(key);
			if(key == fol){
				// System.out.println(user);
				for(String values: TweetStatsServiceImpl.followMap.get(fol)){
					// System.out.println(values);
					shareUser.add(values);
				}
			}
			}
		// System.out.println(shareUser);
		// System.out.println(og);
		// System.out.println(TweetStatsServiceImpl.tweetOwnerId.get(og));
		// System.out.println(user);
		// System.out.println(TweetStatsServiceImpl.replyThreadA);
		UUID parentUUID = null;
		for (Entry<UUID, UUID> entry : TweetStatsServiceImpl.replyThreadA.entrySet()) {
			if (Objects.equals(og, entry.getValue())) {
				parentUUID = entry.getKey();
			}
		}
		// System.out.println(TweetStatsServiceImpl.tweetOwnerId.get(parentUUID));
		// System.out.println(op);
		// System.out.println(og);
		// System.out.println(TweetStatsServiceImpl.tweetOwnerId);
		// System.out.println(TweetStatsServiceImpl.tweetOwnerId.get(og));
		// System.out.println(blockUser.contains(TweetStatsServiceImpl.tweetOwnerId.get(og)));
		
		
		if(blockUser.contains(TweetStatsServiceImpl.tweetOwnerId.get(og))){
			throw new AccessControlException("Blocked User Cannot Reply!");
		}
		if(!(shareUser.contains(user) || TweetStatsServiceImpl.tweetOwnerId.get(parentUUID) == user)){
			throw new AccessControlException("Message not shared!");
		}
		
		try {
			result = joinPoint.proceed();
			// System.out.println(result);
			// System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (AccessControlException e) {
			e.printStackTrace();
			// System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
			throw e;
		}
		return result;
	}



	@Around("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
	public Object likeAccess(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.printf("Prior to the executuion of the metohd %s\n", joinPoint.getSignature().getName());
		Object result = null;
		Object[] args = joinPoint.getArgs();
		// System.out.println(TweetStatsServiceImpl.followMap);
		// System.out.println(args[1]);
		// System.out.println(TweetStatsServiceImpl.);
		String f = null;
		if(TweetStatsServiceImpl.tweetOwnerId.containsKey(args[1])){
			f = TweetStatsServiceImpl.tweetOwnerId.get(args[1]);
		}
		// System.out.println(args[1]);
		// System.out.println(TweetStatsServiceImpl.tweetOwnerId);
		// System.out.println(TweetStatsServiceImpl.tweetReplierId);
		// System.out.println(f);
		// System.out.println(TweetStatsServiceImpl.followMap);
		// System.out.println((String)args[0]);
		ArrayList<String> followL = new ArrayList<>();
		for(String key : TweetStatsServiceImpl.followMap.keySet()){
			if(key == f){
				for(String values: TweetStatsServiceImpl.followMap.get(f)){
						followL.add(values);
				}
			}
			}
		
		ArrayList<String> aL = new ArrayList<>();
		// System.out.println(TweetStatsServiceImpl.tweetLiker.toString());
		if(TweetStatsServiceImpl.tweetLiker.containsKey((UUID)args[1])){
		for(UUID key : TweetStatsServiceImpl.tweetLiker.keySet()){
			if(key == (UUID)args[1]){
				for(String values: TweetStatsServiceImpl.tweetLiker.get(key)){
						aL.add(values);
				}
			}
			}
			}
		
		// System.out.println("dasdsa"+followL);
		// System.out.println("DSDSDSD"+aL);
		
		if(f==(String)args[0]){
			throw new AccessControlException("Cant Like Your own message!");
		}
		
		if(!(followL.contains((String)args[0]))){
			throw new AccessControlException("Cant Like a Message which isnt shared!");
		}

		if(aL.contains((String)args[0])){
			throw new AccessControlException("Cant Like a Message again!");
		}
		
		// if(val == true || val1 == true){
		// 	throw new AccessControlException("Blocked User Cannot Like!");
		// }
		
		try {
			result = joinPoint.proceed();
			// System.out.println(result);
			// System.out.printf("Finished the executuion of the metohd %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (AccessControlException e) {
			e.printStackTrace();
			// System.out.printf("Aborted the executuion of the metohd %s\n", joinPoint.getSignature().getName());
			throw e;
		}
		return result;
	}
}
