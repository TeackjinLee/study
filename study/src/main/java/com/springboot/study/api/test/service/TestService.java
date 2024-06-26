package com.springboot.study.api.test.service;

import com.springboot.study.api.test.mapper.TestMapper;
import com.springboot.study.api.test.vo.TestVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TestMapper mapper;

    public List<TestVo> selectTest() {
        logger.trace("TRACE Level 테스트");
        logger.debug("DEBUG Level 테스트");
        logger.info("INFO Level 테스트");
        logger.warn("WARN Level 테스트");
        logger.error("ERROR Level 테스트");
        return mapper.selectTest();
    }
}
