//package com.lovetotravel.travel.handler;
//
//import com.lovetotravel.travel.entity.Note;
//import com.lovetotravel.travel.entity.vo.note.NoteVo;
//import com.lovetotravel.travel.redis.NoteKey;
//import com.lovetotravel.travel.redis.RedisService;
//import com.lovetotravel.travel.service.NoteService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.List;
//
//@Slf4j
//@Component
//@EnableScheduling
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class ListenHandler {
//
//    final NoteService noteService;
//    final RedisService redisService;
//
//    public ListenHandler(NoteService noteService, RedisService redisService) {
//        this.noteService = noteService;
//        this.redisService = redisService;
//    }
//
//
//    @PostConstruct
//    public void init() throws Exception {
//        //将数据库中的数据写入redis
//        List<Note> noteList = noteService.getAll();
//        System.out.println(noteList);
//        noteList.forEach(note -> {
//            if (note.getComment() == null) {
//                note.setComment(0L);
//            }
//            if (note.getLike() == null) {
//                note.setLike(0L);
//            }
//            if (note.getView() == null) {
//                note.setView(0L);
//            }
//            //将浏览量、点赞数和评论数写入redis
//            redisService.set(NoteKey.getComment, note.getId().toString(), note.getComment().toString());
//            redisService.set(NoteKey.getLike, note.getId().toString(), note.getLike().toString());
//            redisService.set(NoteKey.getView, note.getId().toString(), note.getView().toString());
//        });
//    }
//
//    /**
//     * 关闭时操作
//     */
//    @PreDestroy
//    public void afterDestroy() {
//        log.info("开始关闭...");
//        //将redis中的数据写入数据库
//        List<Note> noteList = noteService.getAll();
//        noteList.forEach(note -> {
//            //将浏览量、点赞数和评论数写入redis
//            Long commentNum = redisService.get(NoteKey.getComment, note.getId().toString(), Long.class);
//            Long viewNum = redisService.get(NoteKey.getView, note.getId().toString(), Long.class);
//
//            writeNum(commentNum, NoteKey.getComment.getPrefix(), note.getId().toString());
//            writeNum(viewNum, NoteKey.getView.getPrefix(), note.getId().toString());
//        });
//
//        log.info("redis写入数据库完毕");
//    }
//
//    @Scheduled(cron = "30 * * * * ?")
//    public void updateNum() {
//        log.info("周期任务开始执行...");
//        List<Note> noteList = noteService.getAll();
//        noteList.forEach(note -> {
//            if (note.getComment() == null) {
//                note.setComment(0L);
//            }
//            if (note.getView() == null) {
//                note.setView(0L);
//            }
//            //从redis获取将浏览量、点赞数和评论数
//            Long commentNum = redisService.get(NoteKey.getComment, note.getId().toString(), Long.class);
//            Long viewNum = redisService.get(NoteKey.getView, note.getId().toString(), Long.class);
//            //写入数据库
//            writeNum(commentNum, NoteKey.getComment.getPrefix(), note.getId().toString());
//            writeNum(viewNum, NoteKey.getView.getPrefix(), note.getId().toString());
//        });
//        log.info("周期任务执行完毕,redis写入数据库完毕");
//    }
//
//    private void writeNum(Long num, String fieldName, String id) {
//        Note note = noteService.getById(id);
//        switch (fieldName) {
//            case "NoteKey:comment":
//                note.setComment(num);
//                break;
//            case "NoteKey:view":
//                note.setView(num);
//                break;
//            default:
//                return;
//        }
//        NoteVo noteVo = new NoteVo();
//        BeanUtils.copyProperties(note, noteVo);
//        //更新数据库
//        noteService.update(noteVo);
//    }
//}