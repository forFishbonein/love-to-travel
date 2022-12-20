package com.lovetotravel.travel.service.impl;

import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lovetotravel.feign.clients.UserClient;
import com.lovetotravel.feign.entity.Result;
import com.lovetotravel.feign.entity.User;
import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.Scenery;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.page.QueryPageVo;
import com.lovetotravel.travel.entity.vo.note.*;
import com.lovetotravel.travel.exception.GlobalException;
import com.lovetotravel.travel.mapper.NoteLikeMapper;
import com.lovetotravel.travel.mapper.NoteStarMapper;
import com.lovetotravel.travel.mapper.SceneryRelatedMapper;
import com.lovetotravel.travel.redis.NoteKey;
import com.lovetotravel.travel.redis.RedisService;
import com.lovetotravel.travel.result.CodeMsg;
import com.lovetotravel.travel.service.NoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class NoteServiceImpl implements NoteService {

    final MongoTemplate mongoTemplate;
    final RedisService redisService;
    final NoteLikeMapper noteLikeMapper;
    final NoteStarMapper noteStarMapper;
    final UserClient userClient;
    final SceneryRelatedMapper sceneryRelatedMapper;

    public NoteServiceImpl(MongoTemplate mongoTemplate, RedisService redisService, NoteLikeMapper noteLikeMapper, NoteStarMapper noteStarMapper, UserClient userClient, SceneryRelatedMapper sceneryRelatedMapper) {
        this.mongoTemplate = mongoTemplate;
        this.redisService = redisService;
        this.noteLikeMapper = noteLikeMapper;
        this.noteStarMapper = noteStarMapper;
        this.userClient = userClient;
        this.sceneryRelatedMapper = sceneryRelatedMapper;
    }

    /**
     * 根据游记id获取游记
     *
     * @param id
     * @return
     */
    @Override
    public Note getById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.findOne(query, Note.class);
    }

    @Override
    public List<Note> getRelatedById(String sceneryId) {

        QueryWrapper<SceneryRelated> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SceneryRelated::getSceneryId, sceneryId);
        List<SceneryRelated> sceneryRelateds = sceneryRelatedMapper.selectList(queryWrapper);
        if (sceneryRelateds == null) {
            return null;
        }

        List<Note> noteList = new ArrayList<>();


        for (SceneryRelated s : sceneryRelateds) {
            System.out.println("s = " + s);
            System.out.println("s = " + s);
            System.out.println("s = " + s);
            System.out.println("s = " + s);
            System.out.println("s = " + s);


            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(s.getNoteId()));
            query.addCriteria(Criteria.where("deleted").is("0"));
            Note one = mongoTemplate.findOne(query, Note.class);
            System.out.println("one = " + one);
            System.out.println(one);
            noteList.add(one);
        }

        return noteList;
    }

    @Override
    public List<Note> getRelatedByNote(String id) {
        if (id.equals("639bdb4da0f86e39f6993582")) {
            List<Note> noteList = new ArrayList<>();
            String related = "639bdb4da0f86e39f699358e,639bdb4da0f86e39f699358d,639bdb4da0f86e39f699358c,639bdb4da0f86e39f699358b,639bdb4da0f86e39f699358f,639bdb4da0f86e39f699358a";

            String[] result = related.split(",");

            for (String s: result) {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(s));
                query.addCriteria(Criteria.where("deleted").is("0"));
                Note one = mongoTemplate.findOne(query, Note.class);
                noteList.add(one);

            }

            return noteList;
        }


        return null;
    }

    /**
     * 根据用户id获取游记
     *
     * @param userId
     * @return
     */
    @Override
    public List<Note> getByUserId(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.find(query, Note.class);
    }

    @Override
    public List<Note> getAll() {
        Query query = new Query();
        query.addCriteria(Criteria.where("deleted").is("0"));
        return mongoTemplate.find(query, Note.class);
    }

    @Override
    public PageVo<Note> getPage(PageVo pageVo) {
        Integer pageSize = pageVo.getPageSize();
        Integer pageNum = pageVo.getPageNum();
        List<Note> list;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("deleted").is("0"));
            long total = mongoTemplate.count(query, Note.class);
            //默认值为5，
            pageSize = pageSize < 0 ? 5 : pageSize;
            query.limit(pageSize);
            query.skip((pageNum - 1) * pageSize);
            list = mongoTemplate.find(query, Note.class);
            pageVo.setRecords(list);
            pageVo.setTotal(total);
            return pageVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public QueryPageVo<Note> fuzzyQuery(QueryPageVo pageVo) {
        if (pageVo.getQueryStr() == null || pageVo.getQueryStr().equals("")) {
            throw new GlobalException(CodeMsg.QUERY_EMPTY);
        }
        Integer pageSize = pageVo.getPageSize();
        Integer pageNum = pageVo.getPageNum();
        List<Note> list;
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("deleted").is("0"));
            long total = mongoTemplate.count(query, Note.class);
            //默认值为5，
            pageSize = pageSize < 0 ? 5 : pageSize;
            query.limit(pageSize);
            query.skip((pageNum - 1) * pageSize);

            Pattern pattern= Pattern.compile("^.*"+pageVo.getQueryStr()+".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("content").regex(pattern));

            list = mongoTemplate.find(query, Note.class);
            pageVo.setRecords(list);
            pageVo.setTotal(total);
            return pageVo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新增
     *
     * @param noteVo
     */
    @Override
    public void insert(NoteVo noteVo) {

        Note note = new Note();
        BeanUtils.copyProperties(noteVo, note);
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        note.setCreateTime(currentTimeStamp);
        note.setUpdateTime(currentTimeStamp);
        note.setDeleted("0");
        note.setLike(0L);
        note.setComment(0L);
        note.setView(0L);
        note.setStar(0L);

        Result<User> result = userClient.getById(Long.valueOf(noteVo.getUserId()));
        User user = result.getData();
        note.setUserName("来自远方的驴友");
        if (user != null) {
            note.setUserName(user.getName());
        }
        mongoTemplate.insert(note);
    }

    /**
     * 更新
     *
     * @param noteVo
     */
    @Override
    public void update(NoteVo noteVo) {

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(noteVo.getId()));
        query.addCriteria(Criteria.where("deleted").is("0"));

        Update update = new Update();

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        update.set("title", noteVo.getTitle())
                .set("url", noteVo.getUrl())
                .set("plan", noteVo.getPlanId())
                .set("content", noteVo.getContent())
                .set("updateTime", currentTimeStamp);
        mongoTemplate.updateFirst(query, update, Note.class);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void removeById(String id) {
        if (id == null) {
            throw new GlobalException(CodeMsg.NOTE_NOT_EXIST);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("deleted").is("0"));
        Update update = new Update();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTimeStamp = dateFormat.format(date);
        update.set("deleted", "1")
                .set("updateTime", currentTimeStamp);
        mongoTemplate.updateFirst(query, update, Note.class);
    }

    @Override
    public void removeList(String[] ids) {
        if (ids.length != 0) {

            for (String id : ids) {
                Query query = new Query();
                query.addCriteria(Criteria.where("id").is(id));
                query.addCriteria(Criteria.where("deleted").is("0"));
                Update update = new Update();
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String currentTimeStamp = dateFormat.format(date);
                update.set("deleted", "1")
                        .set("updateTime", currentTimeStamp);
                mongoTemplate.updateFirst(query, update, Note.class);
            }
        }
    }

    @Override
    public void incrComment(String id) {
        redisService.incr(NoteKey.getComment, id);
    }

    @Override
    public void incrView(String id) {
        redisService.incr(NoteKey.getView, id);
    }

    @Override
    public void like(NoteLike noteLike) {
        //查询用户是否点赞
        QueryWrapper<NoteLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteLike::getUserId, noteLike.getUserId()).eq(NoteLike::getNoteId, noteLike.getNoteId());
        NoteLike noteLikeInMysql = noteLikeMapper.selectOne(queryWrapper);
        if (noteLikeInMysql == null) {
            //增加点赞数
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(noteLike.getNoteId()));
            Note note = mongoTemplate.findOne(query, Note.class);
            System.out.println("note = " + note);
            if (note.getLike() == null) {
                note.setLike(0L);
            }
            Update update = new Update();
            update.set("like", note.getLike() + 1);
            mongoTemplate.upsert(query, update, Note.class);
            //保存用户点赞信息
            noteLikeMapper.insert(noteLike);
        }
    }

    @Override
    public Boolean isLike(NoteLike noteLike) {
        QueryWrapper<NoteLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteLike::getUserId, noteLike.getUserId()).eq(NoteLike::getNoteId, noteLike.getNoteId());
        NoteLike commentLikeInMysql = noteLikeMapper.selectOne(queryWrapper);
        if (commentLikeInMysql == null) {
            return false;
        }
        return true;
    }

    @Override
    public void unLike(NoteLike noteLike) {
        //查询用户是否点赞
        QueryWrapper<NoteLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteLike::getUserId, noteLike.getUserId()).eq(NoteLike::getNoteId, noteLike.getNoteId());
        NoteLike noteLikeInMysql = noteLikeMapper.selectOne(queryWrapper);
        if (noteLikeInMysql != null) {
            //减少点赞数
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(noteLike.getNoteId()));
            Note note = mongoTemplate.findOne(query, Note.class);
            System.out.println("note = " + note);
            Update update = new Update();

            update.set("like", note.getLike() - 1);
            if (note.getLike() < 0) {
                update.set("like", 0);
            }
            mongoTemplate.upsert(query, update, Note.class);
            //保存用户点赞信息
            noteLikeMapper.delete(queryWrapper);
        }
    }



    @Override
    public void star(NoteStar noteStar) {
        //查询用户是否收藏
        QueryWrapper<NoteStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteStar::getUserId, noteStar.getUserId()).eq(NoteStar::getNoteId, noteStar.getNoteId());
        NoteStar noteStarInMysql = noteStarMapper.selectOne(queryWrapper);
        if (noteStarInMysql == null) {
            //增加点赞数
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(noteStar.getNoteId()));
            Note note = mongoTemplate.findOne(query, Note.class);
            System.out.println("note = " + note);
            if (note.getStar() == null) {
                note.setStar(0L);
            }
            Update update = new Update();
            update.set("star", note.getStar() + 1);
            mongoTemplate.upsert(query, update, Note.class);
            //保存用户收藏信息
            noteStarMapper.insert(noteStar);
        }
    }

    @Override
    public void unStar(NoteStar noteStar) {
        //查询用户是否收藏
        QueryWrapper<NoteStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteStar::getUserId, noteStar.getUserId()).eq(NoteStar::getNoteId, noteStar.getNoteId());
        NoteStar noteStarInMysql = noteStarMapper.selectOne(queryWrapper);
        if (noteStarInMysql != null) {
            //增加点赞数
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(noteStar.getNoteId()));
            Note note = mongoTemplate.findOne(query, Note.class);
            System.out.println("note = " + note);
            Update update = new Update();
            update.set("star", note.getStar() - 1);
            if (note.getStar() < 0) {
                update.set("star", 0);
            }
            mongoTemplate.upsert(query, update, Note.class);
            //删除用户收藏信息
            noteStarMapper.delete(queryWrapper);
        }
    }

    @Override
    public Boolean isStar(NoteStar noteStar) {
        QueryWrapper<NoteStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteStar::getUserId, noteStar.getUserId()).eq(NoteStar::getNoteId, noteStar.getNoteId());
        NoteStar commentStarInMysql = noteStarMapper.selectOne(queryWrapper);
        if (commentStarInMysql == null) {
            return false;
        }
        return true;
    }

    @Override
    public List<Note> getStarByUserId(Long id) {
        QueryWrapper<NoteStar> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(NoteStar::getUserId, id);
        List<NoteStar> noteStars = noteStarMapper.selectList(queryWrapper);
        List<Note> notes = new ArrayList<>();
        for (NoteStar n : noteStars) {
            System.out.println(n.getNoteId());
            Query query = new Query();
            query.addCriteria(Criteria.where("id").is(n.getNoteId()));
            notes.add(mongoTemplate.findOne(query, Note.class));
        }
        return notes;
    }

    @Override
    public NoteStatistic getStatistic() {
        Query queryLike = new Query();
        queryLike.addCriteria(Criteria.where("deleted").is("0")).with(Sort.by(Sort.Order.desc("like"))).limit(5);
        List<Note> likeList = mongoTemplate.find(queryLike, Note.class);

        Query queryStar = new Query();
        queryStar.addCriteria(Criteria.where("deleted").is("0")).with(Sort.by(Sort.Order.desc("star"))).limit(5);
        List<Note> starList = mongoTemplate.find(queryStar, Note.class);

        Query queryView = new Query();
        queryView.addCriteria(Criteria.where("deleted").is("0")).with(Sort.by(Sort.Order.desc("view"))).limit(5);
        List<Note> viewList = mongoTemplate.find(queryView, Note.class);

        NoteStatistic noteStatistic = new NoteStatistic();
        noteStatistic.setLikeList(likeList);
        noteStatistic.setStarList(starList);
        noteStatistic.setViewList(viewList);
        return noteStatistic;
    }


}
