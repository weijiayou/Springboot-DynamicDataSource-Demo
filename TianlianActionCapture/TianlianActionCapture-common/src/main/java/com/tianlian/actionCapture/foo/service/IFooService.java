package com.tianlian.actionCapture.foo.service;

import com.tianlian.actionCapture.foo.model.StaffPO;
import com.tianlian.actionCapture.foo.model.UserPO;
import java.util.List;

public interface IFooService {

  void aaa();

  List<StaffPO> getStaff();

  String getUserName();

  List<UserPO> getUser();
}
