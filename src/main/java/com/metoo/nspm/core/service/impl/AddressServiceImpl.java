package com.metoo.nspm.core.service.impl;

import com.metoo.nspm.core.mapper.AddressMapper;
import com.metoo.nspm.core.mapper.zabbix.IpAddressMapper;
import com.metoo.nspm.core.service.IAddressService;
import com.metoo.nspm.core.service.zabbix.IIPAddressServie;
import com.metoo.nspm.entity.Address;
import com.metoo.nspm.entity.zabbix.IpAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> selectObjByMap(Map map) {
        return this.addressMapper.selectObjByMap(map);
    }

    @Override
    public Address selectObjByMac(String mac) {
        return this.addressMapper.selectObjByMac(mac);
    }

    @Override
    public Address selectObjByIp(String ip) {
        return this.addressMapper.selectObjByIp(ip);
    }

    @Override
    public Address selectObjById(Long id) {
        return this.addressMapper.selectObjById(id);
    }

    @Override
    public int save(Address instance) {
        if(instance.getId() == null){
            instance.setAddTime(new Date());
        }
        if(instance.getId() == null){
            try {
                return this.addressMapper.save(instance);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }else{
            try {
                return this.addressMapper.update(instance);
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    @Override
    public int update(Address instance) {
        return this.addressMapper.update(instance);
    }

    @Override
    public int delete(Long id) {
        return this.addressMapper.delete(id);
    }

    @Override
    public void truncateTable() {
        this.addressMapper.truncateTable();
    }

}
