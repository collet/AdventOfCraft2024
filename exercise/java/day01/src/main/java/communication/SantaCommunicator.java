package communication;

public class SantaCommunicator {
    private final int numberOfDaysToRest;

    public SantaCommunicator(int numberOfDaysToRest) {
        this.numberOfDaysToRest = numberOfDaysToRest;
    }

    public String composeMessage(String reindeerName, Location location, int numberOfDaysBeforeChristmas) {
        var daysBeforeReturn = daysBeforeReturn(location.numbersOfDaysForComingBack(), numberOfDaysBeforeChristmas);

        return "Dear " + reindeerName + ", please return from " + location.currentLocation() +
                " in " + daysBeforeReturn + " day(s) to be ready and rest before Christmas.";
    }

    public boolean isOverdue(String reindeerName, Location location, int numberOfDaysBeforeChristmas, Logger logger) {
        if (daysBeforeReturn(location.numbersOfDaysForComingBack(), numberOfDaysBeforeChristmas) <= 0) {
            logger.log("Overdue for " + reindeerName + " located " + location.currentLocation() + ".");
            return true;
        }
        return false;
    }

    private int daysBeforeReturn(int numbersOfDaysForComingBack, int numberOfDaysBeforeChristmas) {
        return numberOfDaysBeforeChristmas - numbersOfDaysForComingBack - numberOfDaysToRest;
    }
}