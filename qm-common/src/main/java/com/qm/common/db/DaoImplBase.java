package com.qm.common.db;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import com.qm.common.profile.Beacon;
import com.qm.common.profile.Profiler;
import com.qm.common.utils.StrUtils;

/**
 * 主从读写分离，请自主实现依赖主库的强一致性读写需求
 * 
 */
public abstract class DaoImplBase {

	protected abstract String getNamespace();

	/**
	 * 主库：负责写入
	 */
	protected abstract SqlSessionTemplate getSessionW();

	/**
	 * 从库：负责读取
	 */
	protected abstract SqlSessionTemplate getSessionR();

	private String genStatement(String id) {
		return StrUtils.concat(getNamespace(), ".", id);
	}

	// SQL性能统计
	private Beacon start(String statement) {
		return Profiler.start(Beacon.FUNC_SQL, statement, "");
	}

	// ==== 重载各种方法以在框架上保证读写分离 ====

	protected <T> T selectOne(String id) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		T ret = this.getSessionR().selectOne(statement);
		b.end();
		return ret;
	}

	protected <T> T selectOne(String id, Object parameter) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		T ret = this.getSessionR().selectOne(statement, parameter);
		b.end();
		return ret;
	}

	protected <K, V> Map<K, V> selectMap(String id, String mapKey) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		Map<K, V> ret = this.getSessionR().selectMap(statement, mapKey);
		b.end();
		return ret;
	}

	protected <K, V> Map<K, V> selectMap(String id, Object parameter, String mapKey) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		Map<K, V> ret = this.getSessionR().selectMap(statement, parameter, mapKey);
		b.end();
		return ret;
	}

	protected <K, V> Map<K, V> selectMap(String id, Object parameter, String mapKey, RowBounds rowBounds) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		Map<K, V> ret = this.getSessionR().selectMap(statement, parameter, mapKey, rowBounds);
		b.end();
		return ret;
	}

	protected <E> List<E> selectList(String id) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		List<E> ret = this.getSessionR().selectList(statement);
		b.end();
		return ret;
	}

	protected <E> List<E> selectList(String id, Object parameter) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		List<E> ret = this.getSessionR().selectList(statement, parameter);
		b.end();
		return ret;
	}

	protected <E> List<E> selectList(String id, Object parameter, RowBounds rowBounds) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		List<E> ret = this.getSessionR().selectList(statement, parameter, rowBounds);
		b.end();
		return ret;
	}

	@SuppressWarnings("rawtypes")
	protected void select(String id, ResultHandler handler) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		this.getSessionR().select(statement, handler);
		b.end();
	}

	@SuppressWarnings("rawtypes")
	protected void select(String id, Object parameter, ResultHandler handler) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		this.getSessionR().select(statement, parameter, handler);
		b.end();
	}

	@SuppressWarnings("rawtypes")
	protected void select(String id, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		this.getSessionR().select(statement, parameter, rowBounds, handler);
		b.end();
	}

	protected int insert(String id) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		int ret = this.getSessionW().insert(statement);
		b.end();
		return ret;
	}

	protected int insert(String id, Object parameter) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		int ret = this.getSessionW().insert(statement, parameter);
		b.end();
		return ret;
	}

	protected int update(String id) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		int ret = this.getSessionW().update(statement);
		b.end();
		return ret;
	}

	protected int update(String id, Object parameter) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		int ret = this.getSessionW().update(statement, parameter);
		b.end();
		return ret;
	}

	protected int delete(String id) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		int ret = this.getSessionW().delete(statement);
		b.end();
		return ret;
	}

	protected int delete(String id, Object parameter) {
		String statement = genStatement(id);
		Beacon b = this.start(statement);
		int ret = this.getSessionW().delete(statement, parameter);
		b.end();
		return ret;
	}
}
