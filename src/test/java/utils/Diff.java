package utils;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.algorithm.myers.MyersDiff;
import com.github.difflib.patch.Patch;

import java.util.List;
import java.util.function.BiPredicate;

public class Diff {
    public static <T> Patch<T> diff(List<T> original, List<T> revised,
                                    BiPredicate<T, T> equalizer) throws DiffException {
        if (equalizer != null) {
            return DiffUtils.diff(original, revised,
                    new MyersDiff<>(equalizer));
        }
        return DiffUtils.diff(original, revised, new MyersDiff<>());
    }
}
