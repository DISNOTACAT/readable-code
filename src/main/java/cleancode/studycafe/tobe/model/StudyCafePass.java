package cleancode.studycafe.tobe.model;

import static cleancode.studycafe.tobe.model.StudyCafePassType.isFixedPass;
import static cleancode.studycafe.tobe.model.StudyCafePassType.isHourlyPass;
import static cleancode.studycafe.tobe.model.StudyCafePassType.isWeeklyPass;

public class StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafePass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafePass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafePass(passType, duration, price, discountRate);
    }

    public StudyCafePassType getPassType() {
        return passType;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public String display() {
        if (isHourlyPass(passType)) {
            return String.format("%s시간권 - %d원", duration, price);
        }
        if (isWeeklyPass(passType)) {
            return String.format("%s주권 - %d원", duration, price);
        }
        if (isFixedPass(passType)) {
            return String.format("%s주권 - %d원", duration, price);
        }
        return "";
    }

}
