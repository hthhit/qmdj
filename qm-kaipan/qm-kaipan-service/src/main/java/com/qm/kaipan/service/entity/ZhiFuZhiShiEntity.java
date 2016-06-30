package com.qm.kaipan.service.entity;

import com.qm.kaipan.service.constant.QmConsts;

public class ZhiFuZhiShiEntity {
	NodeRow zhiFu;
	NodeRow zhiShi;

	public ZhiFuZhiShiEntity(int zhiFu, int zhiShi) {
		this.zhiFu = new NodeRow(zhiFu, QmConsts.EIGHT_STAR.get(zhiFu));
		this.zhiShi = new NodeRow(zhiFu, QmConsts.EIGHT_GATE.get(zhiShi));
	}

	public NodeRow getZhiFu() {
		return zhiFu;
	}

	public void setZhiFu(NodeRow zhiFu) {
		this.zhiFu = zhiFu;
	}

	public NodeRow getZhiShi() {
		return zhiShi;
	}

	public void setZhiShi(NodeRow zhiShi) {
		this.zhiShi = zhiShi;
	}
}
