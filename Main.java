import java.io.*;
import java.util.*;

class Main {
  static final String DNA_BASE_ENCODING_A = "00";
  static final String DNA_BASE_ENCODING_C = "01";
  static final String DNA_BASE_ENCODING_G = "10";
  static final String DNA_BASE_ENCODING_T = "11";
  static final String[] DNA_BASE_ENCODINGS = 
      {DNA_BASE_ENCODING_A, DNA_BASE_ENCODING_C, DNA_BASE_ENCODING_G, DNA_BASE_ENCODING_T};
  static final String[] DNA_BASES = {"A", "C", "G", "T"};

  public static void main(String[] args) throws Exception {
    String input = "00000000 11100000 11000001 01111111";
    int L = 2;

//    try (Reader reader = new BufferedReader(new InputStringReader(filename)) { // java 7 resource declaration syntax
    try (Reader reader = new StringReader(input)) { // java 7 resource declaration syntax
      System.out.println(toFASTQ(reader, L));
    } // resources automagically closed

  }

  /**
  Read 
  */
  static String toFASTQ(Reader reader, int L) throws IOException {
    StringBuffer output = new StringBuffer();
    int pieceIndex = 1; 

    String nextByte = getNextByte(reader);
    while (!nextByte.trim().equals("")) {
      output.append("@READ_" + pieceIndex + "\n");
      StringBuffer qualityOutput = new StringBuffer();;
      for (int i=0; i<L; i++) {
        String baseEncoded = nextByte.substring(0,2);
        String base = DNA_BASES[Arrays.binarySearch(DNA_BASE_ENCODINGS, baseEncoded)];
        output.append(base);
        int qualityScore = Byte.parseByte(nextByte.substring(2), 2);
        char qualityEncoded = (char) (qualityScore + 33);
        qualityOutput.append(qualityEncoded);
        nextByte = getNextByte(reader);
      }
      output.append("\n+READ_" + pieceIndex + "\n");
      output.append(qualityOutput + "\n");
      pieceIndex++;
    }
    return output.toString();
  }

  /**
  Fetch up to the next 8 binary digits from the stream. This function skips over any characters other than 0 or 1.
  If there are less than 8 binary digits remaining before the end of the stream, less than 8 are returned.
  If the reader is closed or no binary digits remain, an empty string is returned.

  @param reader the stream to read from
  @return a String with up to the next 8 binary digits in the stream
  */
  static String getNextByte(Reader reader) throws IOException {
      StringBuffer byteBuffer = new StringBuffer();
      int nextBit = reader.read();

      while (nextBit != -1 && byteBuffer.length() < 8) { 
      // while we haven't reached the end of the stream and we haven't yet found 8 digits
        char nextBitChar = (char) nextBit;
        if (nextBitChar == '0' || nextBitChar == '1')
          byteBuffer.append((char) nextBit);
        // else do nothing - this skips over any whitespace or unexpected characters

        nextBit = reader.read();
      }
      return byteBuffer.toString();
  }
}