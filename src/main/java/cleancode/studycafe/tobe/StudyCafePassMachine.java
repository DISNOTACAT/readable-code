package cleancode.studycafe.tobe;

import static cleancode.studycafe.tobe.model.StudyCafeLockerPassList.doesContainLockerPass;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.InputHandler;
import cleancode.studycafe.tobe.io.OutputHandler;
import cleancode.studycafe.tobe.io.StudyCafeFileHandler;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();


    public void run() {

        showWelcomeAndAnnouncement();

        try {
            StudyCafePassType studyCafePassTypeFromUser = getStudyCafePassTypeFromUser();
            List<StudyCafePass> candidatesPasses = studyCafeFileHandler.candidatesPassesFrom(
                studyCafePassTypeFromUser);
            outputHandler.showPassListForSelection(candidatesPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(
                candidatesPasses);

            if (!doesContainLockerPass(studyCafePassTypeFromUser)) {
                outputHandler.showPassOrderSummary(selectedPass, null);
            }

            if (doesContainLockerPass(studyCafePassTypeFromUser)) {

                Optional<StudyCafeLockerPass> candidatelockerPass = studyCafeFileHandler.getLockerPassFrom(
                    selectedPass);
                candidatelockerPass.ifPresentOrElse(
                    pass -> askLockerSelection(pass, selectedPass),
                    () -> outputHandler.showPassOrderSummary(selectedPass,
                        null)
                );
            }

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private void askLockerSelection(StudyCafeLockerPass lockerPass,
        StudyCafePass selectedPass) {
        outputHandler.askLockerPass(lockerPass);

        boolean lockerSelection = false;
        lockerSelection = inputHandler.getLockerSelection();

        if (lockerSelection) {
            outputHandler.showPassOrderSummary(selectedPass,
                lockerPass);
            return;
        }
        outputHandler.showPassOrderSummary(selectedPass, null);
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
