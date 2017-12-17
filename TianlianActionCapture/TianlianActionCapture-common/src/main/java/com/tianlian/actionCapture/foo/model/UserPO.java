package com.tianlian.actionCapture.foo.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserPO implements Serializable {

  private static final long serialVersionUID = 2206759648663074641L;

  private String userId;

  private String userName;
}
