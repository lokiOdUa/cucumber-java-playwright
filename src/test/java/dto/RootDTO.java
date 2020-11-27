package dto;

public class RootDTO {
    protected boolean matches(Boolean first, Boolean second) {
        if (first == null)
            return second == null;
        return first.equals(second);
    }
}
