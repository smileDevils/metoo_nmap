package com.metoo.nspm.core.service.zabbix;

import com.metoo.nspm.dto.zabbix.TemplateDTO;

public interface ITemplateService {

    Object getTemplate(TemplateDTO dto);

    String getTemplateId();
}
