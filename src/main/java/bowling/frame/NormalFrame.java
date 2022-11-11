package bowling.frame;

import bowling.FallenPins;
import bowling.ResultMark;
import bowling.exception.ExceedFallenPinsException;

public class NormalFrame implements Frame {

    private FallenPins firstFallenPins;
    private FallenPins secondFallenPins;

    @Override
    public NormalFrame update(FallenPins fallenPins) {
        if (firstFallenPins == null) {
            this.firstFallenPins = fallenPins;
            return this;
        }

        validateSecondFallenPins(fallenPins);

        this.secondFallenPins = fallenPins;

        return this;
    }

    public String getResult() {
        ResultMark firstResultMark = ResultMark.getResultMark(firstFallenPins, null);
        ResultMark secondResultMark = ResultMark.getResultMark(secondFallenPins, firstFallenPins);

        String firstResult = getResultMark(firstResultMark, firstFallenPins);
        String secondResult = getResultMark(secondResultMark, secondFallenPins);
        if (!ResultMark.EMPTY.equals(secondResultMark)) {
            secondResult = RESULT_DELIMITER + secondResult;
        }

        return firstResult + secondResult;
    }

    @Override
    public boolean isFinish() {
        return firstFallenPins != null && secondFallenPins != null;
    }

    private void validateSecondFallenPins(FallenPins fallenPins) {
        if (firstFallenPins.getCountOfPin() + fallenPins.getCountOfPin()
                > FallenPins.MAX_COUNT_OF_PIN) {
            throw new ExceedFallenPinsException();
        }
    }

    private String getResultMark(ResultMark resultMark, FallenPins fallenPins) {
        if (ResultMark.MISS.equals(resultMark)) {
            return String.valueOf(fallenPins.getCountOfPin());
        }
        return resultMark.getMark();
    }

}