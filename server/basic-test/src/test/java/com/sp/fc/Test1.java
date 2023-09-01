package com.sp.fc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Test1 {


  /**
   * 스프링 의존성이랑 java 설정이 정상적으로 진행 되었는지 확인하기 위한 테스트
   */
  @DisplayName("1. 테스트")
  @Test
  void test_1() {

    assertEquals("test", "test");

  }
}
