package cleancode.studycafe.tobe.model;

import java.util.ArrayList;
import java.util.List;

public class StudyCafeLockerPassList {

  private final List<StudyCafePassType> passTypeList;

  private StudyCafeLockerPassList() {

    List<StudyCafePassType> list = new ArrayList<>();
    list.add(StudyCafePassType.FIXED);

    this.passTypeList = list;
  }

  public List<StudyCafePassType> getPassTypeList() {
    return passTypeList;
  }

  public static boolean doesContainLockerPass(StudyCafePassType type) {
    List<StudyCafePassType> list = new StudyCafeLockerPassList().getPassTypeList();

    for(StudyCafePassType lockerPassType : list) {
      if(lockerPassType.name().equals(type.name())) {
        return true;
      }
    }
    return false;
  }
}
