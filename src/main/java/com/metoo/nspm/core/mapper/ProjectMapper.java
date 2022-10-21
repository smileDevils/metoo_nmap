package com.metoo.nspm.core.mapper;

import com.metoo.nspm.dto.ProjectDTO;
import com.metoo.nspm.entity.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectMapper {

    Project selectObjById(Long id);

    List<Project> selectConditionQuery(ProjectDTO dto);

    List<Project> selectObjByMap(Map params);

    int save(Project instance);

    int update(Project instance);

    int delete(Long id);

}
