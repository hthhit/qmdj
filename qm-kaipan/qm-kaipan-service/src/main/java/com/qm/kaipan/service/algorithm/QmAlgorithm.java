package com.qm.kaipan.service.algorithm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qm.common.utils.DateUtils;
import com.qm.kaipan.service.constant.QmConsts;
import com.qm.kaipan.service.entity.FourStem;
import com.qm.kaipan.service.entity.NodeRow;
import com.qm.kaipan.service.entity.NodeTable;
import com.qm.kaipan.service.entity.QmJuTypeEntity;
import com.qm.kaipan.service.entity.QmResultEntity;
import com.qm.kaipan.service.entity.StemNode;
import com.qm.kaipan.service.entity.ZhiFuZhiShiEntity;
import com.qm.kaipan.service.utils.LunarCalendar;
import com.qm.kaipan.service.utils.SolarTerm;

public class QmAlgorithm extends BaseAlgorithm {
	private static final Logger LOGGER = LoggerFactory.getLogger(QmAlgorithm.class);

	public QmAlgorithm() {
	}

	public static void main(String[] args) {
		QmAlgorithm amA = new QmAlgorithm();
		Date date = new Date();
		QmResultEntity result = amA.orderPan(date.getTime());
		LOGGER.info(JSONObject.toJSONString(result));
		LunarCalendar lc = new LunarCalendar();
		FourStem fs = lc.getFourStems();
		QmJuTypeEntity ju = amA.getQmJuEntityByFourStem(2016, fs);
		LOGGER.info(JSON.toJSONString(ju));
	}

