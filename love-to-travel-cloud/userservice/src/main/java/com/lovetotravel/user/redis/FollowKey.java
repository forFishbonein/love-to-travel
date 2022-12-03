package com.lovetotravel.user.redis;

public class FollowKey extends BasePrefix {


    //缓存的前缀
    public static final String GET_FOLLOWER = "follower";
    public static final String GET_FOLLOWEE = "followee";
    public static FollowKey getFollower = new FollowKey(0, GET_FOLLOWER);
    public static FollowKey getFollowee = new FollowKey(0, GET_FOLLOWEE);
    /**
     * 防止被外面实例化
     */
    private FollowKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
