package communication;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class LocationTest {

    @Test
    void aValidLocation() {
        Location location = new Location("SOUTH_POLE", 85);
        assertThat(location.currentLocation()).isEqualTo("SOUTH_POLE");
        assertThat(location.numbersOfDaysForComingBack()).isEqualTo(85);
    }

    @Test
    void shouldThrowExceptionOnNullCurrentLocation() {
        assertThatThrownBy(() -> new Location(null, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("currentLocation cannot be null or blank.");
    }

    @Test
    void shouldThrowExceptionOnBlankCurrentLocation() {
        assertThatThrownBy(() -> new Location("   ", 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("currentLocation cannot be null or blank.");
    }

    @Test
    void shouldThrowExceptionOnNegativeDays() {
        assertThatThrownBy(() -> new Location("SOUTH_POLE", -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("numbersOfDaysForComingBack cannot be negative.");
    }

}