package com.metoo.nspm.core.service;

import com.metoo.nspm.dto.RackDTO;
import com.metoo.nspm.entity.Rack;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface IRackService {
    Rack getObjById(Long id);

    Page<Rack> findBySelect(RackDTO instance);

    List<Rack> query(Rack rack);

    List<Rack> selectObjByMap(Map params);

    int save(Rack instance);

    int update(Rack instance);

    int delete(Long id);

    int batchDel(String ids);

    // 机柜信息（包含机柜空闲位置，以及设备信息，正反面）
    Object rack(Long id);

    boolean verifyRack(Rack rack, Integer start, Integer size, boolean rear, Long id);
}
