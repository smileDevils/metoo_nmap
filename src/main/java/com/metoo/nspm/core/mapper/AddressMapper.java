package com.metoo.nspm.core.mapper;

import com.metoo.nspm.entity.Address;
import com.metoo.nspm.entity.zabbix.IpAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AddressMapper {

    Address selectObjById(Long id);

    List<Address> selectObjByMap(Map map);

    Address selectObjByMac(String mac);

    Address selectObjByIp(String ip);

    int save(Address instance);

    int update(Address instance);

    int delete(Long id);

    void truncateTable();

}
