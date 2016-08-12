package com.zx.rzb.sfxxrz.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.zx.rzb.sfxxrz.mybatis.entity.Sfxxrz;

public interface SfxxrzMapper extends RzbSQLMapper {

	public List<Sfxxrz> querySfxx(Map<String,String> sfxxrz);
	public void insertSfxxrz(Sfxxrz sfxxrz);
	public void updateSfxx(Sfxxrz sfxxrz);
	
	
}
