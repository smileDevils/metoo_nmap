package com.metoo.nspm.core.websocket.api;

import com.alibaba.fastjson.JSONObject;
import com.metoo.nspm.core.config.websocket.demo.NoticeWebsocketResp;
import com.metoo.nspm.core.manager.admin.tools.DateTools;
import com.metoo.nspm.core.manager.admin.tools.MacUtil;
import com.metoo.nspm.core.service.api.zabbix.ZabbixService;
import com.metoo.nspm.core.service.nspm.IMacHistoryService;
import com.metoo.nspm.core.service.nspm.IMacService;
import com.metoo.nspm.core.service.nspm.IMacVendorService;
import com.metoo.nspm.core.service.nspm.INetworkElementService;
import com.metoo.nspm.core.service.zabbix.ItemService;
import com.metoo.nspm.entity.nspm.Mac;
import com.metoo.nspm.entity.nspm.NetworkElement;
import com.metoo.nspm.entity.zabbix.Item;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.*;

@RequestMapping("/websocket/api/zabbix")
@RestController
public class TopologyManagerApi {

    @Autowired
    private IMacService macService;
    @Autowired
    private IMacHistoryService macHistoryService;
    @Autowired
    private IMacVendorService macVendorService;
    @Autowired
    private INetworkElementService networkElementService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ZabbixService zabbixService;
    @Autowired
    private MacUtil macUtil;

    @ApiOperation("设备 Mac (DT))")
    @GetMapping(value = {"/mac/dt"})
    public NoticeWebsocketResp getObjMac(@RequestParam(value = "requestParams", required = false) String requestParams){
//        Date time = null;
//        List<String> list = new ArrayList<>();
        Map params = JSONObject.parseObject(String.valueOf(requestParams), Map.class);
        Date time = DateTools.parseDate(String.valueOf(params.get("time")), "yyyy-MM-dd HH:mm");
        List<String> list = JSONObject.parseObject(String.valueOf(params.get("params")), List.class);
//        if(requestParams instanceof Map){
//            Map params = JSONObject.parseObject(String.valueOf(requestParams), Map.class);
//            time = DateTools.parseDate(String.valueOf(params.get("time")), "yyyy-MM-dd HH:mm");
//            list = JSONObject.parseObject(String.valueOf(params.get("params")), List.class);
//        }
//        if(requestParams instanceof String){
//            list = JSONObject.parseObject(String.valueOf(requestParams), List.class);
//        }
        Map map = new HashMap();
        Map args = new HashMap();
        if(time == null){
            for (String uuid : list) {
                Map flux_terminal = new HashMap();
                args.clear();
                args.put("uuid", uuid);
                args.put("tag", "DT");
                List<Mac> macs = this.macService.selectByMap(args);
                this.macUtil.macJoint(macs);
                flux_terminal.put("terminal", macs);
                // 流量
                Map flux = new HashMap();
                params.clear();
                NetworkElement ne = this.networkElementService.selectObjByUuid(uuid);
                if(ne != null){
                    args.put("ip", ne.getIp());
                    // 采集ifbasic,然后查询端口对应的历史流量
                    args.put("tag", "ifreceived");
                    args.put("available", 1);
                    List<Item> items = this.itemService.selectTagByMap(args);
                    //                                Map ele = new HashMap();
                    if(items.size() > 0){
                        for (Item item : items) {
                            String lastvalue = this.zabbixService.getItemLastvalueByItemId(item.getItemid().intValue());
                            flux.put("received", lastvalue);
                            break;
                        }
                    } else{
                        flux.put("received", "0");
                    }
                    args.clear();
                    args.put("ip", ne.getIp());
                    // 采集ifbasic,然后查询端口对应的历史流量
                    args.put("tag", "ifsent");
                    args.put("available", 1);
                    List<Item> ifsents = this.itemService.selectTagByMap(args);
                    if(ifsents.size() > 0){
                        for (Item item : ifsents) {
                            String lastvalue = this.zabbixService.getItemLastvalueByItemId(item.getItemid().intValue());
                            flux.put("sent", lastvalue);
                            break;
                        }
                    }else{
                        flux.put("sent", "0");
                    }
                }
                flux_terminal.put("flux", flux);
                map.put(uuid, flux_terminal);
            }
        }else{
            for (String item : list) {
                args.clear();
                args.put("uuid", item);
                args.put("tag", "DT");
                args.put("time", time);
                List<Mac> macs = this.macHistoryService.selectByMap(args);
                this.macUtil.macJoint(macs);
                map.put(item, macs);
            }
        }


        NoticeWebsocketResp rep = new NoticeWebsocketResp();
        if(map.size() > 0){
            rep.setNoticeType("4");
            rep.setNoticeStatus(1);
            rep.setNoticeInfo(map);
        }else{
            rep.setNoticeType("4");
            rep.setNoticeStatus(0);
        }
        return rep;
    }
}
