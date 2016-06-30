package com.qm.kaipan.service.qm.qmImpl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qm.common.utils.DateUtils;
import com.qm.common.utils.StrUtils;
import com.qm.kaipan.service.algorithm.QmAlgorithm;
import com.qm.kaipan.service.entity.JsonResult;
import com.qm.kaipan.service.entity.QmResultEntity;
import com.qm.kaipan.service.qm.QmService;

@Service("qmService")
public class QmServiceimpl implements QmService {

	@Override
	public JsonResult queryPanBase(String dateTime) {
		Date date = new Date();
		if (StrUtils.isEmpty(dateTime)) {
			date = new Date();
		} else {
			date = DateUtils.stringToDate(dateTime, DateUtils.FMT.yyyy_MM_dd_HH_mm);
		}
		QmAlgorithm qmAlg = new QmAlgorithm();
		QmResultEntity resultEntity = qmAlg.orderPan(date.getTime());
		return JsonResult.setJsonResult(JsonResult.Code.CODE_SUCCESS, JsonResult.Msg.MSG_SUCCESS, resultEntity);
	}

}
