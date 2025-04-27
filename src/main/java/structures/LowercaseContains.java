package structures;

public class LowercaseContains {
    public static boolean contains(String src, String dest) {
        for (int i = src.length() - dest.length(); i >= 0; i--) 
            if (src.regionMatches(true, i, dest, 0, dest.length())) 
                return true; 
        return false;
    }
}
