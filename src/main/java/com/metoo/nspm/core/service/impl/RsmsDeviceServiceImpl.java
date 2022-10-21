package com.metoo.nspm.core.service.impl;

import com.metoo.nspm.core.manager.admin.tools.ShiroUserHolder;
import com.metoo.nspm.core.mapper.DeviceTypeMapper;
import com.metoo.nspm.core.mapper.PlantRoomMapper;
import com.metoo.nspm.core.mapper.RackMapper;
import com.metoo.nspm.core.mapper.RsmsDeviceMapper;
import com.metoo.nspm.core.service.IRsmsDeviceService;
import com.metoo.nspm.dto.RsmsDeviceDTO;
import com.metoo.nspm.vo.RsmsDeviceVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.metoo.nspm.entity.DeviceType;
import com.metoo.nspm.entity.RsmsDevice;
import com.metoo.nspm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RsmsDeviceServiceImpl implements IRsmsDeviceService {

    @Autowired
    private RsmsDeviceMapper rsmsDeviceMapper;
    @Autowired
    private PlantRoomMapper plantRoomMapper;
    @Autowired
    private RackMapper rackMapper;
    @Autowired
    private DeviceTypeMapper deviceTypeMapper;

    @Override
    public RsmsDevice getObjById(Long id) {
        return this.rsmsDeviceMapper.getObjById(id);
    }

    @Override
    public RsmsDevice getObjAndProjectById(Long id) {
        RsmsDevice rsmsDevice = this.rsmsDeviceMapper.getObjAndProjectById(id);
        if(rsmsDevice != null){
            User user = ShiroUserHolder.currentUser();
            if(rsmsDevice.getUserId().equals(user.getId())){
                return rsmsDevice;
            }
        }
        return null;
    }

    @Override
    public Page<RsmsDevice> selectConditionQuery(RsmsDeviceDTO instance) {
        User user = ShiroUserHolder.currentUser();
        instance.setUserId(user.getId());
        Page<RsmsDevice> page = PageHelper.startPage(instance.getCurrentPage(), instance.getPageSize());
        this.rsmsDeviceMapper.selectConditionQuery(instance);
        return page;
    }

    @Override
    public List<RsmsDeviceVo> selectNameByMap(Map map) {
        User user = ShiroUserHolder.currentUser();
        map.put("userId", user.getId());
        return this.rsmsDeviceMapper.selectNameByMap(map);
    }


    @Override
    public List<RsmsDevice> selectObjByMap(Map map) {
        User user = ShiroUserHolder.currentUser();
        map.put("userId", user.getId());
        return this.rsmsDeviceMapper.selectObjByMap(map);
    }

    @Override
    public int save(RsmsDevice instance) {
        DeviceType deviceType = this.deviceTypeMapper.selectObjById(instance.getDeviceTypeId());
        if(deviceType instanceof DeviceType){
            instance.setDeviceTypeName(deviceType.getName());
        }
        User user = ShiroUserHolder.currentUser();
        if(user != null){
            instance.setUserId(user.getId());
        }
        if(instance.getId() == null){
            instance.setAddTime(new Date());
            return this.rsmsDeviceMapper.save(instance);
        }else{
            return this.rsmsDeviceMapper.update(instance);
        }
    }

    @Override
    public int update(RsmsDevice instance) {
        try {
            return this.rsmsDeviceMapper.update(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return  0;
        }
    }

    @Override
    public int delete(Long id) {
        return this.rsmsDeviceMapper.delete(id);
    }

    @Override
    public int batchDel(String ids) {
        return this.rsmsDeviceMapper.batchDel(ids);
    }
}