	public QmResultEntity orderPan(long timeInMillis) {
		LunarCalendar lc = new LunarCalendar(timeInMillis);
		FourStem fs = lc.getFourStems();
		QmJuTypeEntity juEntity = getQmJuEntityByFourStem(timeInMillis, fs);
		QmResultEntity result = orderPan(fs, juEntity);
		return result;
	}

	
	private final static long oneDayMillis = 86400012;
	/**
	 * 通过四柱的天干与9取余法
	 * 
	 * @param timeInMillis
	 * @return
	 */
	public QmJuTypeEntity getQmJuEntityByFourStem(long timeInMillis, FourStem fs) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeInMillis);
		Date nowDate = new Date(timeInMillis);
		int year = cal.get(Calendar.YEAR);
		int y = fs.getYearNode().getHeavenNode().getKey();
		int m = fs.getMonthNode().getHeavenNode().getKey();
		int d = fs.getDayNode().getHeavenNode().getKey();
		int h = fs.getHourNode().getHeavenNode().getKey();
		int t = y + m + d + h;
		int juNumber = t % 9;
		SolarTerm s = new SolarTerm();
		List<NodeTable> nodeTables = s.getStermTables(year);
		Date dateChunFen = new Date();
		Date dateDongZhi = new Date();
		for (NodeTable node : nodeTables) {
			int key = node.getKey();
			// 夏至
			if (key == 6) {
				dateChunFen = DateUtils.stringToDate(node.getDateTime(), DateUtils.FMT.yyyy_MM_dd_HH_mm_ss);
			}
			// 冬至
			if (key == 18) {
				dateDongZhi = DateUtils.stringToDate(node.getDateTime(), DateUtils.FMT.yyyy_MM_dd_HH_mm_ss);
			}
		}
		int juType = QmJuTypeEntity.TYPE_SOLAR;
		if (nowDate.after(dateChunFen) && nowDate.before(dateDongZhi)) {
			juType = QmJuTypeEntity.TYPE_LUNAR;
		}
		return new QmJuTypeEntity(juType, juNumber);
	}
	/**
	 * 1 计算当前时间距离最近的节气 2 获取距离最近节气的天数 3 根据阳遁： 冬至、惊蛰一七四，小寒二八五， 大寒、春分三九六，雨水九六三，
	 * 清明、立夏四一七，立春八五二， 谷雨、小满五二八，芒种六三九。 阴遁： 夏至、白露九三六，小暑八二五， 大暑、秋分七一四，立秋二五八，
	 * 寒露、立冬六九三，处暑一四七， 霜降、小雪五八二，大雪四七一 确定局类型和局数
	 * 
	 * @param timeInMillis
	 * @return
	 */
	private QmJuTypeEntity getQmJuEntity(long timeInMillis) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeInMillis);
		int year = c.get(Calendar.YEAR);
		SolarTerm s = new SolarTerm();
		List<NodeTable> nodeTables = s.getStermTables(year);
		long diff = 1482400010;// 取16天的相差
		NodeTable nodeJQ = new NodeTable();
		for (NodeTable node : nodeTables) {
			String nodeTime = node.getDateTime();
			Date nodeDate = DateUtils.stringToDate(nodeTime, DateUtils.FMT.yyyy_MM_dd_HH_mm);
			long diffNow = timeInMillis - nodeDate.getTime();
			if (diffNow <= diff && diffNow >= 0) {
				diff = diffNow;
				nodeJQ.setDateTime(node.getDateTime());
				nodeJQ.setKey(node.getKey());
				nodeJQ.setName(node.getName());
			}
		}
		long diffDay = diff / oneDayMillis;
		System.out.println("最近的节气:" + JSON.toJSONString(nodeJQ));
		System.out.println("相差的天数:" + JSON.toJSONString(diffDay));
		int yuan = -10;
		if (diffDay >= -5 && diffDay < 0) {
			yuan = 1;
		} else if (diffDay >= 5 && diffDay < 10) {
			yuan = 2;
		} else if (diffDay >= 10 && diffDay < 15) {
			yuan = 3;
		}
		return null;
	}

	public QmResultEntity orderPan(Date date) {
		long timeInMillis = date.getTime();
		return null;
	}

	/**
	 * 开始排盘 找直符直使
	 */
	private QmResultEntity orderPan(FourStem fourStem, QmJuTypeEntity juEntity) {
		int juNumber = juEntity.getNumber();
		ZhiFuZhiShiEntity zhiFuZhiShiEntity = getZhifuZhiShi(fourStem, juEntity);
		LOGGER.info("直符和直使:" + JSON.toJSONString(zhiFuZhiShiEntity));
		Map<Integer, NodeRow> earthPan = getEarthPan(fourStem, juEntity);
		LOGGER.info("地盘干:" + JSON.toJSONString(earthPan));
		// 找时柱天支
		StemNode hourStem = fourStem.getHourNode();
		int heavenStem = hourStem.getHeavenNode().getKey();
		// 找询
		NodeRow xun = getXun(hourStem);// 找询
		int xunIndex = xun.getKey();
		LOGGER.info("询首:" + xunIndex + "-" + QmConsts.HEAVEN_STEM.get(xunIndex));
		// 把地盘对应的宫和干支反对应
		Map<Integer, Integer> earthPanMap = orderEarthPanAndPlace(earthPan);
		// 获取询所在的宫
		int xunPlace = earthPanMap.get(xunIndex);
		if (xunPlace == 5) {
			xunPlace = 2;
		}
		LOGGER.info("地盘符首所在的宫:" + xunPlace);
		Map<Integer, NodeRow> heavenPan = getHeavenPanMap(earthPan, heavenStem, juNumber);
		LOGGER.info("天盘排序:" + JSON.toJSONString(heavenPan));

		Map<Integer, NodeRow> zhiFuGodMap = getZhiFuPlaceMap(fourStem, juEntity);
		LOGGER.info("神落宫：" + JSON.toJSONString(zhiFuGodMap));

		Map<Integer, NodeRow> zhiShiGateMap = getZhiShiGatePlaceMap(fourStem, juEntity);
		LOGGER.info("直使门落宫:" + JSON.toJSONString(zhiShiGateMap));
		Map<Integer, NodeRow> zhiFuStarMap = getZhiFuStarPlaceMap(fourStem, juEntity);
		LOGGER.info("直符星落宫:" + JSON.toJSONString(zhiFuStarMap));

		QmResultEntity resultQm = new QmResultEntity();
		resultQm.setEarthPan(earthPan);
		resultQm.setHeavenPan(heavenPan);
		resultQm.setStarPan(zhiFuStarMap);
		resultQm.setGodPan(zhiFuGodMap);
		resultQm.setGatePan(zhiShiGateMap);
		return resultQm;
	}

	/**
	 * 排天盘干
	 * 
	 * @return
	 */
	private Map<Integer, NodeRow> getHeavenPanMap(Map<Integer, NodeRow> earthPan, int hourHeavenStem,
			int firstEarthPanPlace) {
		int hourHeavenPlace = getHeavenHourInEarthPlace(earthPan, hourHeavenStem);
		LOGGER.info("时天干所在的宫:" + hourHeavenPlace);
		List<Integer> heavenPanPlace = orderIndexPanByPlace(hourHeavenPlace);
		LOGGER.info("天盘宫序:" + JSON.toJSONString(heavenPanPlace));
		List<NodeRow> heavenPanOrder = getEarthPanForHeavenPan(earthPan, firstEarthPanPlace);
		LOGGER.info("天盘干支序:" + JSON.toJSONString(heavenPanOrder));
		Map<Integer, NodeRow> heavenPanMap = new LinkedHashMap<>();
		for (int i = 0; i < heavenPanPlace.size(); i++) {
			heavenPanMap.put(heavenPanPlace.get(i), heavenPanOrder.get(i));
		}
		return heavenPanMap;
	}

	/**
	 * 获取地盘干
	 * 
	 * @return
	 */
	private List<NodeRow> getEarthPanForHeavenPan(Map<Integer, NodeRow> earthPan, int firstEarthPanPlace) {
		List<NodeRow> heavenPanOrder = new ArrayList<>();
		List<Integer> earthPanPlaceForHeaven = orderIndexPanByPlace(firstEarthPanPlace);
		for (Integer place : earthPanPlaceForHeaven) {
			NodeRow node = earthPan.get(place);
			heavenPanOrder.add(node);
		}
		return heavenPanOrder;
	}

	/**
	 * 获取时天干在 地盘中的宫序号
	 * 
	 * @return
	 */
	private int getHeavenHourInEarthPlace(Map<Integer, NodeRow> earthPan, int hourHeavenStem) {

		int resultPlace = -1;
		/**
		 * 如果时天干是甲则说明是伏吟局
		 */
		if (hourHeavenStem == 1) {
			return resultPlace;
		}
		if (hourHeavenStem == 5) {
			resultPlace = 2;
		} else {
			Map<Integer, Integer> revertEarthPan = revertHeavenHourInEarthPanPlace(earthPan);
			resultPlace = revertEarthPan.get(hourHeavenStem);
		}
		return resultPlace;
	}

	/**
	 * 把地盘的宫和天干反转
	 * 
	 * @return
	 */
	private Map<Integer, Integer> revertHeavenHourInEarthPanPlace(Map<Integer, NodeRow> earthPan) {
		Map<Integer, Integer> revertEarthPan = new LinkedHashMap<>();
		Set<Integer> keySet = earthPan.keySet();
		for (int place : keySet) {
			NodeRow node = earthPan.get(place);
			int key = node.getKey();
			revertEarthPan.put(key, place);
		}
		return revertEarthPan;
	}

	/**
	 * 获取 直 符星的排盘
	 * 
	 * @param fourStem
	 * @param juEntity
	 * @return
	 */
	private Map<Integer, NodeRow> getZhiFuStarPlaceMap(FourStem fourStem, QmJuTypeEntity juEntity) {
		int firstStar = getZhiFuIndex(fourStem, juEntity);
		List<Integer> starPlaceList = orderIndexPanByPlace(firstStar);
		ZhiFuZhiShiEntity zhiFuZhiShi = getZhifuZhiShi(fourStem, juEntity);
		List<NodeRow> starList = getZhiFuZhiShiIndexPan(zhiFuZhiShi, QmConsts.EIGHT_STAR_PAN);
		Map<Integer, NodeRow> starMap = new LinkedHashMap<>();
		int j = 0;
		for (Integer key : starPlaceList) {
			NodeRow nr = starList.get(j);
			starMap.put(key, nr);
			j++;
		}
		return starMap;
	}

	/**
	 * 获取直使门排盘
	 * 
	 * @param fourStem
	 * @param juEntity
	 * @return
	 */
	private Map<Integer, NodeRow> getZhiShiGatePlaceMap(FourStem fourStem, QmJuTypeEntity juEntity) {
		ZhiFuZhiShiEntity zhiFuZhiShi = getZhifuZhiShi(fourStem, juEntity);
		int firstZhiShi = getZhiShiIndex(fourStem, juEntity);
		LOGGER.info("直使落宫:" + firstZhiShi);
		List<NodeRow> zhiShiList = getZhiFuZhiShiIndexPan(zhiFuZhiShi, QmConsts.EIGHT_GATE_PAN);
		List<Integer> zhiShiPlaceList = orderIndexPanByPlace(firstZhiShi);
		Map<Integer, NodeRow> zhiShiMap = new LinkedHashMap<>();
		int j = 0;
		for (Integer key : zhiShiPlaceList) {
			zhiShiMap.put(key, zhiShiList.get(j));
			j++;
		}
		return zhiShiMap;
	}

	/**
	 * 根据直符所在的星和直使所在的门重新排序星和门
	 * 
	 * @param zhiFuZhiShiEntity
	 * @return
	 */
	private List<NodeRow> getZhiFuZhiShiIndexPan(ZhiFuZhiShiEntity zhiFuZhiShiEntity,
			Map<Integer, String> zhiFuzhiShi) {
		int zhiShiIndex = -1, maxLen = -1;
		int mapLen = zhiFuzhiShi.size();
		if (zhiFuzhiShi.equals(QmConsts.EIGHT_GATE_PAN)) {
			zhiShiIndex = zhiFuZhiShiEntity.getZhiShi().getKey();
			maxLen = zhiFuzhiShi.size() + zhiShiIndex;
		} else if (zhiFuzhiShi.equals(QmConsts.EIGHT_STAR_PAN)) {
			zhiShiIndex = zhiFuZhiShiEntity.getZhiShi().getKey();
			maxLen = zhiFuzhiShi.size() + zhiShiIndex;
		}
		List<NodeRow> shiList = new ArrayList<>();
		for (int i = zhiShiIndex; i < maxLen; i++) {
			int j = i % mapLen;
			if (j == 0) {
				j = mapLen;
			}
			shiList.add(new NodeRow(j, zhiFuzhiShi.get(j)));
		}
		return shiList;
	}

	/**
	 * 确定直符的宫 阳局 直符落宫数=时干所在的三奇六仪中对应的序数+局数-1 阴局 直符落宫=1+局数-时干所在的三奇六仪中对应的次序数
	 * 如果落宫数大于9则减去9 若落宫数<0则加上9
	 * 
	 * @param zhiFu
	 */
	private Map<Integer, NodeRow> getZhiFuPlaceMap(FourStem fourStem, QmJuTypeEntity juEntity) {
		// 获取直符所在的宫
		int firstPlace = getZhiFuIndex(fourStem, juEntity);
		LOGGER.info("直符落宫:" + firstPlace);
		List<Integer> zhiFuPlaceList = orderIndexPanByPlace(firstPlace);
		Map<Integer, NodeRow> zhiFuMap = new LinkedHashMap<>();
		int j = 0;
		for (Integer key : zhiFuPlaceList) {
			j++;
			NodeRow nr = new NodeRow(j, QmConsts.EIGHT_GOD.get(j));
			zhiFuMap.put(key, nr);
		}
		return zhiFuMap;
	}

	private int getZhiFuIndex(FourStem fourStem, QmJuTypeEntity juEntity) {
		int juNumber = juEntity.getNumber();
		int juType = juEntity.getType();
		StemNode hourStem = fourStem.getHourNode();
		int hourHeaven = hourStem.getHeavenNode().getKey();
		int hourThreeSixIndex = STEM_THREE_SIX_INDEX.get(hourHeaven);
		int zhifuIndex = -1;
		if (juType == QmJuTypeEntity.TYPE_SOLAR) {
			zhifuIndex = hourThreeSixIndex + juNumber - 1;
		} else if (juType == QmJuTypeEntity.TYPE_LUNAR) {
			zhifuIndex = juNumber + 1 - hourThreeSixIndex;
		}
		zhifuIndex %= 9;
		if (zhifuIndex <= 0) {
			zhifuIndex += 9;
		}
		return zhifuIndex;
	}

	/**
	 * 阳遁 时干在天干中的顺序为1甲2乙3丙.... 直使落宫=直使序数+时干在天干中的序数-1 阴遁 时干在十天干中的顺序为1甲 9乙 8丙...
	 * 直使落宫=直使序数+时干在天干中的序数-1 结果大于9的减去9 如果直使落5宫寄在坤2宫
	 * 
	 * @return
	 */
	private static int getZhiShiIndex(FourStem fourStem, QmJuTypeEntity juEntity) {
		ZhiFuZhiShiEntity zhifuZhiShiEntity = getZhifuZhiShi(fourStem, juEntity);
		int zhiShiNum = zhifuZhiShiEntity.getZhiShi().getKey();
		int hourStem = fourStem.getHourNode().getHeavenNode().getKey();
		int juType = juEntity.getType();
		int zhiShiIndex = -1;
		if (juType == QmJuTypeEntity.TYPE_SOLAR) {
			zhiShiIndex = zhiShiNum + hourStem - 1;
		} else if (juType == QmJuTypeEntity.TYPE_LUNAR) {
			zhiShiIndex = zhiShiNum + HEAVEN_REVERSE_INDEX.get(hourStem) - 1;
		}
		if (zhiShiIndex == 5) {
			zhiShiIndex = 2;
		}
		return zhiShiIndex;
	}

	/**
	 * 计算公式
	 * 
	 * 阳局 直符直使序数=所用局数+时辰所在询序数-1 阴局 直符直使序数=所用局数-时辰所在询序数+1
	 * 时辰所在询数为地支序数-天干序数如果小于0则加12 大于9在此基础上减9
	 * 
	 * @param fourStem
	 * @param juEntity
	 * @return
	 */
	public static ZhiFuZhiShiEntity getZhifuZhiShi(FourStem fourStem, QmJuTypeEntity juEntity) {
		int type = juEntity.getType();
		int juNumber = juEntity.getNumber();
		StemNode hourStem = fourStem.getHourNode();
		// 时辰所在的序数 0表示甲子
		int hourDiff = hourStem.getEarthNode().getKey() - hourStem.getHeavenNode().getKey();
		if (hourDiff < 0) {
			hourDiff = hourDiff + 12;
		}
		int xunIndex = XUNSHU.get(hourDiff);
		int temp = -1;
		if (QmJuTypeEntity.TYPE_SOLAR == type) { // 阳局算法
			temp = juNumber + xunIndex - 1;
		} else if (QmJuTypeEntity.TYPE_LUNAR == type) {// 阴局算法
			temp = juNumber - xunIndex + 1;
		}
		int fuzhiIndex = temp % 9;
		if (fuzhiIndex <= 0) {
			fuzhiIndex = 9;
		}
		return new ZhiFuZhiShiEntity(fuzhiIndex, fuzhiIndex);
	}

	/**
	 * 排地盘干 根据局数来排 排法:戊排入局数坐在的宫边数
	 */
	public static Map<Integer, NodeRow> getEarthPan(FourStem fourStem, QmJuTypeEntity juEntity) {
		int juNumber = juEntity.getNumber();
		int maxValue = juNumber + 9;
		Map<Integer, NodeRow> resultMap = new LinkedHashMap<>();
		int index = -1;
		int j = 0;
		for (int i = juNumber; i < maxValue; i++) {
			index = i % 9;
			if (index == 0) {
				index = 9;
			}
			j++;
			NodeRow row = new NodeRow(THREE_SIX_INDEX.get(j), QmConsts.THREE_SIX.get(j));
			resultMap.put(index, row);
		}
		return resultMap;
	}

	// 宫的顺时针排序
	private static final int[] SHUN_SHI_ZHENG = { 1, 8, 3, 4, 9, 2, 7, 6 };

	private List<Integer> orderIndexPanByPlace(int heavenPlace) {
		int heavenIndex = 0;
		int len = SHUN_SHI_ZHENG.length;
		List<Integer> order = new ArrayList<>();
		// 找出时天支所在顺时针表中的序号
		for (int i = 0; i < len; i++) {
			if (heavenPlace == SHUN_SHI_ZHENG[i]) {
				heavenIndex = i;
			}
		}
		int maxIndex = heavenIndex + len;
		int newIndex = -1;
		for (int j = heavenIndex; j < maxIndex; j++) {
			newIndex = j % len;
			order.add(SHUN_SHI_ZHENG[newIndex]);
		}
		return order;
	}

	/**
	 * 排出地盘干的干支序号和对应的宫数
	 * 
	 * @return
	 */
	public Map<Integer, Integer> orderEarthPanAndPlace(Map<Integer, NodeRow> earthPan) {
		Map<Integer, Integer> resultMap = new HashMap<>();
		Set<Integer> placeSet = earthPan.keySet();
		for (Integer placeInt : placeSet) {
			NodeRow row = earthPan.get(placeInt);
			resultMap.put(row.getKey(), placeInt);
		}
		return resultMap;
	}

	/**
	 * 询首查找方法 时地支-时天干 对应到0戊（甲子）、10己（甲戌）、8庚（甲申）、6辛（甲午）、4壬(甲辰)、2癸(甲寅)
	 * 
	 * @param fourStem
	 * @return
	 */
	private NodeRow getXun(StemNode hourStem) {
		int heavenStem = hourStem.getHeavenNode().getKey();
		int earthStem = hourStem.getEarthNode().getKey();
		int firstXunIndex = earthStem - heavenStem;
		if (firstXunIndex < 0) {
			firstXunIndex += 12;
		}
		NodeRow xunRow = XUN_INDEX.get(firstXunIndex);
		return xunRow;
	}

	/**
	 * 根据查出来的询来找询首
	 */
	private static final Map<Integer, NodeRow> FIRST_XUN_INDEX = new HashMap<Integer, NodeRow>() {
		private static final long serialVersionUID = 1L;

		{
			put(0, new NodeRow(1, QmConsts.EARTH_STEM.get(1)));
			put(10, new NodeRow(11, QmConsts.EARTH_STEM.get(11)));
			put(8, new NodeRow(9, QmConsts.EARTH_STEM.get(9)));
			put(6, new NodeRow(7, QmConsts.EARTH_STEM.get(7)));
			put(4, new NodeRow(5, QmConsts.EARTH_STEM.get(5)));
			put(2, new NodeRow(3, QmConsts.EARTH_STEM.get(3)));

		}
	};

	/**
	 * 索引询的时候需要
	 */
	private static final Map<Integer, NodeRow> XUN_INDEX = new HashMap<Integer, NodeRow>() {
		private static final long serialVersionUID = 1L;

		{
			put(0, new NodeRow(5, QmConsts.HEAVEN_STEM.get(5)));
			put(10, new NodeRow(6, QmConsts.HEAVEN_STEM.get(6)));
			put(8, new NodeRow(7, QmConsts.HEAVEN_STEM.get(7)));
			put(6, new NodeRow(8, QmConsts.HEAVEN_STEM.get(8)));
			put(4, new NodeRow(9, QmConsts.HEAVEN_STEM.get(9)));
			put(2, new NodeRow(10, QmConsts.HEAVEN_STEM.get(10)));
		}
	};

	/**
	 * 天干反序
	 */
	private static final Map<Integer, Integer> HEAVEN_REVERSE_INDEX = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put(1, 1);
			put(9, 2);
			put(8, 3);
			put(7, 4);
			put(6, 5);
			put(5, 6);
			put(4, 7);
			put(3, 8);
			put(2, 9);
		}
	};

	/**
	 * 三奇六仪的序号对应到天干地序号
	 */
	private static final Map<Integer, Integer> THREE_SIX_INDEX = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put(1, 5);
			put(2, 6);
			put(3, 7);
			put(4, 8);
			put(5, 9);
			put(6, 10);
			put(7, 4);
			put(8, 3);
			put(9, 2);
		}
	};
	/**
	 * 用天干序号来索引出三奇六仪的序号
	 */
	private static final Map<Integer, Integer> STEM_THREE_SIX_INDEX = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put(5, 1);
			put(6, 2);
			put(7, 3);
			put(8, 4);
			put(9, 5);
			put(10, 6);
			put(4, 7);
			put(3, 8);
			put(2, 9);
		}
	};

	/**
	 * 计算直符直使得询数0甲戌 10 甲午 8甲申 6甲午 4 甲辰 2甲寅
	 */
	private static final Map<Integer, Integer> XUNSHU = new HashMap<Integer, Integer>() {
		private static final long serialVersionUID = 1L;

		{
			put(0, 1);
			put(10, 2);
			put(8, 3);
			put(6, 4);
			put(4, 5);
			put(2, 6);
		}
	};
}
