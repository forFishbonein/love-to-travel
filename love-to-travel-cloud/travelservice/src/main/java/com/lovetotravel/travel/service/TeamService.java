package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Team;
import com.lovetotravel.travel.entity.page.PageVo;
import com.lovetotravel.travel.entity.vo.team.*;

import java.util.List;

public interface TeamService {

    Team getById(String id);

    List<Team> getCreatedById(String id);

    List<Team> getJoinedById(String id);

    List<Team> getAll();

    PageVo<Team> getPage(PageVo pageVo);

    void insert(TeamCreateVo teamCreateVo);

    void update(TeamUpdateVo teamUpdateVo);

    void removeById(String id);

    void invite(TeamInviteVo teamInviteVo);

    void kick(TeamKickVo teamKickVo);

    void join(TeamJoinVo teamJoinVo);

    Boolean isJoin(TeamVo teamVo);

    void leave(TeamLeaveVo teamLeaveVo);


}
