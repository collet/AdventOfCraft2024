package games;

import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Seq;
import io.vavr.test.Arbitrary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static games.FizzBuzz.MAX;
import static games.FizzBuzz.MIN;
import static io.vavr.API.List;
import static io.vavr.API.Some;
import static io.vavr.test.Arbitrary.integer;
import static io.vavr.test.Property.def;
import static org.assertj.core.api.Assertions.assertThat;

class FizzBuzzTests {
    private static final Seq<String> fizzBuzzStrings = List(
            "Fizz", "Buzz", "Whizz", "Bang",
            "FizzBuzz", "FizzWhizz", "FizzBang",
            "BuzzWhizz", "BuzzBang",
            "WhizzBang",
            "FizzBuzzWhizz", "FizzBuzzBang",
            "FizzWhizzBang",
            "BuzzWhizzBang",
            "FizzBuzzWhizzBang");

    private static final FizzBuzz fizzBuzz = new FizzBuzz(LinkedHashMap.of(
            3, "Fizz",
            5, "Buzz",
            7, "Whizz",
            11, "Bang"
    ));

    public static Stream<Arguments> validInputs() {
        return Stream.of(
                Arguments.of(1, "1"),
                Arguments.of(67, "67"),
                Arguments.of(82, "82"),
                Arguments.of(3, "Fizz"),
                Arguments.of(66, "FizzBang"),
                Arguments.of(99, "FizzBang"),
                Arguments.of(5, "Buzz"),
                Arguments.of(50, "Buzz"),
                Arguments.of(85, "Buzz"),
                Arguments.of(15, "FizzBuzz"),
                Arguments.of(30, "FizzBuzz"),
                Arguments.of(35, "BuzzWhizz"),
                Arguments.of(45, "FizzBuzz"),
                Arguments.of(7, "Whizz"),
                Arguments.of(28, "Whizz"),
                Arguments.of(77, "WhizzBang"),
                Arguments.of(21, "FizzWhizz"),
                Arguments.of(42, "FizzWhizz"),
                Arguments.of(84, "FizzWhizz"),
                Arguments.of(35, "BuzzWhizz"),
                Arguments.of(70, "BuzzWhizz"),
                Arguments.of(140, "BuzzWhizz"),
                Arguments.of(55, "BuzzBang"),
                Arguments.of(110, "BuzzBang"),
                Arguments.of(105, "FizzBuzzWhizz"),
                Arguments.of(210, "FizzBuzzWhizz"),
                Arguments.of(165, "FizzBuzzBang"),
                Arguments.of(231, "FizzWhizzBang"),
                Arguments.of(385, "BuzzWhizzBang"),
                Arguments.of(1155, "FizzBuzzWhizzBang")
        );
    }

    @ParameterizedTest
    @MethodSource("validInputs")
    void parse_successfully_relevant_numbers_between_MIN_and_MAX_samples(int input, String expectedResult) {
        assertThat(fizzBuzz.convert(input))
                .isEqualTo(Some(expectedResult));
    }

    @Test
    void parse_return_valid_string_for_numbers_between_MIN_and_MAX() {
        def("Some(validString) for numbers in [%d; %d]".formatted(MIN, MAX))
                .forAll(validInput())
                .suchThat(this::isConvertValid)
                .check()
                .assertIsSatisfied();
    }

    @Test
    void parse_fail_for_numbers_out_of_range() {
        def("None for numbers out of range")
                .forAll(invalidInput())
                .suchThat(x -> fizzBuzz.convert(x).isEmpty())
                .check()
                .assertIsSatisfied();
    }

    private boolean isConvertValid(Integer x) {
        return fizzBuzz.convert(x)
                .exists(s -> validStringsFor(x).contains(s));
    }

    private static Arbitrary<Integer> validInput() {
        return integer().filter(x -> x >= MIN && x <= MAX);
    }

    private static Seq<String> validStringsFor(Integer x) {
        return fizzBuzzStrings.append(x.toString());
    }

    private static Arbitrary<Integer> invalidInput() {
        return integer().filter(x -> x < MIN || x > MAX);
    }
}