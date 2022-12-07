package com.lovetotravel.travel.redis;

public class NoteKey extends BasePrefix {


    //缓存的前缀
    public static final String VIEW_KEY = "view";
    public static final String COMMENT_KEY = "comment";
    public static final String LIKE_KEY = "like";

    public static NoteKey getView = new NoteKey(0, VIEW_KEY);
    public static NoteKey getComment = new NoteKey(0, COMMENT_KEY);
    public static NoteKey getLike = new NoteKey(0, LIKE_KEY);

    /**
     * 防止被外面实例化
     */
    private NoteKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

}
