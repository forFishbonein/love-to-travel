package com.lovetotravel.travel.handler;

import com.lovetotravel.travel.entity.Note;
import com.lovetotravel.travel.entity.vo.NoteVo;
import com.lovetotravel.travel.redis.NoteKey;
import com.lovetotravel.travel.redis.RedisService;
import com.lovetotravel.travel.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    final NoteService noteService;
    final RedisService redisService;

    public ListenHandler(NoteService noteService, RedisService redisService) {
        this.noteService = noteService;
        this.redisService = redisService;
    }


    @PostConstruct
    public void init() throws Exception {
        log.info("数据初始化开始...");
        //将数据库中的数据写入redis
        List<Note> noteList = noteService.getAll();
        noteList.forEach(note -> {
            //将浏览量、点赞数和评论数写入redis
            redisService.sadd(NoteKey.getComment, note.getId().toString(), note.getComment());
            redisService.sadd(NoteKey.getLike, note.getId().toString(), note.getLike());
            redisService.sadd(NoteKey.getView, note.getId().toString(), note.getView());
        });
        log.info("数据已写入redis...");
    }

    /**
     * 关闭时操作
     */
    @PreDestroy
    public void afterDestroy() {
        log.info("开始关闭...");
        //将redis中的数据写入数据库
        List<Note> noteList = noteService.getAll();
        noteList.forEach(note -> {
            //将浏览量、点赞数和评论数写入redis
            Long commentNum = redisService.scard(NoteKey.getLike, note.getId().toString());
            Long likeNum = redisService.scard(NoteKey.getView, note.getId().toString());
            Long viewNum = redisService.scard(NoteKey.getComment, note.getId().toString());

            writeNum(commentNum, NoteKey.getComment.getPrefix(), note.getId().toString());
            writeNum(likeNum, NoteKey.getLike.getPrefix(), note.getId().toString());
            writeNum(viewNum, NoteKey.getView.getPrefix(), note.getId().toString());
        });

        log.info("redis写入数据库完毕");
    }

    @Scheduled(cron = "*/15 * * * * ?")
    public void updateNum() {
        log.info("周期任务开始执行...");
        List<Note> noteList = noteService.getAll();
        noteList.forEach(note -> {
            //将浏览量、点赞数和评论数写入redis
            Long likeNum = redisService.scard(NoteKey.getView, note.getId().toString());
            Long viewNum = redisService.scard(NoteKey.getComment, note.getId().toString());

            writeNum(likeNum, NoteKey.getLike.getPrefix(), note.getId().toString());
            writeNum(viewNum, NoteKey.getView.getPrefix(), note.getId().toString());
        });
        log.info("周期任务执行完毕,redis写入数据库完毕");
    }

    private void writeNum(Long num, String fieldName, String id) {

        Note note = noteService.getById(id);
        switch (fieldName) {
            case "comment":
                note.setComment(num.toString());
                break;
            case "like":
                note.setLike(num.toString());
                break;
            case "view":
                note.setView(num.toString());
                break;
            default:
                return;
        }
        NoteVo noteVo = new NoteVo();
        BeanUtils.copyProperties(note, noteVo);

        //更新数据库
        noteService.update(noteVo);
        log.info("{} 更新完毕", fieldName);

    }
}