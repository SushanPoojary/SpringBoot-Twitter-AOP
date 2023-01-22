package edu.sjsu.cmpe275.aop.tweet.aspect;

import java.security.AccessControlException;
import java.util.Set;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.tweet.TweetStatsServiceImpl;


@Aspect
@Order(3)
public class ValidationAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	// @Before("execution(public int edu.sjsu.cmpe275.aop.tweet.TweetService.retweet(..))")
	// public void dummyBeforeAdvice(JoinPoint joinPoint) {
	// 	System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
	// }
    @Autowired TweetStatsServiceImpl tweetDetails;

    @Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.tweet(..))")
    public void validTweet(JoinPoint joinPoint){
        System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
        Object[] params = joinPoint.getArgs();
        for(Object i : params){
            if(i == null){
                throw new IllegalArgumentException("Cannot be Null!");
            }
        }
        if(((String)params[1]).length() < 1){
            throw new IllegalArgumentException("Tweet cannot be empty.");
        }
        if(((String)params[0]).length() < 1){
            throw new IllegalArgumentException("User cannot be empty.");
        }
        if(((String)params[1]).length() > 140){
            throw new IllegalArgumentException("Tweet length exceeds 140 character limit.");
        }
    }

    @Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.reply(..))")
    public void validReply(JoinPoint joinPoint){
        System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
        // System.out.println(TweetStatsServiceImpl.blockedByMap);
        Object[] params = joinPoint.getArgs();
        for(Object i : params){
            if(i == null){
                throw new IllegalArgumentException("Cannot be Null!");
            }
        }
        if(((String)params[2]).length() < 1){
            throw new IllegalArgumentException("Cannot have an empty reply.");
        }
        if(((String)params[2]).length() > 140){
            throw new IllegalArgumentException("Tweet length exceeds 140 character limit.");
        }
        if(((String)params[0]).length() < 1){
            throw new IllegalArgumentException("Invalid user.");
        }
        if(((UUID)params[1]).toString() == ""){
            throw new IllegalArgumentException("Invalid UUID.");
        }
        if(TweetStatsServiceImpl.tweetOwnerId.get((UUID)params[1]) == (String)params[0]){
            throw new IllegalArgumentException("Cannot reply to your own message.");
        }
        
    }

    @Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.follow(..))")
    public void validFollow(JoinPoint joinPoint){
        System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
        Object[] params = joinPoint.getArgs();
        for(Object i : params){
            if(i == null){
                throw new IllegalArgumentException("Cannot be Null!");
            }
        }
        if(((String)params[1]).length() < 1){
            throw new IllegalArgumentException("Cannot follow invalid user.");
        }
        if(((String)params[0]).length() < 1){
            throw new IllegalArgumentException("Invalid user.");
        }
        if(((String)params[0]).equals((String)params[1])){
            throw new IllegalArgumentException("Cannot follow yourself!");
        }
    }

    

    @Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.block(..))")
    public void validBlock(JoinPoint joinPoint){
        System.out.printf("Permission check before the executuion of the metohd %s\n", joinPoint.getSignature().getName());
        Object[] params = joinPoint.getArgs();
        for(Object i : params){
            if(i == null){
                throw new IllegalArgumentException("Cannot be Null!");
            }
        }
        if(((String)params[1]).length() < 1){
            throw new IllegalArgumentException("Cannot block invalid user.");
        }
        if(((String)params[0]).length() < 1){
            throw new IllegalArgumentException("Invalid user.");
        }
        if(((String)params[0]).equals((String)params[1])){
            throw new IllegalArgumentException("Cannot block yourself!");
        }
    }

    @Before("execution(public * edu.sjsu.cmpe275.aop.tweet.TweetService.like(..))")
    public void validLike(JoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        for(Object i : params){
            if(i == null){
                throw new IllegalArgumentException("Cannot be Null!");
            }
        }
        if(((UUID)params[1]).toString() == ""){
            throw new IllegalArgumentException("Invalid UUID.");
        }
        if(((String)params[0]).length() < 1){
            throw new IllegalArgumentException("Invalid user.");
        }
        
       
    }

	
}
