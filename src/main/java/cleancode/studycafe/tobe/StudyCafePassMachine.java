package cleancode.studycafe.tobe;

import static cleancode.studycafe.tobe.model.StudyCafePassType.isFixedPass;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();


    public void run() {

        showWelcomeAndAnnouncement();

        try {

            StudyCafePassType studyCafePassTypeFromUser = getStudyCafePassTypeFromUser();
            List<StudyCafePass> candidatesPasses = studyCafeFileHandler.candidatesPassesFrom(studyCafePassTypeFromUser);
            outputHandler.showPassListForSelection(candidatesPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(candidatesPasses);

            if(!isFixedPass(studyCafePassTypeFromUser)) {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

            if (isFixedPass(studyCafePassTypeFromUser)) {
                StudyCafeLockerPass lockerPass = studyCafeFileHandler.getLockerPassFrom(selectedPass);

                boolean lockerSelection = false;
                if (lockerPass != null) {
                    outputHandler.askLockerPass(lockerPass);
                    lockerSelection = inputHandler.getLockerSelection();
                }

                if (lockerSelection) {
                    outputHandler.showPassOrderSummary(selectedPass,
                        lockerPass);
                } else {
                    outputHandler.showPassOrderSummary(selectedPass, null);
                }
            }

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafePassType getStudyCafePassTypeFromUser() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    private void showWelcomeAndAnnouncement() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();
    }

}
