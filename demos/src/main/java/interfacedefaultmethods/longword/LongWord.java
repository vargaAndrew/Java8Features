package interfacedefaultmethods.longword;

import java.nio.file.Path;
import java.util.List;

public class LongWord implements FileOperations {

    private Path path;

    public LongWord(Path path) {
        this.path = path;
    }

    @Override
    public String getLongWord() {
        List<String> lines = readFromFile(path);
        String firstWord = lines.get(0);
        int lengthOfWord = firstWord.length() + lines.size() - 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < lengthOfWord; i++) {
            if (i < firstWord.length()) {
                result.append(firstWord.charAt(i));
            } else {
                result.append(lines.get(i - (firstWord.length() - 1)).charAt(firstWord.length() - 1));
            }
        }
        return result.toString();
    }
}
