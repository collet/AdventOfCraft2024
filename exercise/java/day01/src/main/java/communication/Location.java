package communication;

public record Location(String currentLocation, int numbersOfDaysForComingBack) {

    public Location {
        if (currentLocation == null || currentLocation.isBlank()) {
            throw new IllegalArgumentException("currentLocation cannot be null or blank.");
        }
        if (numbersOfDaysForComingBack < 0) {
            throw new IllegalArgumentException("numbersOfDaysForComingBack cannot be negative.");
        }
    }

}
