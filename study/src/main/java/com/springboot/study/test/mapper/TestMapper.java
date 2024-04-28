package com.springboot.study.test.mapper;

import com.springboot.study.test.vo.TestVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {
    List<TestVo> selectTest();
}
