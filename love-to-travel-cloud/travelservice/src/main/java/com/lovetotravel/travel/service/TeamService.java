package com.lovetotravel.travel.service;

import com.lovetotravel.travel.entity.Team;
import com.lovetotravel.travel.entity.vo.TeamCreateVo;
import com.lovetotravel.travel.entity.vo.TeamInviteVo;
import com.lovetotravel.travel.entity.vo.TeamKickVo;
import com.lovetotravel.travel.entity.vo.TeamUpdateVo;

import java.util.List;

public interface TeamService {

    Team getById(String id);

    List<Team> getCreatedById(String id);

    List<Team> getJoinedById(String id);

    void insert(TeamCreateVo teamCreateVo);

    void update(TeamUpdateVo teamUpdateVo);

    void removeById(String id);

    void invite(TeamInviteVo teamInviteVo);

    void kick(TeamKickVo teamKickVo);


}
