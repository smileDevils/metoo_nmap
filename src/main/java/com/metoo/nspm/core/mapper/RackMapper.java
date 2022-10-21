package com.metoo.nspm.core.mapper;

import com.metoo.nspm.dto.RackDTO;
import com.metoo.nspm.entity.Rack;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RackMapper {

    Rack getObjById(Long id);

    List<Rack> findBySelect(RackDTO instance);

    List<Rack> selectObjByMap(Map params);

    List<Rack> query(Rack rack);

    int save(Rack instance);

    int update(Rack instance);

    int delete(Long id);

    int batchDel(String ids);
}
