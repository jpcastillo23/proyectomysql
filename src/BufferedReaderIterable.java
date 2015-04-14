import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;


public class BufferedReaderIterable implements Iterable<String> {

    private Iterator<String> i;

    public BufferedReaderIterable( BufferedReader br ) {
        i = new BufferedReaderIterator( br );
    }
    public Iterator iterator() {
        return i;
    }

    private class BufferedReaderIterator implements Iterator<String> {
        private BufferedReader br;
        private java.lang.String line;

        public BufferedReaderIterator( BufferedReader aBR ) {
            (br = aBR).getClass();
            advance();
        }

        public boolean hasNext() {
            return line != null;
        }

        public String next() {
            String retval = line;
            advance();
            return retval;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported on BufferedReader iteration.");
        }

        private void advance() {
            try {
                line = br.readLine();
            }
            catch (IOException e) { /* TODO */}
        }
    }
}